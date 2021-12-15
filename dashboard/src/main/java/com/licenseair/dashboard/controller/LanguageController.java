package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Language;
import com.licenseair.backend.domainModel.LanguageModel;
import com.licenseair.dashboard.service.LanguageService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/language")
public class LanguageController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Language.Uni.class) Language language) throws HttpRequestFormException {
    LanguageService languageService = new LanguageService(AuthUser);
    try {
      return new CreateResponse(languageService.create(language));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody LanguageModel language) throws HttpRequestException, HttpRequestFormException {
    LanguageService languageService = new LanguageService(AuthUser);
    try {
      return new UpdateResponse(languageService.update(language));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    LanguageService languageService = new LanguageService(AuthUser);
    try {
      return new DeleteResponse(languageService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    LanguageService languageService = new LanguageService(AuthUser);
    DataResource dataResource = languageService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
