class OrderNotifyModel {
  public id: number = 0;

  // method 
  public method?: string;

  // deleted 是否删除
  public deleted: boolean = false;

  // sign 
  public sign?: string;

  // trade_no 
  public trade_no?: string;

  // charset 
  public charset?: string;

  // auth_app_id 
  public auth_app_id?: string;

  // out_trade_no 
  public out_trade_no?: string;

  // timestamp 
  public timestamp?: string;

  // app_id 
  public app_id?: string;

  // version 
  public version?: string;

  // seller_id 
  public seller_id?: string;

  // total_amount 
  public total_amount?: string;

  // sign_type 
  public sign_type?: string;

  public created_at: string;

  public updated_at: string;
}

const OrderNotifyModelFields = {
  id: "id",
  method: "method",
  deleted: "deleted",
  sign: "sign",
  trade_no: "trade_no",
  charset: "charset",
  auth_app_id: "auth_app_id",
  out_trade_no: "out_trade_no",
  timestamp: "timestamp",
  app_id: "app_id",
  version: "version",
  seller_id: "seller_id",
  total_amount: "total_amount",
  sign_type: "sign_type",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {OrderNotifyModel, OrderNotifyModelFields};
