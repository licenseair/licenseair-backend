package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.PayOrder;
import com.licenseair.backend.domainModel.PayOrderModel;
import com.licenseair.dashboard.service.PayOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/pay-order")
public class PayOrderController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(PayOrder.Uni.class) PayOrder payOrder) throws HttpRequestFormException {
    PayOrderService payOrderService = new PayOrderService(AuthUser);
    try {
      return new CreateResponse(payOrderService.create(payOrder));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody PayOrderModel payOrder) throws HttpRequestException, HttpRequestFormException {
    PayOrderService payOrderService = new PayOrderService(AuthUser);
    try {
      return new UpdateResponse(payOrderService.update(payOrder));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    PayOrderService payOrderService = new PayOrderService(AuthUser);
    try {
      return new DeleteResponse(payOrderService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    PayOrderService payOrderService = new PayOrderService(AuthUser);
    DataResource dataResource = payOrderService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
