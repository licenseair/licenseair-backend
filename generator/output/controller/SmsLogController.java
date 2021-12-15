package com.licenseair.backend.controller;

import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.SmsLog;
import com.licenseair.backend.domainModel.SmsLogModel;
import com.licenseair.backend.service.SmsLogService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/sms-log")
public class SmsLogController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(SmsLog.Uni.class) SmsLog smsLog) throws HttpRequestFormException {
    SmsLogService smsLogService = new SmsLogService(AuthUser);
    try {
      return new CreateResponse(smsLogService.create(smsLog));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody SmsLogModel smsLog) throws HttpRequestException, HttpRequestFormException {
    SmsLogService smsLogService = new SmsLogService(AuthUser);
    try {
      return new UpdateResponse(smsLogService.update(smsLog));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    SmsLogService smsLogService = new SmsLogService(AuthUser);
    try {
      return new DeleteResponse(smsLogService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    SmsLogService smsLogService = new SmsLogService(AuthUser);
    DataResource dataResource = smsLogService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
