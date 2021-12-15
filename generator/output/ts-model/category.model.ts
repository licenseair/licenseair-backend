class CategoryModel {
  public id: number = 0;

  // deleted 是否删除
  public deleted?: number = 0;

  // name 分类名称
  public name: string;

  // path 英文名称
  public path: string;

  public created_at: string;

  public updated_at: string;
}

const CategoryModelFields = {
  id: "id",
  deleted: "deleted",
  name: "name",
  path: "path",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {CategoryModel, CategoryModelFields};
