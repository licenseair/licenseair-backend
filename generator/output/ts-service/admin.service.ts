import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {AdminModel} from '@app/models/admin.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public admin: AdminModel;
  public adminList: AdminModel[];

  private adminSource = new Subject<AdminModel>();
  // Observable string streams
  admin$ = this.adminSource.asObservable();

  private adminListSource = new Subject<AdminModel[]>();
  // Observable string streams
  adminList$ = this.adminListSource.asObservable();

  setAdmin(admin: AdminModel) {
    this.admin = admin;
    this.adminSource.next(admin);
  }

  setAdminList(adminList: AdminModel[] = []) {
    this.adminList = adminList;
    this.adminListSource.next(adminList);
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
