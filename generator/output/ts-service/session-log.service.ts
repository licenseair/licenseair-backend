import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {SessionLogModel} from '@app/models/session-log.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class SessionLogService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/session-log/get", params);
  }

  create = (model: SessionLogModel) => {
    return this.http.post("/session-log/create", model);
  }

  update = (model: SessionLogModel) => {
    return this.http.post("/session-log/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/session-log/delete", {id: id});
  }
}
