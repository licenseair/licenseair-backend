package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Platform;
import com.licenseair.backend.domainModel.PlatformModel;
import com.licenseair.dashboard.service.PlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/platform")
public class PlatformController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Platform.Uni.class) Platform platform) throws HttpRequestFormException {
    PlatformService platformService = new PlatformService(AuthUser);
    try {
      return new CreateResponse(platformService.create(platform));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody PlatformModel platform) throws HttpRequestException, HttpRequestFormException {
    PlatformService platformService = new PlatformService(AuthUser);
    try {
      return new UpdateResponse(platformService.update(platform));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    PlatformService platformService = new PlatformService(AuthUser);
    try {
      return new DeleteResponse(platformService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    PlatformService platformService = new PlatformService(AuthUser);
    DataResource dataResource = platformService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
