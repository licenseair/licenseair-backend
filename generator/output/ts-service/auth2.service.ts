import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {Auth2Model} from '@app/models/auth2.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class Auth2Service extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/auth2/get", params);
  }

  create = (model: Auth2Model) => {
    return this.http.post("/auth2/create", model);
  }

  update = (model: Auth2Model) => {
    return this.http.post("/auth2/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/auth2/delete", {id: id});
  }
}
