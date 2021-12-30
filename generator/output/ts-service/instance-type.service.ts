import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {InstanceTypeModel} from '@app/models/instance-type.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class InstanceTypeService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/instance-type/get", params);
  }

  create = (model: InstanceTypeModel) => {
    return this.http.post("/instance-type/create", model);
  }

  update = (model: InstanceTypeModel) => {
    return this.http.post("/instance-type/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/instance-type/delete", {id: id});
  }
}
