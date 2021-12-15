import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {PlatformModel} from '@app/models/platform.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlatformService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public platform: PlatformModel;
  public platformList: PlatformModel[];

  private platformSource = new Subject<PlatformModel>();
  // Observable string streams
  platform$ = this.platformSource.asObservable();

  private platformListSource = new Subject<PlatformModel[]>();
  // Observable string streams
  platformList$ = this.platformListSource.asObservable();

  setPlatform(platform: PlatformModel) {
    this.platform = platform;
    this.platformSource.next(platform);
  }

  setPlatformList(platformList: PlatformModel[] = []) {
    this.platformList = platformList;
    this.platformListSource.next(platformList);
  }

  query = (params: object) => {
    return this.http.post("/platform/get", params);
  }

  create = (model: PlatformModel) => {
    return this.http.post("/platform/create", model);
  }

  update = (model: PlatformModel) => {
    return this.http.post("/platform/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/platform/delete", {id: id});
  }
}
