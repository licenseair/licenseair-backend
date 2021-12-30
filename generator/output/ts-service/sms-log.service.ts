import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {SmsLogModel} from '@app/models/sms-log.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class SmsLogService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
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
