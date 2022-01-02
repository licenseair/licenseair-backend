package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.AppInstance;
import com.licenseair.backend.domainModel.AppInstanceModel;
import com.licenseair.backend.service.AppInstanceService;
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
  private CreateResponse create(@RequestBody @Validated(AppInstance.Uni.class) AppInstance appInstance) throws HttpRequestFormException {
    AppInstanceService appInstanceService = new AppInstanceService(AuthUser);
    try {
      return new CreateResponse(appInstanceService.create(appInstance));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody AppInstanceModel appInstance) throws HttpRequestException, HttpRequestFormException {
    AppInstanceService appInstanceService = new AppInstanceService(AuthUser);
    try {
      return new UpdateResponse(appInstanceService.update(appInstance));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
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

}
