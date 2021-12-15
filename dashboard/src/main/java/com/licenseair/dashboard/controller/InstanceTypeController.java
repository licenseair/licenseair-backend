package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.InstanceType;
import com.licenseair.backend.domainModel.InstanceTypeModel;
import com.licenseair.dashboard.service.InstanceTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/instance-type")
public class InstanceTypeController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(InstanceType.Uni.class) InstanceType instanceType) throws HttpRequestFormException {
    InstanceTypeService instanceTypeService = new InstanceTypeService(AuthUser);
    try {
      return new CreateResponse(instanceTypeService.create(instanceType));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody InstanceTypeModel instanceType) throws HttpRequestException, HttpRequestFormException {
    InstanceTypeService instanceTypeService = new InstanceTypeService(AuthUser);
    try {
      return new UpdateResponse(instanceTypeService.update(instanceType));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    InstanceTypeService instanceTypeService = new InstanceTypeService(AuthUser);
    try {
      return new DeleteResponse(instanceTypeService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    InstanceTypeService instanceTypeService = new InstanceTypeService(AuthUser);
    DataResource dataResource = instanceTypeService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
