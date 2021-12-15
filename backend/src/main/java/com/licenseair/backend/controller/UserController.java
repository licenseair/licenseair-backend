package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.service.UserService;
import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.domain.User;
import com.licenseair.backend.domainModel.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(User.Uni.class) User user) throws HttpRequestFormException {
    UserService userService = new UserService(AuthUser);
    try {
      return new CreateResponse(userService.create(user));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody UserModel user) throws HttpRequestException, HttpRequestFormException {
    UserService userService = new UserService(AuthUser);
    try {
      return new UpdateResponse(userService.update(user));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    UserService userService = new UserService(AuthUser);
    try {
      return new DeleteResponse(userService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    UserService userService = new UserService(AuthUser);
    DataResource dataResource = userService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
