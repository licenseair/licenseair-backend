import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {ProfileModel} from '@app/models/profile.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/profile/get", params);
  }

  create = (model: ProfileModel) => {
    return this.http.post("/profile/create", model);
  }

  update = (model: ProfileModel) => {
    return this.http.post("/profile/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/profile/delete", {id: id});
  }
}
