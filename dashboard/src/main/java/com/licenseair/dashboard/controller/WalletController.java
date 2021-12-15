package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.*;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Wallet;
import com.licenseair.backend.domainModel.WalletModel;
import com.licenseair.dashboard.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by foxsir
 */
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(Wallet.Uni.class) Wallet wallet) throws HttpRequestFormException {
    WalletService walletService = new WalletService(AuthUser);
    try {
      return new CreateResponse(walletService.create(wallet));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody WalletModel wallet) throws HttpRequestException, HttpRequestFormException {
    WalletService walletService = new WalletService(AuthUser);
    try {
      return new UpdateResponse(walletService.update(wallet));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    WalletService walletService = new WalletService(AuthUser);
    try {
      return new DeleteResponse(walletService.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    WalletService walletService = new WalletService(AuthUser);
    DataResource dataResource = walletService.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
