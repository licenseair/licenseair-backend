import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {${camelName}Model} from '@app/models/${urlString}.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class ${camelName}Service extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
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
