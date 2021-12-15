import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {SmsLogModel} from '@app/models/sms-log.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SmsLogService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public smsLog: SmsLogModel;
  public smsLogList: SmsLogModel[];

  private smsLogSource = new Subject<SmsLogModel>();
  // Observable string streams
  smsLog$ = this.smsLogSource.asObservable();

  private smsLogListSource = new Subject<SmsLogModel[]>();
  // Observable string streams
  smsLogList$ = this.smsLogListSource.asObservable();

  setSmsLog(smsLog: SmsLogModel) {
    this.smsLog = smsLog;
    this.smsLogSource.next(smsLog);
  }

  setSmsLogList(smsLogList: SmsLogModel[] = []) {
    this.smsLogList = smsLogList;
    this.smsLogListSource.next(smsLogList);
  }

  query = (params: object) => {
    return this.http.post("/sms-log/get", params);
  }

  create = (model: SmsLogModel) => {
    return this.http.post("/sms-log/create", model);
  }

  update = (model: SmsLogModel) => {
    return this.http.post("/sms-log/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/sms-log/delete", {id: id});
  }
}
