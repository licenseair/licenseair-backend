import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {WalletLogModel} from '@app/models/wallet-log.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class WalletLogService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/wallet-log/get", params);
  }

  create = (model: WalletLogModel) => {
    return this.http.post("/wallet-log/create", model);
  }

  update = (model: WalletLogModel) => {
    return this.http.post("/wallet-log/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/wallet-log/delete", {id: id});
  }
}
