import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {WalletLogModel} from '@app/models/wallet-log.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WalletLogService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public walletLog: WalletLogModel;
  public walletLogList: WalletLogModel[];

  private walletLogSource = new Subject<WalletLogModel>();
  // Observable string streams
  walletLog$ = this.walletLogSource.asObservable();

  private walletLogListSource = new Subject<WalletLogModel[]>();
  // Observable string streams
  walletLogList$ = this.walletLogListSource.asObservable();

  setWalletLog(walletLog: WalletLogModel) {
    this.walletLog = walletLog;
    this.walletLogSource.next(walletLog);
  }

  setWalletLogList(walletLogList: WalletLogModel[] = []) {
    this.walletLogList = walletLogList;
    this.walletLogListSource.next(walletLogList);
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
