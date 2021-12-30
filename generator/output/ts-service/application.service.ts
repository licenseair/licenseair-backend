import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {ApplicationModel} from '@app/models/application.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
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
