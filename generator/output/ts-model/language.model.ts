class LanguageModel {
  public id: number = 0;

  // tag 
  public tag: string;

  // deleted 
  public deleted: boolean = false;

  // description 
  public description: string;

  public created_at: string;

  public updated_at: string;
}

const LanguageModelFields = {
  id: "id",
  tag: "tag",
  deleted: "deleted",
  description: "description",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {LanguageModel, LanguageModelFields};
