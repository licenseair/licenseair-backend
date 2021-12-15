package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.service.CategoryService;
import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.domain.Category;
import com.licenseair.backend.domainModel.CategoryModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Category.Uni.class) Category category) throws HttpRequestFormException {
    CategoryService categoryService = new CategoryService(AuthUser);
    try {
      return new CreateResponse(categoryService.create(category));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody CategoryModel category) throws HttpRequestException, HttpRequestFormException {
    CategoryService categoryService = new CategoryService(AuthUser);
    try {
      return new UpdateResponse(categoryService.update(category));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    CategoryService categoryService = new CategoryService(AuthUser);
    try {
      return new DeleteResponse(categoryService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    CategoryService categoryService = new CategoryService(AuthUser);
    DataResource dataResource = categoryService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
