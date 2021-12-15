import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {UserModel} from '@app/models/user.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public user: UserModel;
  public userList: UserModel[];

  private userSource = new Subject<UserModel>();
  // Observable string streams
  user$ = this.userSource.asObservable();

  private userListSource = new Subject<UserModel[]>();
  // Observable string streams
  userList$ = this.userListSource.asObservable();

  setUser(user: UserModel) {
    this.user = user;
    this.userSource.next(user);
  }

  setUserList(userList: UserModel[] = []) {
    this.userList = userList;
    this.userListSource.next(userList);
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
