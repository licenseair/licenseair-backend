package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.InstanceImage;
import com.licenseair.backend.domainModel.InstanceImageModel;
import com.licenseair.dashboard.service.InstanceImageService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/instance-image")
public class InstanceImageController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(InstanceImage.Uni.class) InstanceImage instanceImage) throws HttpRequestFormException {
    InstanceImageService instanceImageService = new InstanceImageService(AuthUser);
    try {
      return new CreateResponse(instanceImageService.create(instanceImage));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody InstanceImageModel instanceImage) throws HttpRequestException, HttpRequestFormException {
    InstanceImageService instanceImageService = new InstanceImageService(AuthUser);
    try {
      return new UpdateResponse(instanceImageService.update(instanceImage));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    InstanceImageService instanceImageService = new InstanceImageService(AuthUser);
    try {
      return new DeleteResponse(instanceImageService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    InstanceImageService instanceImageService = new InstanceImageService(AuthUser);
    DataResource dataResource = instanceImageService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
