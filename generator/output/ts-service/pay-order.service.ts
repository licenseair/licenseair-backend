import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {PayOrderModel} from '@app/models/pay-order.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class PayOrderService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/pay-order/get", params);
  }

  create = (model: PayOrderModel) => {
    return this.http.post("/pay-order/create", model);
  }

  update = (model: PayOrderModel) => {
    return this.http.post("/pay-order/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/pay-order/delete", {id: id});
  }
}
