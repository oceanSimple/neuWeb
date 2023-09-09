create table user
(
    id           bigint unsigned auto_increment primary key comment '主键',
    code         char(8) unique not null comment '学号',
    password     varchar(32)    not null comment '密码',
    nickname     varchar(20)    not null comment '昵称',
    campus       varchar(20)    not null comment '校区',
    dormitory    varchar(20)    not null comment '宿舍',
    is_deleted   tinyint(1)     not null default 0 comment '是否删除:0-否,1-是',
    email        varchar(50) unique      default '' comment '邮箱',
    phone        char(11)                default '' comment '手机号',
    gmt_create   datetime       not null comment '创建时间',
    gmt_modified datetime       not null comment '修改时间',
    unique index uk_code (code) comment '唯一索引:学号',
    unique index uk_email (email) comment '唯一索引:邮箱'
) default charset = utf8 comment '用户表';

# 为用户表插入数据
insert into user (code, password, nickname, campus, dormitory, email, phone, gmt_create, gmt_modified)
values ('20216901', 'e10adc3949ba59abbe56e057f20f883e', 'ocean', '南校区', '1号楼', '1', '', now(), now()),
       ('20216902', 'e10adc3949ba59abbe56e057f20f883e', 'ocean', '南校区', '1号楼', '2', '', now(), now()),
       ('20216903', 'e10adc3949ba59abbe56e057f20f883e', 'ocean', '南校区', '1号楼', '3', '', now(), now()),
       ('20216904', 'e10adc3949ba59abbe56e057f20f883e', 'ocean', '南校区', '1号楼', '4', '', now(), now()),
       ('20216905', 'e10adc3949ba59abbe56e057f20f883e', 'ocean', '南校区', '1号楼', '5', '', now(), now()),
       ('20216906', 'e10adc3949ba59abbe56e057f20f883e', 'ocean', '南校区', '1号楼', '6', '', now(), now());