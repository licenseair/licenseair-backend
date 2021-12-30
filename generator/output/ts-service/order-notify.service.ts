import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {OrderNotifyModel} from '@app/models/order-notify.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class OrderNotifyService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/order-notify/get", params);
  }

  create = (model: OrderNotifyModel) => {
    return this.http.post("/order-notify/create", model);
  }

  update = (model: OrderNotifyModel) => {
    return this.http.post("/order-notify/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/order-notify/delete", {id: id});
  }
}
