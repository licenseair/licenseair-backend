import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {InstanceImageModel} from '@app/models/instance-image.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class InstanceImageService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/instance-image/get", params);
  }

  create = (model: InstanceImageModel) => {
    return this.http.post("/instance-image/create", model);
  }

  update = (model: InstanceImageModel) => {
    return this.http.post("/instance-image/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/instance-image/delete", {id: id});
  }
}
