package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Admin;
import com.licenseair.backend.domainModel.AdminModel;
import com.licenseair.dashboard.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Admin.Uni.class) Admin admin) throws HttpRequestFormException {
    AdminService adminService = new AdminService(AuthUser);
    try {
      return new CreateResponse(adminService.create(admin));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody AdminModel admin) throws HttpRequestException, HttpRequestFormException {
    AdminService adminService = new AdminService(AuthUser);
    try {
      return new UpdateResponse(adminService.update(admin));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    AdminService adminService = new AdminService(AuthUser);
    try {
      return new DeleteResponse(adminService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    AdminService adminService = new AdminService(AuthUser);
    DataResource dataResource = adminService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
