package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.SessionLog;
import com.licenseair.backend.domainModel.SessionLogModel;
import com.licenseair.backend.service.SessionLogService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/session-log")
public class SessionLogController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(SessionLog.Uni.class) SessionLog sessionLog) throws HttpRequestFormException {
    SessionLogService sessionLogService = new SessionLogService(AuthUser);
    try {
      return new CreateResponse(sessionLogService.create(sessionLog));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody SessionLogModel sessionLog) throws HttpRequestException, HttpRequestFormException {
    SessionLogService sessionLogService = new SessionLogService(AuthUser);
    try {
      return new UpdateResponse(sessionLogService.update(sessionLog));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    SessionLogService sessionLogService = new SessionLogService(AuthUser);
    try {
      return new DeleteResponse(sessionLogService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    SessionLogService sessionLogService = new SessionLogService(AuthUser);
    DataResource dataResource = sessionLogService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
