package com.licenseair.backend.controller;

import com.aliyuncs.exceptions.ClientException;
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
      this.alreadyRunningApp();
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

        CreateResponse createResponse = new CreateResponse(appInstanceService.create(appInstance));
        AliyunInstances instances = new AliyunInstances();
        instances.callToRunInstances(instanceImage, appInstance);
        transaction.commit();
        return createResponse;
      } else {
        transaction.rollback();
        throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "创建失败！");
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
    try {
      AppInstance instance = appInstanceService.update(appInstance);
      UpdateResponse res = new UpdateResponse(instance);
      AliyunInstances instances = new AliyunInstances();
      instances.callToStopInstances(instance.instance_id);
      return res;
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
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
  private void alreadyRunningApp() throws HttpRequestException {
    int count = AppInstance.find.query().where()
      .eq("user_id", AuthUser.id)
      .ne("status", ServerStatus.Stopping)
      .setMaxRows(1)
      .findCount();

    if(count > 0) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "请先在个人中心停止运行中的应用！");
    }
  }

}
