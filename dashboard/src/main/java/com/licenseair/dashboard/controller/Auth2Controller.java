package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Auth2;
import com.licenseair.backend.domainModel.Auth2Model;
import com.licenseair.dashboard.service.Auth2Service;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/auth2")
public class Auth2Controller extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Auth2.Uni.class) Auth2 auth2) throws HttpRequestFormException {
    Auth2Service auth2Service = new Auth2Service(AuthUser);
    try {
      return new CreateResponse(auth2Service.create(auth2));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody Auth2Model auth2) throws HttpRequestException, HttpRequestFormException {
    Auth2Service auth2Service = new Auth2Service(AuthUser);
    try {
      return new UpdateResponse(auth2Service.update(auth2));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    Auth2Service auth2Service = new Auth2Service(AuthUser);
    try {
      return new DeleteResponse(auth2Service.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    Auth2Service auth2Service = new Auth2Service(AuthUser);
    DataResource dataResource = auth2Service.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
