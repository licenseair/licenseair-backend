class PlatformModel {
  public id: number = 0;

  // deleted 
  public deleted?: number = 0;

  // name 
  public name: string;

  public created_at: string;

  public updated_at: string;
}

const PlatformModelFields = {
  id: "id",
  deleted: "deleted",
  name: "name",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {PlatformModel, PlatformModelFields};
