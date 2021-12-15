package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.WalletLog;
import com.licenseair.backend.domainModel.WalletLogModel;
import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.service.WalletLogService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/wallet-log")
public class WalletLogController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(WalletLog.Uni.class) WalletLog walletLog) throws HttpRequestFormException {
    WalletLogService walletLogService = new WalletLogService(AuthUser);
    try {
      return new CreateResponse(walletLogService.create(walletLog));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody WalletLogModel walletLog) throws HttpRequestException, HttpRequestFormException {
    WalletLogService walletLogService = new WalletLogService(AuthUser);
    try {
      return new UpdateResponse(walletLogService.update(walletLog));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    WalletLogService walletLogService = new WalletLogService(AuthUser);
    try {
      return new DeleteResponse(walletLogService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    WalletLogService walletLogService = new WalletLogService(AuthUser);
    DataResource dataResource = walletLogService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
