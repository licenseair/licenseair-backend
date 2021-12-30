class WalletModel {
  public id: number = 0;

  // deleted 
  public deleted: boolean = false;

  // user_id 
  public user_id: number = 0;

  // money 余额
  public money: number = 0;

  public created_at: string;

  public updated_at: string;
}

const WalletModelFields = {
  id: "id",
  deleted: "deleted",
  user_id: "user_id",
  money: "money",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {WalletModel, WalletModelFields};
