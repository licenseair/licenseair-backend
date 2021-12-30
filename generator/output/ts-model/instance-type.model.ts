class InstanceTypeModel {
  public id: number = 0;

  // deleted 是否删除
  public deleted: boolean = false;

  // name 名称
  public name: string;

  // type 规格
  public type: string;

  // price 价格（单位元）/小时
  public price: number = 0;

  public created_at: string;

  public updated_at: string;
}

const InstanceTypeModelFields = {
  id: "id",
  deleted: "deleted",
  name: "name",
  type: "type",
  price: "price",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {InstanceTypeModel, InstanceTypeModelFields};
