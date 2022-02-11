package com.licenseair.backend.controller;

import com.aliyun.tea.TeaModel;
import com.google.gson.Gson;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.domain.*;

import com.licenseair.backend.library.AppManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/buy")
public class BuyController extends BaseController {
  @Value("${site.payReturn}")
  private String payReturn;

  @Value("${site.payOk}")
  private String payOk;

  @Value("${site.payError}")
  private String payError;

  @RequestMapping(value = "/return-notify")
  public RedirectView returnNotify(@RequestParam Map<String,String> params) throws Exception {
    Notify res = getNotify(params);

    if(res.notify) {
      // return new RedirectView(String.format("%s/%s/%s", this.payOk, res.payOrder.id.trim(), res.payOrder.subject_id));
      return new RedirectView(String.format("%s/%s/%s", this.payOk, res.payOrder.id, res.payOrder.subject_id));
    } else {
      return new RedirectView(this.payError + res.error);
    }
  }

  @RequestMapping(value = "/notify")
  public void notify(@RequestParam Map<String,String> params) throws Exception {
    Notify res = getNotify(params);

    if(!res.notify) {
      logger.error("支付错误：" + res.error);
    }
  }

  private static class Notify {
    public PayOrder payOrder;
    public boolean notify;
    public String error;

    public Notify(PayOrder payOrder, boolean notify, String error) {
      this.payOrder = payOrder;
      this.notify = notify;
      this.error = error;
    }
  }

  private Notify getNotify(Map<String,String> params) {
    boolean notify = false;
    String error = "";
    PayOrder payOrder = null;
    try {
      OrderNotify orderNotify = new OrderNotify();
      orderNotify.charset = params.get("charset");
      orderNotify.out_trade_no = params.get("out_trade_no");
      orderNotify.method = params.get("method");
      orderNotify.total_amount = params.get("total_amount");
      orderNotify.sign = params.get("sign");
      orderNotify.trade_no = params.get("trade_no");
      orderNotify.auth_app_id = params.get("auth_app_id");
      orderNotify.version = params.get("version");
      orderNotify.app_id = params.get("app_id");
      orderNotify.sign_type = params.get("sign_type");
      orderNotify.seller_id = params.get("seller_id");

      if (params.get("notify_time") != null) {
        orderNotify.timestamp = Timestamp.valueOf(params.get("notify_time"));
      } else {
        orderNotify.timestamp = Timestamp.valueOf(params.get("timestamp"));
      }

      if ( Payment.Common().verifyNotify(params) ) {
        payOrder = PayOrder.find.query().where()
          .eq("trade_no", orderNotify.out_trade_no)
          .findOne();

        if(payOrder != null && payOrder.is_pay == false) {
          orderNotify.save();
          payOrder.setIs_pay(true);
          payOrder.setPay_time(orderNotify.timestamp);
          payOrder.save();
          notify = true;

          /**
           * 支付完成，启动APP
           * subject_id = AppInstance.id
           */
          // payOrder.subject_id
          AppManager appManager = new AppManager();

        } else if(payOrder != null && payOrder.is_pay == true) {
          // 已经支付过
          notify = true;
        } else {
          // 出错
          notify = false;
          error = URLEncoder.encode("找不到这个订单！如已经支付请联系管理员。", StandardCharsets.UTF_8.toString());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      notify = false;
      error = e.getMessage();
    }
    this.payAfter(payOrder);

    return new Notify(payOrder, notify, error);
  }

  /**
   * 支付后启动虚拟机
   * @param payOrder 订单信息
   */
  private void payAfter(PayOrder payOrder) {
    Gson gson = new Gson();
    if (payOrder != null ) {

    }
  }

  @PostMapping(path = "/get-form")
  public String pay(@RequestBody PayOrder payOrder) throws HttpRequestException {
    AppInstance appInstance = AppInstance.find.byId(payOrder.subject_id);
    assert appInstance != null;
    Application application = Application.find.byId(appInstance.application_id);
    assert application != null;

    return this.getForm(application, appInstance, payOrder.trade_no.trim());
  }

  private String getForm(Application application, AppInstance appInstance, String tradeNo) {
    String subject = String.format("%s: 使用%s小时", application.name, appInstance.hours);

    // 支付表单
    String form = "";
    try {
      // 2. 发起API调用
      TeaModel response = null;
      response = Factory.Payment.Page()
        .pay(subject, tradeNo, application.price.toString(), this.payReturn);

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
    HashMap<String, String> res = new HashMap<>();
    res.put("form", form);

    return form;
  }

}

