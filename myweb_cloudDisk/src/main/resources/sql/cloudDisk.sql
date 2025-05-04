create table admin
(
    id       int unsigned auto_increment comment '管理员id'
        primary key,
    admin    varchar(50)  null comment '管理员账号',
    password varchar(255) null comment '管理员密码'
)
    comment '管理员表' charset = utf8;

create table file
(
    file_id        bigint auto_increment comment '文件Id'
        primary key,
    file_size      bigint       null comment '文件大小',
    file_url       varchar(255) null comment '文件url',
    timestamp_name varchar(500) null comment '时间戳',
    identifier     varchar(32)  null comment 'md5唯一标识',
    point_count    int          null comment '引用数量',
    file_status    tinyint(1)   null comment '文件状态(0-失效，1-生效)',
    storage_type   int          null comment '存储类型',
    create_time    timestamp    null comment '创建时间',
    create_user_id bigint       null comment '创建用户id'
)
    comment '文件表' charset = utf8;

create table file_classify
(
    classify_id bigint auto_increment comment '文件分类id'
        primary key,
    type_id     int        null comment '文件类型id',
    extend_name varchar(5) null comment '文件扩展名'
)
    comment '文件分类表' charset = utf8;

create table file_extend
(
    extend_id      int auto_increment comment '扩展名Id'
        primary key,
    extend_name    varchar(5)   null comment '扩展名',
    extend_desc    varchar(25)  null comment '扩展名描述',
    extend_img_url varchar(100) null comment '扩展名预览图'
)
    comment '文件扩展名表' charset = utf8;

create table file_type
(
    type_id   int         not null comment '类型id'
        primary key,
    type_name varchar(50) null comment '文件类型名',
    order_num int         null comment '次序'
)
    comment '文件类型表' charset = utf8;

create table login_log
(
    id             bigint auto_increment comment '登录日志id'
        primary key,
    user_id        bigint      null comment '登录用户id',
    login_time     timestamp   null comment '最后登录时间',
    logout_time    timestamp   null comment '最后登出时间',
    login_ip       varchar(50) null comment '登录ip',
    login_location varchar(50) null comment '登录地址',
    browser        varchar(50) null comment '浏览器类型',
    os             varchar(50) null comment '操作系统',
    status         tinyint(1)  null comment '登录状态（0-成功,1-失败）'
)
    comment '登录日志表' charset = utf8;

create table operation_log
(
    id             bigint auto_increment comment '操作日志id'
        primary key,
    user_id        bigint       null comment '操作用户id',
    message        varchar(50)  null comment '消息',
    operation      varchar(20)  null comment '日志类型',
    method         varchar(255) null comment '请求方法',
    params         text         null comment '请求参数',
    ip             varchar(255) null comment '请求ip',
    location       varchar(50)  null comment '请求地址',
    request_url    varchar(255) null comment '请求路由',
    request_time   timestamp    null comment '请求时间',
    request_way    varchar(20)  null comment '请求方式',
    request_status tinyint(1)   null comment '操作状态（0-成功，1-失败）'
)
    comment '操作日志表' charset = utf8;

create table recovery_file
(
    recovery_file_id bigint auto_increment comment '文件删除记录id'
        primary key,
    user_file_id     bigint      null comment '用户文件id',
    delete_time      timestamp   null comment '删除时间',
    delete_batch_num varchar(50) null comment '删除批次号'
)
    comment '文件删除记录表' charset = utf8;

create table storage
(
    storage_id     bigint auto_increment comment '存储id'
        primary key,
    user_id        bigint    null comment '用户id',
    total_size     bigint    null comment '总存储大小',
    modify_time    timestamp null comment '修改时间',
    modify_user_id bigint    null comment '修改用户id'
)
    comment '存储信息表' charset = utf8;

create table user
(
    user_id       bigint auto_increment comment '用户Id'
        primary key,
    username      varchar(50)             null comment '用户名',
    telephone     char(11)                null comment '手机号',
    password      varchar(50)             null comment '登录密码',
    salt          varchar(255)            null comment '盐',
    avatar        varchar(255) default '' null comment '用户头像地址',
    register_time timestamp               null comment '注册时间'
)
    comment '普通用户表' charset = utf8;

create table user_file
(
    user_file_id bigint auto_increment comment '用户文件Id'
        primary key,
    user_id      bigint               null comment '用户Id',
    file_id      bigint               null comment '文件Id',
    file_name    varchar(255)         null comment '文件名称',
    file_path    varchar(500)         null comment '文件路径',
    extend_name  varchar(100)         null comment '扩展名',
    is_dir       tinyint(1) default 0 null comment '是否是目录(0-不是,1-是)',
    upload_time  timestamp            null comment '上传时间',
    delete_flag  tinyint(1)           null comment '删除标识（0-未删除，1-已删除）',
    delete_time  timestamp            null comment '删除时间',
    delete_num   varchar(50)          null comment '删除批次号'
)
    comment '用户文件表' charset = utf8;

