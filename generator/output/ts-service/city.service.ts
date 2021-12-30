import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {CityModel} from '@app/models/city.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class CityService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/city/get", params);
  }

  create = (model: CityModel) => {
    return this.http.post("/city/create", model);
  }

  update = (model: CityModel) => {
    return this.http.post("/city/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/city/delete", {id: id});
  }
}
