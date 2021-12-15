import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {${camelName}Model} from '@app/models/${urlString}.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ${camelName}Service extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public ${camelNameVar}: ${camelName}Model;
  public ${camelNameVar}List: ${camelName}Model[];

  private ${camelNameVar}Source = new Subject<${camelName}Model>();
  // Observable string streams
  ${camelNameVar}$ = this.${camelNameVar}Source.asObservable();

  private ${camelNameVar}ListSource = new Subject<${camelName}Model[]>();
  // Observable string streams
  ${camelNameVar}List$ = this.${camelNameVar}ListSource.asObservable();

  set${camelName}(${camelNameVar}: ${camelName}Model) {
    this.${camelNameVar} = ${camelNameVar};
    this.${camelNameVar}Source.next(${camelNameVar});
  }

  set${camelName}List(${camelNameVar}List: ${camelName}Model[] = []) {
    this.${camelNameVar}List = ${camelNameVar}List;
    this.${camelNameVar}ListSource.next(${camelNameVar}List);
  }

  query = (params: object) => {
    return this.http.post("/${urlString}/get", params);
  }

  create = (model: ${camelName}Model) => {
    return this.http.post("/${urlString}/create", model);
  }

  update = (model: ${camelName}Model) => {
    return this.http.post("/${urlString}/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/${urlString}/delete", {id: id});
  }
}
