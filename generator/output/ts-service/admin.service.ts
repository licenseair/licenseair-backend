import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {AdminModel} from '@app/models/admin.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/admin/get", params);
  }

  create = (model: AdminModel) => {
    return this.http.post("/admin/create", model);
  }

  update = (model: AdminModel) => {
    return this.http.post("/admin/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/admin/delete", {id: id});
  }
}
