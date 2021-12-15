import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {CategoryModel} from '@app/models/category.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public category: CategoryModel;
  public categoryList: CategoryModel[];

  private categorySource = new Subject<CategoryModel>();
  // Observable string streams
  category$ = this.categorySource.asObservable();

  private categoryListSource = new Subject<CategoryModel[]>();
  // Observable string streams
  categoryList$ = this.categoryListSource.asObservable();

  setCategory(category: CategoryModel) {
    this.category = category;
    this.categorySource.next(category);
  }

  setCategoryList(categoryList: CategoryModel[] = []) {
    this.categoryList = categoryList;
    this.categoryListSource.next(categoryList);
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
