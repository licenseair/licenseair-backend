class InstanceImageModel {
  public id: number = 0;

  // deleted 
  public deleted: boolean = false;

  // busy 忙碌 false为可用
  public busy: boolean = false;

  // application_id 
  public application_id: number = 0;

  // image_id 镜像 ID
  public image_id: string;

  public created_at: string;

  public updated_at: string;
}

const InstanceImageModelFields = {
  id: "id",
  deleted: "deleted",
  busy: "busy",
  application_id: "application_id",
  image_id: "image_id",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {InstanceImageModel, InstanceImageModelFields};
