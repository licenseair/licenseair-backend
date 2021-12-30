import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {UserModel} from '@app/models/user.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/user/get", params);
  }

  create = (model: UserModel) => {
    return this.http.post("/user/create", model);
  }

  update = (model: UserModel) => {
    return this.http.post("/user/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/user/delete", {id: id});
  }
}
