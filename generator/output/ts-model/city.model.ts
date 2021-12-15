class CityModel {
  public id: number = 0;

  // province 
  public province: string;

  // deleted 
  public deleted?: number = 0;

  // city 
  public city: string;

  // join_name 
  public join_name: string;

  public created_at: string;

  public updated_at: string;
}

const CityModelFields = {
  id: "id",
  province: "province",
  deleted: "deleted",
  city: "city",
  join_name: "join_name",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {CityModel, CityModelFields};
