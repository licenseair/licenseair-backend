import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {WalletModel} from '@app/models/wallet.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WalletService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public wallet: WalletModel;
  public walletList: WalletModel[];

  private walletSource = new Subject<WalletModel>();
  // Observable string streams
  wallet$ = this.walletSource.asObservable();

  private walletListSource = new Subject<WalletModel[]>();
  // Observable string streams
  walletList$ = this.walletListSource.asObservable();

  setWallet(wallet: WalletModel) {
    this.wallet = wallet;
    this.walletSource.next(wallet);
  }

  setWalletList(walletList: WalletModel[] = []) {
    this.walletList = walletList;
    this.walletListSource.next(walletList);
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
