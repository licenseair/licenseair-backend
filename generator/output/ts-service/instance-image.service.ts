import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {InstanceImageModel} from '@app/models/instance-image.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstanceImageService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public instanceImage: InstanceImageModel;
  public instanceImageList: InstanceImageModel[];

  private instanceImageSource = new Subject<InstanceImageModel>();
  // Observable string streams
  instanceImage$ = this.instanceImageSource.asObservable();

  private instanceImageListSource = new Subject<InstanceImageModel[]>();
  // Observable string streams
  instanceImageList$ = this.instanceImageListSource.asObservable();

  setInstanceImage(instanceImage: InstanceImageModel) {
    this.instanceImage = instanceImage;
    this.instanceImageSource.next(instanceImage);
  }

  setInstanceImageList(instanceImageList: InstanceImageModel[] = []) {
    this.instanceImageList = instanceImageList;
    this.instanceImageListSource.next(instanceImageList);
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
