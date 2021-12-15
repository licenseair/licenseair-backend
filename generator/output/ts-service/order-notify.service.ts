import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {OrderNotifyModel} from '@app/models/order-notify.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderNotifyService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public orderNotify: OrderNotifyModel;
  public orderNotifyList: OrderNotifyModel[];

  private orderNotifySource = new Subject<OrderNotifyModel>();
  // Observable string streams
  orderNotify$ = this.orderNotifySource.asObservable();

  private orderNotifyListSource = new Subject<OrderNotifyModel[]>();
  // Observable string streams
  orderNotifyList$ = this.orderNotifyListSource.asObservable();

  setOrderNotify(orderNotify: OrderNotifyModel) {
    this.orderNotify = orderNotify;
    this.orderNotifySource.next(orderNotify);
  }

  setOrderNotifyList(orderNotifyList: OrderNotifyModel[] = []) {
    this.orderNotifyList = orderNotifyList;
    this.orderNotifyListSource.next(orderNotifyList);
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
