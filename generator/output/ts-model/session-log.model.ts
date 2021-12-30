class SessionLogModel {
  public id: number = 0;

  // deleted 
  public deleted: boolean = false;

  // sign 
  public sign?: string;

  // user_id 
  public user_id?: number = 0;

  // key 
  public key?: string;

  // type 1 user, 2 admin
  public type?: number = 0;

  public created_at: string;

  public updated_at: string;
}

const SessionLogModelFields = {
  id: "id",
  deleted: "deleted",
  sign: "sign",
  user_id: "user_id",
  key: "key",
  type: "type",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {SessionLogModel, SessionLogModelFields};
