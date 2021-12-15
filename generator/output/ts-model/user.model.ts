class UserModel {
  public id: number = 0;

  // deleted 是否删除
  public deleted?: number = 0;

  // email 邮箱地址
  public email?: string;

  // mobile 手机号
  public mobile: string;

  // password 密码
  public password: string;

  // domain 用户域名
  public domain?: string;

  // active 0未激活，1激活
  public active: number = 0;

  // username 用户名
  public username: string;

  public created_at: string;

  public updated_at: string;
}

const UserModelFields = {
  id: "id",
  deleted: "deleted",
  email: "email",
  mobile: "mobile",
  password: "password",
  domain: "domain",
  active: "active",
  username: "username",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {UserModel, UserModelFields};
