import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {SessionLogModel} from '@app/models/session-log.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionLogService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public sessionLog: SessionLogModel;
  public sessionLogList: SessionLogModel[];

  private sessionLogSource = new Subject<SessionLogModel>();
  // Observable string streams
  sessionLog$ = this.sessionLogSource.asObservable();

  private sessionLogListSource = new Subject<SessionLogModel[]>();
  // Observable string streams
  sessionLogList$ = this.sessionLogListSource.asObservable();

  setSessionLog(sessionLog: SessionLogModel) {
    this.sessionLog = sessionLog;
    this.sessionLogSource.next(sessionLog);
  }

  setSessionLogList(sessionLogList: SessionLogModel[] = []) {
    this.sessionLogList = sessionLogList;
    this.sessionLogListSource.next(sessionLogList);
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
