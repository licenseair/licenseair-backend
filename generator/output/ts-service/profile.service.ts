import {Injectable} from '@angular/core';
import {HttpService} from '@services/http/http.service';
import {ProfileModel} from '@app/models/profile.model';
import {BaseService} from '@services/data/base.service';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService extends BaseService {
  constructor(
    private http: HttpService
  ) {
    super();
  }

  public profile: ProfileModel;
  public profileList: ProfileModel[];

  private profileSource = new Subject<ProfileModel>();
  // Observable string streams
  profile$ = this.profileSource.asObservable();

  private profileListSource = new Subject<ProfileModel[]>();
  // Observable string streams
  profileList$ = this.profileListSource.asObservable();

  setProfile(profile: ProfileModel) {
    this.profile = profile;
    this.profileSource.next(profile);
  }

  setProfileList(profileList: ProfileModel[] = []) {
    this.profileList = profileList;
    this.profileListSource.next(profileList);
  }

  query = (params: object) => {
    return this.http.post("/profile/get", params);
  }

  create = (model: ProfileModel) => {
    return this.http.post("/profile/create", model);
  }

  update = (model: ProfileModel) => {
    return this.http.post("/profile/update", model);
  }

  delete = (id: number) => {
    return this.http.post("/profile/delete", {id: id});
  }
}
