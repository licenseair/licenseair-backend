import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {PayOrderModel} from '@app/models/pay-order.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PayOrderService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public payOrder: PayOrderModel;
  public payOrderList: PayOrderModel[];

  private payOrderSource = new Subject<PayOrderModel>();
  // Observable string streams
  payOrder$ = this.payOrderSource.asObservable();

  private payOrderListSource = new Subject<PayOrderModel[]>();
  // Observable string streams
  payOrderList$ = this.payOrderListSource.asObservable();

  setPayOrder(payOrder: PayOrderModel) {
    this.payOrder = payOrder;
    this.payOrderSource.next(payOrder);
  }

  setPayOrderList(payOrderList: PayOrderModel[] = []) {
    this.payOrderList = payOrderList;
    this.payOrderListSource.next(payOrderList);
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
