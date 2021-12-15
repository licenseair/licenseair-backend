import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {InstanceTypeModel} from '@app/models/instance-type.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstanceTypeService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public instanceType: InstanceTypeModel;
  public instanceTypeList: InstanceTypeModel[];

  private instanceTypeSource = new Subject<InstanceTypeModel>();
  // Observable string streams
  instanceType$ = this.instanceTypeSource.asObservable();

  private instanceTypeListSource = new Subject<InstanceTypeModel[]>();
  // Observable string streams
  instanceTypeList$ = this.instanceTypeListSource.asObservable();

  setInstanceType(instanceType: InstanceTypeModel) {
    this.instanceType = instanceType;
    this.instanceTypeSource.next(instanceType);
  }

  setInstanceTypeList(instanceTypeList: InstanceTypeModel[] = []) {
    this.instanceTypeList = instanceTypeList;
    this.instanceTypeListSource.next(instanceTypeList);
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
