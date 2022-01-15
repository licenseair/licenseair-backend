package com.licenseair.backend.controller;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.InstanceImage;
import com.licenseair.backend.library.AliyunInstances;
import com.licenseair.backend.service.AppInstanceService;
import com.licenseair.backend.domain.AppInstance;
import com.licenseair.backend.domainModel.AppInstanceModel;
import io.ebean.DB;
import io.ebean.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/app-instance")
public class AppInstanceController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(AppInstance.Uni.class) AppInstance appInstance) throws HttpRequestException, HttpRequestFormException {
    AppInstanceService appInstanceService = new AppInstanceService(AuthUser);
    Transaction transaction = DB.beginTransaction();
    try {
      // 检查用户是否有正在运行的app
      this.alreadyRunningApp(appInstance.application_id);
      InstanceImage instanceImage = InstanceImage.find.query().where()
        .eq("application_id", appInstance.application_id)
        .eq("busy", false)
        .setMaxRows(1)
        .order().desc("id")
        .findOne();

      if(instanceImage != null) {
        appInstance.setImage_id(instanceImage.image_id);
        appInstance.setApplication_id(instanceImage.application_id);
        appInstance.setStatus(ServerStatus.Pending);

        // 设置镜像为忙碌
        instanceImage.setBusy(true);
        instanceImage.save();

        appInstance = appInstanceService.create(appInstance);
        CreateResponse createResponse = new CreateResponse(appInstance);
        AliyunInstances instances = new AliyunInstances();
        instances.callToRunInstances(instanceImage, appInstance);
        transaction.commit();

        if(createResponse.code == 200) {
          List<AppInstance> list = AppInstance.find.query().where()
            .eq("image_id", appInstance.image_id)
            .eq("user_id", AuthUser.id)
            .eq("status", ServerStatus.Stopping).findList();
          list.forEach(ai -> {
            ai.setAuto_save(false);
            ai.save();
            try {
              instances.callToStopInstances(ai.instance_id);
            } catch (ClientException e) {
              e.printStackTrace();
            }
          });
        }
        return createResponse;
      } else {
        transaction.rollback();
        throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "暂时没有可用的资源，待资源空闲时将通过短信通知您！");
      }
    } catch (HttpRequestFormException e) {
      transaction.rollback();
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    } finally {
      transaction.end();  // rollback if not committed
    }

  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody AppInstanceModel appInstance) throws HttpRequestException, HttpRequestFormException {
    AppInstanceService appInstanceService = new AppInstanceService(AuthUser);
    Transaction transaction = DB.beginTransaction();
    try {
      AliyunInstances instances = new AliyunInstances();

      if(appInstance.status.trim().equals(ServerStatus.Stopping)) {
        AppInstance instance = appInstanceService.update(appInstance);
        UpdateResponse res = new UpdateResponse(instance);
        try {
          instances.callToStopInstances(instance.instance_id);
          InstanceImage image = InstanceImage.find.query().where()
            .eq("image_id", instance.image_id.trim())
            .findOne();
          if(image != null) {
            image.setBusy(false);
            image.save();
          } else {
            throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "找不到指定的APP");
          }
          transaction.commit();
        } catch (ServerException e) {
          e.printStackTrace();
          transaction.rollback();
        } catch (ClientException e) {
          transaction.rollback();
          instances.callToStopInstances(instance.instance_id);
          System.out.println("重试:" + e.getErrCode());
          System.out.println("ErrCode:" + e.getErrCode());
          System.out.println("ErrMsg:" + e.getErrMsg());
          System.out.println("RequestId:" + e.getRequestId());
        }
        return res;
      } else if(appInstance.status.trim().equals(ServerStatus.Running)) {
        // 检查用户是否有正在运行的app
        this.alreadyRunningApp(appInstance.application_id);
        AppInstance instance = appInstanceService.update(appInstance);
        UpdateResponse res = new UpdateResponse(instance);
        InstanceImage instanceImage = InstanceImage.find.query().where()
          .eq("application_id", appInstance.application_id)
          .eq("busy", false)
          .setMaxRows(1)
          .order().desc("id")
          .findOne();

        if(instanceImage != null) {
          AppInstance appIns = AppInstance.find.byId(appInstance.id);
          if(appIns != null) {
            appIns.setStatus(ServerStatus.Running);
            appIns.save();

            // 设置镜像为忙碌
            instanceImage.setBusy(true);
            instanceImage.save();

            instances.callToRestartInstances(appIns.instance_id);
            transaction.commit();
            return res;
          } else {
            throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "找不到指定保存的APP");
          }
        } else {
          transaction.rollback();
          throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "暂时没有可用的资源，待资源空闲时将通过短信通知您！");
        }
      }
    } catch (HttpRequestException | ClientException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
    return null;
  }

  // @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    AppInstanceService appInstanceService = new AppInstanceService(AuthUser);
    try {
      return new DeleteResponse(appInstanceService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    AppInstanceService appInstanceService = new AppInstanceService(AuthUser);
    DataResource dataResource = appInstanceService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

  /**
   * 检查释放有正在运行的APP
   * @throws HttpRequestException
   */
  private void alreadyRunningApp(Long appID) throws HttpRequestException {
    int count = AppInstance.find.query().where()
      .eq("user_id", AuthUser.id)
      .ne("status", ServerStatus.Stopping)
      .setMaxRows(1)
      .findCount();
    if(count > 0) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "请先在个人中心停止运行中的应用！");
    }

    int usableImage = InstanceImage.find.query().where()
      .eq("application_id", appID)
      .eq("busy", false)
      .findCount();

    if(usableImage == 0) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "应用资源暂时全部使用中，可用时将以短信通知您！");
    }
  }

}
