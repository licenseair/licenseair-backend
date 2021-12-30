CREATE TABLE "admin" (
  id bigserial PRIMARY KEY,
  username varchar(32) NOT NULL,
  password char(60) NOT NULL,
  mobile char(11) NOT NULL,
  email varchar(70) NOT NULL,
  active boolean NOT NULL DEFAULT true,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."admin" IS '管理员';
CREATE UNIQUE INDEX "admin_mobile" ON "public"."admin" USING btree (mobile);
COMMENT ON COLUMN "public"."admin"."username" IS '网站显示';
COMMENT ON COLUMN "public"."admin"."active" IS  '是否激活';
COMMENT ON COLUMN "public"."admin"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."admin"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."admin"."updated_at" IS '更新时间';

CREATE TABLE "category" (
  id bigserial PRIMARY KEY,
  name text NOT NULL,
  path text NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX "category_path" ON "public"."category" USING btree (path);
COMMENT ON TABLE "public"."category" IS 'APP分类';
COMMENT ON COLUMN "public"."category"."name" IS '分类名称';
COMMENT ON COLUMN "public"."category"."path" IS '英文名称';
COMMENT ON COLUMN "public"."category"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."category"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."category"."updated_at" IS '更新时间';

CREATE TABLE "profile" (
  id bigserial PRIMARY KEY,
  user_id int8 NOT NULL,
  avatar varchar(42) DEFAULT NULL,
  nickname char(20) DEFAULT NULL,
  intro varchar(255) DEFAULT NULL,
  company char(64) DEFAULT NULL,
  city varchar(64) DEFAULT 0,
  birthday date DEFAULT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX "profile_user_id" ON "public"."profile" USING btree (user_id);
COMMENT ON TABLE "public"."profile" IS '用户详细信息';
COMMENT ON COLUMN "public"."profile"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."profile"."avatar" IS '头像';
COMMENT ON COLUMN "public"."profile"."nickname" IS '昵称';
COMMENT ON COLUMN "public"."profile"."intro" IS '简介';
COMMENT ON COLUMN "public"."profile"."company" IS '公司';
COMMENT ON COLUMN "public"."profile"."birthday" IS '生日';
COMMENT ON COLUMN "public"."profile"."city" IS '所在城市';
COMMENT ON COLUMN "public"."profile"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."profile"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."profile"."updated_at" IS '更新时间';


CREATE TABLE session_log (
  id bigserial PRIMARY KEY,
  user_id int8,
  sign text,
  key char(44),
  type int2 DEFAULT 1,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX "session_log_user_id" ON "public"."session_log" USING btree (user_id);
CREATE INDEX "session_log_sign" ON "public"."session_log" USING btree (sign);
COMMENT ON TABLE "public"."session_log" IS '用户登录信息';
COMMENT ON COLUMN "public"."session_log"."type" IS '1 user, 2 admin';
COMMENT ON COLUMN "public"."session_log"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."session_log"."updated_at" IS '更新时间';

CREATE TABLE pay_order (
  id bigserial PRIMARY KEY,
  trade_no char(128) NOT NULL DEFAULT '',
  user_id int8 NOT NULL DEFAULT 0,
  subject_id int4 NOT NULL DEFAULT 0,
  type char(32) NOT NULL,
  snapshot text NULL,
  price decimal(10,2) NOT NULL DEFAULT 0.00,
  is_pay boolean NOT NULL DEFAULT false,
  pay_time timestamp NULL DEFAULT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX "pay_order_trade_no" ON "public"."pay_order" USING btree (trade_no);
COMMENT ON TABLE "public"."pay_order" IS '支付订单';
COMMENT ON COLUMN "public"."pay_order"."id"  IS '主键';
COMMENT ON COLUMN "public"."pay_order"."trade_no"  IS '订单号';
COMMENT ON COLUMN "public"."pay_order"."user_id"  IS '用户id';
COMMENT ON COLUMN "public"."pay_order"."subject_id"  IS 'subject id';
COMMENT ON COLUMN "public"."pay_order"."type"  IS '购买类型';
COMMENT ON COLUMN "public"."pay_order"."snapshot"  IS '快照';
COMMENT ON COLUMN "public"."pay_order"."price"  IS '价格（单位元）';
COMMENT ON COLUMN "public"."pay_order"."is_pay"  IS '是否已经支付';
COMMENT ON COLUMN "public"."pay_order"."pay_time"  IS '支付时间';
COMMENT ON COLUMN "public"."pay_order"."deleted"  IS '是否删除';
COMMENT ON COLUMN "public"."pay_order"."created_at"  IS '创建时间';
COMMENT ON COLUMN "public"."pay_order"."updated_at"  IS '更新时间';

CREATE TABLE order_notify (
  id  bigserial PRIMARY KEY,
  charset char(16) DEFAULT NULL,
  out_trade_no char(64) DEFAULT NULL,
  method char(32) DEFAULT NULL,
  total_amount varchar(255) DEFAULT NULL,
  sign text,
  trade_no char(32) DEFAULT NULL,
  auth_app_id char(16) DEFAULT NULL,
  version char(4) DEFAULT NULL,
  app_id char(16) DEFAULT NULL,
  sign_type char(4) DEFAULT NULL,
  seller_id char(16) DEFAULT NULL,
  timestamp timestamp NULL DEFAULT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."order_notify" IS '订单支付宝回调记录';
COMMENT ON COLUMN "public"."order_notify"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."order_notify"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."order_notify"."updated_at" IS '更新时间';

CREATE TABLE "user" (
  id bigserial PRIMARY KEY,
  username varchar(32) NOT NULL,
  password char(60) NOT NULL,
  mobile char(11) NOT NULL,
  email varchar(70) DEFAULT NULL,
  domain varchar(32) NULL,
  active boolean NOT NULL DEFAULT true,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX "user_mobile" ON "public"."user" USING btree (mobile);
CREATE UNIQUE INDEX "user_username" ON "public"."user" USING btree (username);
CREATE UNIQUE INDEX "user_domain" ON "public"."user" USING btree (domain);
COMMENT ON TABLE "public"."user" IS '用户';
COMMENT ON COLUMN "public"."user"."username" IS '用户名';
COMMENT ON COLUMN "public"."user"."password" IS '密码';
COMMENT ON COLUMN "public"."user"."mobile" IS '手机号';
COMMENT ON COLUMN "public"."user"."email" IS '邮箱地址';
COMMENT ON COLUMN "public"."user"."domain" IS '用户域名';
COMMENT ON COLUMN "public"."user"."active" IS '是否激活';
COMMENT ON COLUMN "public"."user"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."user"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."user"."updated_at" IS '更新时间';


CREATE TABLE "sms_log" (
  id bigserial PRIMARY KEY,
  mobile char(11) NOT NULL,
  code int4 NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX "sms_log_mobile" ON "public"."sms_log" USING btree (mobile);
CREATE INDEX "sms_log_code" ON "public"."sms_log" USING btree (code);
COMMENT ON TABLE "public"."sms_log" IS '短信记录';
COMMENT ON COLUMN "public"."sms_log"."mobile" IS '手机号';
COMMENT ON COLUMN "public"."sms_log"."code" IS '验证码';
COMMENT ON COLUMN "public"."sms_log"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."sms_log"."updated_at" IS '更新时间';


CREATE TABLE "application" (
  id bigserial PRIMARY KEY,
  name text NOT NULL,
  path text NOT NULL,
  icon char(42) NOT NULL,
  category bigint[] NOT NULL,
  tags text[] NULL,
  languages bigint[] NOT NULL,
  version text NOT NULL,
  introduce text NOT NULL,
  price numeric(10,2) NULL default 0.00,
  official_price numeric(10,2) NULL default 0.00,
  usable boolean NOT NULL DEFAULT false,
  platform bigint[] NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX "application_name" ON "public"."application" USING btree (name);
CREATE UNIQUE INDEX "application_path" ON "public"."application" USING btree (path);
CREATE INDEX "application_tags" ON "public"."application" USING btree (tags);
CREATE INDEX "application_category" ON "public"."application" USING btree (category);
COMMENT ON TABLE "public"."application" IS '应用';
COMMENT ON COLUMN "public"."application"."name" IS '名称';
COMMENT ON COLUMN "public"."application"."path" IS '路径';
COMMENT ON COLUMN "public"."application"."icon" IS '图标';
COMMENT ON COLUMN "public"."application"."category" IS '分类';
COMMENT ON COLUMN "public"."application"."tags" IS '标签';
COMMENT ON COLUMN "public"."application"."introduce" IS '简介';
COMMENT ON COLUMN "public"."application"."price" IS '价格';
COMMENT ON COLUMN "public"."application"."official_price" IS '官方价格';
COMMENT ON COLUMN "public"."application"."usable" IS '可用状态';
COMMENT ON COLUMN "public"."application"."version" IS '当前版本';
COMMENT ON COLUMN "public"."application"."platform" IS '支持系统平台';
COMMENT ON COLUMN "public"."application"."languages" IS '支持语言';
COMMENT ON COLUMN "public"."application"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."application"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."application"."updated_at" IS '更新时间';

CREATE TABLE "city" (
  id bigserial PRIMARY KEY,
  province char(64) NOT NULL,
  city char(64) NOT NULL,
  join_name  char(64) NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."city" IS '城市列表';

CREATE TABLE "wallet" (
  id bigserial PRIMARY KEY,
  user_id bigint default 0 NOT NULL,
  money decimal(10,2) NOT NULL DEFAULT 0.00,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."wallet" IS '钱包';
COMMENT ON COLUMN "public"."wallet"."money" IS '余额';
CREATE UNIQUE INDEX "wallet_user_id" ON "public"."wallet" USING btree (user_id);

CREATE TABLE "wallet_log" (
  id bigserial PRIMARY KEY,
  user_id bigint default 0 NOT NULL,
  money decimal(10,2) NOT NULL DEFAULT 0.00,
  description text NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."wallet_log" IS '资金记录';
CREATE INDEX "wallet_log_user_id" ON "public"."wallet_log" USING btree (user_id);
COMMENT ON COLUMN "public"."wallet_log"."money" IS '资金变动';
COMMENT ON COLUMN "public"."wallet_log"."description" IS '资金变动';

CREATE TABLE "instance_image" (
  id bigserial PRIMARY KEY,
  image_id text NOT NULL,
  application_id bigint default 0 NOT NULL,
  busy boolean NOT NULL default false,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."instance_image" IS 'APP镜像';
CREATE UNIQUE INDEX "instance_image_image_id" ON "public"."instance_image" USING btree (image_id);
COMMENT ON COLUMN "public"."instance_image"."image_id" IS '镜像 ID';
COMMENT ON COLUMN "public"."instance_image"."busy" IS '忙碌 false为可用';

CREATE TABLE "auth2" (
  id bigserial PRIMARY KEY,
  user_id bigint default 0 NOT NULL,
  source text NOT NULL,
  uuid text NOT NULL,
  unionid text,
  raw_data text NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."auth2" IS 'Auth 2.0';
CREATE INDEX "auth2_source" ON "public"."auth2" USING btree (source);
COMMENT ON COLUMN "public"."auth2"."source" IS '认证来源';
COMMENT ON COLUMN "public"."auth2"."uuid" IS 'uuid';
COMMENT ON COLUMN "public"."auth2"."unionid" IS 'unionid';


CREATE TABLE "language"
(
  id bigserial PRIMARY KEY,
  "description" text NOT NULL ,
  "tag" text NOT NULL ,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."language" IS '语言';


CREATE TABLE "platform" (
  id bigserial PRIMARY KEY,
  "name" text NOT NULL ,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."platform" IS '平台';


CREATE TABLE "app_instance" (
  id bigserial PRIMARY KEY,
  user_id bigint default 0 NOT NULL,
  application_id bigint default 0 NOT NULL,
  private_address char(15) NOT NULL,
  public_address char(15) NOT NULL,
  status char(16) NOT NULL DEFAULT 'Pending',
  image_id text NOT NULL,
  origin_image_id text NOT NULL,
  instance_id text NOT NULL,
  password text NOT NULL,
  instance_type text NOT NULL,
  hours int2 NOT NULL default 1,
  auto_save boolean NOT NULL DEFAULT false,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."app_instance" IS 'app实例';
COMMENT ON COLUMN "public"."app_instance"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."app_instance"."application_id" IS 'APPid';
COMMENT ON COLUMN "public"."app_instance"."private_address" IS '私有地址';
COMMENT ON COLUMN "public"."app_instance"."public_address" IS '公有地址';
COMMENT ON COLUMN "public"."app_instance"."status" IS '实例状态 Pending | Starting | Running ｜ Stopping';
COMMENT ON COLUMN "public"."app_instance"."image_id" IS '镜像id';
COMMENT ON COLUMN "public"."app_instance"."origin_image_id" IS '原有镜像id';
COMMENT ON COLUMN "public"."app_instance"."instance_id" IS '实例id';
COMMENT ON COLUMN "public"."app_instance"."password" IS '实例id';
COMMENT ON COLUMN "public"."app_instance"."instance_type" IS '实例规格';
COMMENT ON COLUMN "public"."app_instance"."hours" IS '使用时长';
COMMENT ON COLUMN "public"."app_instance"."auto_save" IS '是否删除';
COMMENT ON COLUMN "public"."app_instance"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."app_instance"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."app_instance"."updated_at" IS '更新时间';


CREATE TABLE "instance_type" (
  id bigserial PRIMARY KEY,
  name text NOT NULL,
  type text NOT NULL,
  price decimal(10,2) NOT NULL DEFAULT 0.00,
  deleted boolean NOT NULL DEFAULT false,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "public"."instance_type" IS '实例规格';
CREATE UNIQUE INDEX "instance_type_type" ON "public"."instance_type" USING btree (type);
COMMENT ON COLUMN "public"."instance_type"."name"  IS '名称';
COMMENT ON COLUMN "public"."instance_type"."type"  IS '规格';
COMMENT ON COLUMN "public"."instance_type"."price"  IS '价格（单位元）/小时';
COMMENT ON COLUMN "public"."instance_type"."deleted" IS '是否删除';
COMMENT ON COLUMN "public"."instance_type"."created_at" IS '创建时间';
COMMENT ON COLUMN "public"."instance_type"."updated_at" IS '更新时间';

-- pg_dump -U programschool --dbname=programschool --column-inserts --file="./ps-dump.sql"
