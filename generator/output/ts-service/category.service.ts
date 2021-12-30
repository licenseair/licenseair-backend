import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {CategoryModel} from '@app/models/category.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/category/get", params);
  }

  create = (model: CategoryModel) => {
    return this.http.post("/category/create", model);
  }

  update = (model: CategoryModel) => {
    return this.http.post("/category/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/category/delete", {id: id});
  }
}
