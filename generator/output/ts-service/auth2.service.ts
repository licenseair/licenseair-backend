import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {Auth2Model} from '@app/models/auth2.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Auth2Service extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public auth2: Auth2Model;
  public auth2List: Auth2Model[];

  private auth2Source = new Subject<Auth2Model>();
  // Observable string streams
  auth2$ = this.auth2Source.asObservable();

  private auth2ListSource = new Subject<Auth2Model[]>();
  // Observable string streams
  auth2List$ = this.auth2ListSource.asObservable();

  setAuth2(auth2: Auth2Model) {
    this.auth2 = auth2;
    this.auth2Source.next(auth2);
  }

  setAuth2List(auth2List: Auth2Model[] = []) {
    this.auth2List = auth2List;
    this.auth2ListSource.next(auth2List);
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
