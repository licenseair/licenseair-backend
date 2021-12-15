class WalletLogModel {
  public id: number = 0;

  // deleted 
  public deleted?: number = 0;

  // user_id 
  public user_id: number = 0;

  // money 资金变动
  public money: number = 0;

  // description 资金变动
  public description: string;

  public created_at: string;

  public updated_at: string;
}

const WalletLogModelFields = {
  id: "id",
  deleted: "deleted",
  user_id: "user_id",
  money: "money",
  description: "description",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {WalletLogModel, WalletLogModelFields};
