import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {CityModel} from '@app/models/city.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CityService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public city: CityModel;
  public cityList: CityModel[];

  private citySource = new Subject<CityModel>();
  // Observable string streams
  city$ = this.citySource.asObservable();

  private cityListSource = new Subject<CityModel[]>();
  // Observable string streams
  cityList$ = this.cityListSource.asObservable();

  setCity(city: CityModel) {
    this.city = city;
    this.citySource.next(city);
  }

  setCityList(cityList: CityModel[] = []) {
    this.cityList = cityList;
    this.cityListSource.next(cityList);
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
