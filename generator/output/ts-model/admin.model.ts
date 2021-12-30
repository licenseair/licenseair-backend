class AdminModel {
  public id: number = 0;

  // deleted 是否删除
  public deleted: boolean = false;

  // email 
  public email: string;

  // mobile 
  public mobile: string;

  // password 
  public password: string;

  // active 是否激活
  public active: boolean = false;

  // username 网站显示
  public username: string;

  public created_at: string;

  public updated_at: string;
}

const AdminModelFields = {
  id: "id",
  deleted: "deleted",
  email: "email",
  mobile: "mobile",
  password: "password",
  active: "active",
  username: "username",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {AdminModel, AdminModelFields};
