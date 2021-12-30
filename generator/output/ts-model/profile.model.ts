class ProfileModel {
  public id: number = 0;

  // deleted 是否删除
  public deleted: boolean = false;

  // company 公司
  public company?: string;

  // nickname 昵称
  public nickname?: string;

  // user_id 用户id
  public user_id: number = 0;

  // avatar 头像
  public avatar?: string;

  // birthday 生日
  public birthday?: string;

  // intro 简介
  public intro?: string;

  // city 所在城市
  public city?: string;

  public created_at: string;

  public updated_at: string;
}

const ProfileModelFields = {
  id: "id",
  deleted: "deleted",
  company: "company",
  nickname: "nickname",
  user_id: "user_id",
  avatar: "avatar",
  birthday: "birthday",
  intro: "intro",
  city: "city",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {ProfileModel, ProfileModelFields};
