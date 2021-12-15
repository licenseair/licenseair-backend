import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {ApplicationModel} from '@app/models/application.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public application: ApplicationModel;
  public applicationList: ApplicationModel[];

  private applicationSource = new Subject<ApplicationModel>();
  // Observable string streams
  application$ = this.applicationSource.asObservable();

  private applicationListSource = new Subject<ApplicationModel[]>();
  // Observable string streams
  applicationList$ = this.applicationListSource.asObservable();

  setApplication(application: ApplicationModel) {
    this.application = application;
    this.applicationSource.next(application);
  }

  setApplicationList(applicationList: ApplicationModel[] = []) {
    this.applicationList = applicationList;
    this.applicationListSource.next(applicationList);
  }

  query = (params: object) => {
    return this.http.post("/application/get", params);
  }

  create = (model: ApplicationModel) => {
    return this.http.post("/application/create", model);
  }

  update = (model: ApplicationModel) => {
    return this.http.post("/application/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/application/delete", {id: id});
  }
}
