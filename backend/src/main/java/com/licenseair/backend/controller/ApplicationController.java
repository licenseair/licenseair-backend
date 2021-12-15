package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Application;
import com.licenseair.backend.domainModel.ApplicationModel;
import com.licenseair.backend.service.ApplicationService;
import com.licenseair.backend.commons.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/application")
public class ApplicationController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Application.Uni.class) Application application) throws HttpRequestFormException {
    ApplicationService applicationService = new ApplicationService(AuthUser);
    try {
      return new CreateResponse(applicationService.create(application));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody ApplicationModel application) throws HttpRequestException, HttpRequestFormException {
    ApplicationService applicationService = new ApplicationService(AuthUser);
    try {
      return new UpdateResponse(applicationService.update(application));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    ApplicationService applicationService = new ApplicationService(AuthUser);
    try {
      return new DeleteResponse(applicationService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    ApplicationService applicationService = new ApplicationService(AuthUser);
    DataResource dataResource = applicationService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
