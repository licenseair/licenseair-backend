class PayOrderModel {
  public id: number = 0;

  // snapshot 快照
  public snapshot?: string;

  // deleted 是否删除
  public deleted?: number = 0;

  // trade_no 订单号
  public trade_no: string;

  // price 价格（单位元）
  public price: number = 0;

  // subject_id subject id
  public subject_id: string;

  // pay_time 支付时间
  public pay_time?: string;

  // type 购买类型
  public type: string;

  // is_pay 是否已经支付
  public is_pay: boolean = false;

  // user_id 用户id
  public user_id: number = 0;

  public created_at: string;

  public updated_at: string;
}

const PayOrderModelFields = {
  id: "id",
  snapshot: "snapshot",
  deleted: "deleted",
  trade_no: "trade_no",
  price: "price",
  subject_id: "subject_id",
  pay_time: "pay_time",
  type: "type",
  is_pay: "is_pay",
  user_id: "user_id",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {PayOrderModel, PayOrderModelFields};
