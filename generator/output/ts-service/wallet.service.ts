import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {WalletModel} from '@app/models/wallet.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class WalletService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/wallet/get", params);
  }

  create = (model: WalletModel) => {
    return this.http.post("/wallet/create", model);
  }

  update = (model: WalletModel) => {
    return this.http.post("/wallet/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/wallet/delete", {id: id});
  }
}
