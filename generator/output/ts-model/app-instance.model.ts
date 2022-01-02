class AppInstanceModel {
  public id: number = 0;

  // deleted 是否删除
  public deleted: boolean = false;

  // auto_save 是否删除
  public auto_save: boolean = false;

  // status 实例状态 Pending | Starting | Running ｜ Stopping
  public status: string;

  // image_id 镜像id
  public image_id: string;

  // application_id APPid
  public application_id: number = 0;

  // public_address 公有地址
  public public_address: string;

  // origin_image_id 原有镜像id
  public origin_image_id: string;

  // instance_type 实例规格
  public instance_type: string;

  // hours 使用时长
  public hours: number = 0;

  // password 实例id
  public password: string;

  // private_address 私有地址
  public private_address: string;

  // user_id 用户id
  public user_id: number = 0;

  // instance_id 实例id
  public instance_id: string;

  public created_at: string;

  public updated_at: string;
}

const AppInstanceModelFields = {
  id: "id",
  deleted: "deleted",
  auto_save: "auto_save",
  status: "status",
  image_id: "image_id",
  application_id: "application_id",
  public_address: "public_address",
  origin_image_id: "origin_image_id",
  instance_type: "instance_type",
  hours: "hours",
  password: "password",
  private_address: "private_address",
  user_id: "user_id",
  instance_id: "instance_id",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {AppInstanceModel, AppInstanceModelFields};
