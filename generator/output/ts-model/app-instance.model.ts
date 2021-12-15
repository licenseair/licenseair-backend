class AppInstanceModel {
  public id: number = 0;

  // deleted 是否删除
  public deleted?: number = 0;

  // status 实例状态 Pending | Starting | Running ｜ Stopping
  public status: string;

  // image_id 镜像id
  public image_id: string;

  // application_id APPid
  public application_id: number = 0;

  // public_address 公有地址
  public public_address: string;

  // instance_type 实例规格
  public instance_type: string;

  // origin_image_id 原有镜像id
  public origin_image_id: string;

  // remove_time 释放时间
  public remove_time?: string;

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
  status: "status",
  image_id: "image_id",
  application_id: "application_id",
  public_address: "public_address",
  instance_type: "instance_type",
  origin_image_id: "origin_image_id",
  remove_time: "remove_time",
  private_address: "private_address",
  user_id: "user_id",
  instance_id: "instance_id",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {AppInstanceModel, AppInstanceModelFields};
