import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {LanguageModel} from '@app/models/language.model';
import {BaseService} from '@services/data/base.service';

@Injectable({
  providedIn: 'root'
})
export class LanguageService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  query = (params: object) => {
    return this.http.post("/language/get", params);
  }

  create = (model: LanguageModel) => {
    return this.http.post("/language/create", model);
  }

  update = (model: LanguageModel) => {
    return this.http.post("/language/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/language/delete", {id: id});
  }
}
