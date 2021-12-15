package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Profile;
import com.licenseair.backend.domainModel.ProfileModel;
import com.licenseair.dashboard.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/profile")
public class ProfileController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Profile.Uni.class) Profile profile) throws HttpRequestFormException {
    ProfileService profileService = new ProfileService(AuthUser);
    try {
      return new CreateResponse(profileService.create(profile));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody ProfileModel profile) throws HttpRequestException, HttpRequestFormException {
    ProfileService profileService = new ProfileService(AuthUser);
    try {
      return new UpdateResponse(profileService.update(profile));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    ProfileService profileService = new ProfileService(AuthUser);
    try {
      return new DeleteResponse(profileService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    ProfileService profileService = new ProfileService(AuthUser);
    DataResource dataResource = profileService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
