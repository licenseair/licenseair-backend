package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.OrderNotify;
import com.licenseair.backend.domainModel.OrderNotifyModel;
import com.licenseair.backend.service.OrderNotifyService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/order-notify")
public class OrderNotifyController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(OrderNotify.Uni.class) OrderNotify orderNotify) throws HttpRequestFormException {
    OrderNotifyService orderNotifyService = new OrderNotifyService(AuthUser);
    try {
      return new CreateResponse(orderNotifyService.create(orderNotify));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody OrderNotifyModel orderNotify) throws HttpRequestException, HttpRequestFormException {
    OrderNotifyService orderNotifyService = new OrderNotifyService(AuthUser);
    try {
      return new UpdateResponse(orderNotifyService.update(orderNotify));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    OrderNotifyService orderNotifyService = new OrderNotifyService(AuthUser);
    try {
      return new DeleteResponse(orderNotifyService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    OrderNotifyService orderNotifyService = new OrderNotifyService(AuthUser);
    DataResource dataResource = orderNotifyService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
