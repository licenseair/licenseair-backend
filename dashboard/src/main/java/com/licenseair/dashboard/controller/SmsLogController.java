package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.QueryResponse;
import com.licenseair.backend.library.AliSMS;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/sms-log")
public class SmsLogController extends BaseController {

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated Map<String, String> params) {
    AliSMS aliSMS = new AliSMS();
    return aliSMS.send(params.get("mobile"), aliSMS.config.get("login"));
  }

}
