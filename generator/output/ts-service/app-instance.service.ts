import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {AppInstanceModel} from '@app/models/app-instance.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppInstanceService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public appInstance: AppInstanceModel;
  public appInstanceList: AppInstanceModel[];

  private appInstanceSource = new Subject<AppInstanceModel>();
  // Observable string streams
  appInstance$ = this.appInstanceSource.asObservable();

  private appInstanceListSource = new Subject<AppInstanceModel[]>();
  // Observable string streams
  appInstanceList$ = this.appInstanceListSource.asObservable();

  setAppInstance(appInstance: AppInstanceModel) {
    this.appInstance = appInstance;
    this.appInstanceSource.next(appInstance);
  }

  setAppInstanceList(appInstanceList: AppInstanceModel[] = []) {
    this.appInstanceList = appInstanceList;
    this.appInstanceListSource.next(appInstanceList);
  }

  query = (params: object) => {
    return this.http.post("/app-instance/get", params);
  }

  create = (model: AppInstanceModel) => {
    return this.http.post("/app-instance/create", model);
  }

  update = (model: AppInstanceModel) => {
    return this.http.post("/app-instance/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/app-instance/delete", {id: id});
  }
}
