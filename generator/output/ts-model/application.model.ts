class ApplicationModel {
  public id: number = 0;

  // name 名称
  public name: string;

  // category 分类
  public category: string[] = [];

  // platform 支持系统平台
  public platform: string[] = [];

  // deleted 是否删除
  public deleted: boolean = false;

  // languages 支持语言
  public languages: string[] = [];

  // introduce 简介
  public introduce: string;

  // price 价格
  public price?: number = 0;

  // version 当前版本
  public version: string;

  // icon 图标
  public icon: string;

  // usable 可用状态
  public usable: boolean = false;

  // official_price 官方价格
  public official_price?: number = 0;

  // tags 标签
  public tags?: string[] = [];

  // path 路径
  public path: string;

  public created_at: string;

  public updated_at: string;
}

const ApplicationModelFields = {
  id: "id",
  name: "name",
  category: "category",
  platform: "platform",
  deleted: "deleted",
  languages: "languages",
  introduce: "introduce",
  price: "price",
  version: "version",
  icon: "icon",
  usable: "usable",
  official_price: "official_price",
  tags: "tags",
  path: "path",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {ApplicationModel, ApplicationModelFields};
