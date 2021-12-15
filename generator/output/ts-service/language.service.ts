import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {LanguageModel} from '@app/models/language.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LanguageService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public language: LanguageModel;
  public languageList: LanguageModel[];

  private languageSource = new Subject<LanguageModel>();
  // Observable string streams
  language$ = this.languageSource.asObservable();

  private languageListSource = new Subject<LanguageModel[]>();
  // Observable string streams
  languageList$ = this.languageListSource.asObservable();

  setLanguage(language: LanguageModel) {
    this.language = language;
    this.languageSource.next(language);
  }

  setLanguageList(languageList: LanguageModel[] = []) {
    this.languageList = languageList;
    this.languageListSource.next(languageList);
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
