package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.City;
import com.licenseair.backend.domainModel.CityModel;
import com.licenseair.dashboard.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/city")
public class CityController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(City.Uni.class) City city) throws HttpRequestFormException {
    CityService cityService = new CityService(AuthUser);
    try {
      return new CreateResponse(cityService.create(city));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody CityModel city) throws HttpRequestException, HttpRequestFormException {
    CityService cityService = new CityService(AuthUser);
    try {
      return new UpdateResponse(cityService.update(city));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    CityService cityService = new CityService(AuthUser);
    try {
      return new DeleteResponse(cityService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    CityService cityService = new CityService(AuthUser);
    DataResource dataResource = cityService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
