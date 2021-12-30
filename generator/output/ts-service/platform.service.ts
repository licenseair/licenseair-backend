import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {PlatformModel} from '@app/models/platform.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class PlatformService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/platform/get", params);
  }

  create = (model: PlatformModel) => {
    return this.http.post("/platform/create", model);
  }

  update = (model: PlatformModel) => {
    return this.http.post("/platform/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/platform/delete", {id: id});
  }
}
