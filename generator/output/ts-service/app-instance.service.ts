import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {AppInstanceModel} from '@app/models/app-instance.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class AppInstanceService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
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
