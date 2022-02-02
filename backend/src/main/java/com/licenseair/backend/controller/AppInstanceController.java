package com.licenseair.backend.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.aliyun.tea.TeaModel;
import com.google.gson.Gson;
import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Application;
import com.licenseair.backend.domain.InstanceImage;
import com.licenseair.backend.domain.PayOrder;
import com.licenseair.backend.service.AppInstanceService;
import com.licenseair.backend.domain.AppInstance;
import com.licenseair.backend.domainModel.AppInstanceModel;
import io.ebean.DB;
import io.ebean.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/app-instance")
public class AppInstanceController extends BaseController {

  @Value("${site.payReturn}")
  private String payReturn;

  @Value("${site.payOk}")
  private String payOk;

  @Value("${site.payError}")
  private String payError;

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

        Application app = Application.find.byId(appInstance.application_id);
        if(app != null) {
          createResponse.extra  = this.getForm(app, appInstance);
        }

        transaction.commit();

        if(createResponse.code == 200) {
          List<AppInstance> list = AppInstance.find.query().where()
            .eq("image_id", appInstance.image_id)
            .eq("user_id", AuthUser.id)
            .eq("status", ServerStatus.Stopping).findList();
          list.forEach(ai -> {
            ai.setAuto_save(false);
            ai.save();
          });
          return createResponse;
        } else {
          // 余额不足100元，应通知管理员
          instanceImage.setBusy(false);
          instanceImage.save();
          appInstance.delete();

          throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "资源暂时不可用，请稍后再试！");
        }
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
      if(appInstance.status.trim().equals(ServerStatus.Stopping)) {
        AppInstance instance = appInstanceService.update(appInstance);
        UpdateResponse res = new UpdateResponse(instance);
        InstanceImage image = InstanceImage.find.query().where()
          .eq("image_id", instance.image_id.trim())
          .findOne();
        if(image != null) {
          image.setBusy(false);
          image.save();
        } else {
          transaction.rollback();
          throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "找不到指定的APP");
        }
        transaction.commit();
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
            transaction.commit();
            return res;
          } else {
            transaction.rollback();
            throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "找不到指定保存的APP");
          }
        } else {
          transaction.rollback();
          throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "暂时没有可用的资源，待资源空闲时将通过短信通知您！");
        }
      }
    } catch (HttpRequestException e) {
      transaction.rollback();
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

  private String getForm(Application application, AppInstance appInstance) {
    String subject = String.format("%s: 使用%s小时", application.name, appInstance.hours);

    // 支付表单
    String form = "";
    try {
      // 2. 发起API调用
      TeaModel response = null;
      response = Factory.Payment.Page()
        .pay(subject, getTradeNo(application, appInstance), application.price.toString(), this.payReturn);

      // 3. 处理响应或异常
      if (ResponseChecker.success(response)) {
        form = response.toMap().get("body").toString();
      } else {
        logger.debug("调用失败，原因：" + response.toString() + "，" + response.toString());
      }
    } catch (Exception e) {
      logger.debug("调用遭遇异常，原因：" + e.getMessage());
      throw new RuntimeException(e.getMessage(), e);
    }

    return form;
  }

  /**
   * 生成订单号
   * @return String
   */
  private String getTradeNo(Application application, AppInstance appInstance) {
    String tradeNo = "";
    BigDecimal price = application.price.multiply(new BigDecimal(appInstance.hours));
    Timestamp fiveMinutes = new Timestamp(System.currentTimeMillis() - 60000 * 5);
    PayOrder unpaidOrder = PayOrder.find.query()
      .forUpdate()
      .where()
      .eq("user_id", AuthUser.id)
      .eq("is_pay", false)
      .eq("price", price)
      .eq("subject_id", appInstance.id)
      // 超过5分钟作废
      .gt("created_at", fiveMinutes)
      .findOne();

    Gson gson = new Gson();
    if(unpaidOrder != null) {
      tradeNo = unpaidOrder.trade_no;
      unpaidOrder.setSnapshot(gson.toJson(appInstance));
      unpaidOrder.setPrice(price);
      unpaidOrder.save();
    } else {
      Calendar cale = Calendar.getInstance();
      List<String> list = Arrays.asList(
        Integer.toString(cale.get(Calendar.YEAR)),
        Integer.toString(cale.get(Calendar.MONTH)),
        Integer.toString(cale.get(Calendar.DATE)),
        Integer.toString(cale.get(Calendar.HOUR)),
        Integer.toString(cale.get(Calendar.MINUTE)),
        Integer.toString(cale.get(Calendar.SECOND)),
        UUID.randomUUID().toString()
      );
      tradeNo = String.join(":", list);

      PayOrder payOrder = new PayOrder();
      payOrder.setUser_id(AuthUser.id);
      payOrder.setTrade_no(tradeNo);
      payOrder.setSubject_id(appInstance.id);
      payOrder.setSnapshot(gson.toJson(appInstance));
      payOrder.setPrice(price);
      payOrder.setIs_pay(false);
      payOrder.save();
    }

    return tradeNo.trim();
  }

}
