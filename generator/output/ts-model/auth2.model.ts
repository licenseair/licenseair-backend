class Auth2Model {
  public id: number = 0;

  // deleted 
  public deleted?: number = 0;

  // unionid unionid
  public unionid?: string;

  // user_id 
  public user_id: number = 0;

  // uuid uuid
  public uuid: string;

  // source 认证来源
  public source: string;

  // raw_data 
  public raw_data: string;

  public created_at: string;

  public updated_at: string;
}

const Auth2ModelFields = {
  id: "id",
  deleted: "deleted",
  unionid: "unionid",
  user_id: "user_id",
  uuid: "uuid",
  source: "source",
  raw_data: "raw_data",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {Auth2Model, Auth2ModelFields};
