package com.licenseair.dashboard.library;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.licenseair.backend.commons.model.QueryResponse;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.domain.SmsLog;
import io.ebean.DB;
import io.ebean.Transaction;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AliSMS {
  public Map<String, ConfigType> config = new HashMap<>(){{
    put("register", new ConfigType("SMS_118905003", Map.of("code", AliSMS.getRandom()))); // 注册账号
    put("login", new ConfigType("SMS_118905005", Map.of("code", AliSMS.getRandom()))); // 登录网站
    put("reset_password", new ConfigType("SMS_118905002", Map.of("code", AliSMS.getRandom()))); // 重置密码
    put("container_error", new ConfigType("SMS_175571900", Map.of("code", AliSMS.getRandom()))); // 容器启动失败
    put("remove_account", new ConfigType("SMS_211490869", Map.of("code", AliSMS.getRandom()))); // 注销账号
    put("cert_pass", new ConfigType("SMS_211481052", Map.of("code", AliSMS.getRandom()))); // 认证通过
    put("cert_failed", new ConfigType("SMS_211491063", Map.of("code", AliSMS.getRandom()))); // 认证失败
    put("course_pass", new ConfigType("SMS_213693342", Map.of("code", AliSMS.getRandom()))); // 教程发布成功
    put("course_failed", new ConfigType("SMS_213773280", Map.of("code", AliSMS.getRandom()))); // 教程发布失败
    put("cash_out_apply", new ConfigType("SMS_214515275", Map.of("code", AliSMS.getRandom()))); // 申请提现
    put("cash_out_pass", new ConfigType("SMS_214525260", Map.of("code", AliSMS.getRandom()))); // 提现成功
    put("cash_out_failed", new ConfigType("SMS_214520281", Map.of("code", AliSMS.getRandom()))); // 提现失败
  }};

  public static class ConfigShort {
    public static String REGISTER = "register";
    public static String LOGIN = "login";
    public static String RESET_PASSWORD = "reset_password";
    public static String CONTAINER_ERROR = "container_error";
    public static String REMOVE_ACCOUNT = "remove_account";
    public static String CERT_PASS = "cert_pass";
    public static String CERT_FAILED = "cert_failed";
    public static String COURSE_PASS = "course_pass";
    public static String COURSE_FAILED = "course_failed";
    public static String CASH_OUT_APPLY = "cash_out_apply";
    public static String CASH_OUT_PASS = "cash_out_pass";
    public static String CASH_OUT_FAILED = "cash_out_failed";
  }

  public class ConfigType {
    public final String template;
    public final Map<String, Integer> vars;

    public ConfigType(String template, Map<String, Integer> vars) {
      this.template = template;
      this.vars = vars;
    }
  }

  public QueryResponse send(String mobile, ConfigType scene) {
    DefaultProfile profile = DefaultProfile.getProfile(
      "cn-hangzhou",
      "LTAI8vophXPZveg4",
      "0CtYwpjahQ44f9mkiG1ILZMV5VpoIK"
    );

    IAcsClient client = new DefaultAcsClient(profile);

    CommonRequest smsRequest = new CommonRequest();
    smsRequest.setSysMethod(MethodType.POST);
    smsRequest.setSysDomain("dysmsapi.aliyuncs.com");
    smsRequest.setSysVersion("2017-05-25");
    smsRequest.setSysAction("SendSms");
    smsRequest.putQueryParameter("RegionId", "cn-hangzhou");
    smsRequest.putQueryParameter("SignName", "编程学院");

    smsRequest.putQueryParameter("TemplateCode", scene.template);
    smsRequest.putQueryParameter("PhoneNumbers", mobile);
    Gson gson = new Gson();
    smsRequest.putQueryParameter("TemplateParam", gson.toJson(scene.vars));

    Timestamp m2 = new Timestamp(System.currentTimeMillis() - 1000*60*2);

    List<SmsLog> check = SmsLog.find.query().where()
      .eq("mobile", mobile)
      .gt("created_at", m2)
      .orderBy("id DESC")
      .findList();

    if(check.stream().count() != 0) {
      return new QueryResponse(Map.of(
        "seconds", Duration.between(m2.toLocalDateTime(), check.get(0).created_at.toLocalDateTime()).toMillis() / 1000
      ));
    }
    String data = "";
    try {
      Transaction transaction = DB.beginTransaction();
      SmsLog smsLog = new SmsLog();
      smsLog.mobile = mobile;
      smsLog.code = scene.vars.get("code");
      smsLog.save();

      if(smsLog.id > 0) {
        CommonResponse response = client.getCommonResponse(smsRequest);
        data = response.getData();
        transaction.commit();
      } else {
        transaction.rollback();
        throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "短信发送失败");
      }
    } catch (ServerException e) {
      e.printStackTrace();
    } catch (ClientException e) {
      e.printStackTrace();
    } catch (HttpRequestException e) {
      e.printStackTrace();
    }

    return new QueryResponse(data);
  }

  private static Integer getRandom() {
    int min = 1000;
    int max = 9999;
    Random rand = new Random();
    Integer number = rand.nextInt(max);
    if (number < min) {
      number = number + min;
    }
    return number;
  }
}
