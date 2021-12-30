class SmsLogModel {
  public id: number = 0;

  // deleted 
  public deleted: boolean = false;

  // mobile 手机号
  public mobile: string;

  // code 验证码
  public code: string;

  public created_at: string;

  public updated_at: string;
}

const SmsLogModelFields = {
  id: "id",
  deleted: "deleted",
  mobile: "mobile",
  code: "code",
  created_at: "created_at",
  updated_at: "updated_at"
};

export {SmsLogModel, SmsLogModelFields};
