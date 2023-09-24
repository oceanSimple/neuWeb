/*  book表 */
create table book
(
    id           int primary key auto_increment comment '书籍id',
    title        varchar(20) comment '书名',
    author       varchar(20) comment '作者',
    price        decimal(10, 2) comment '价格',
    picture      varchar(100) comment '图片地址',
    type         integer comment '书籍分类',
    address      varchar(20) comment '地址',
    account      integer comment '库存',
    print_time   char(4) comment '出版年份',
    publisher    varchar(20) comment '出版社',
    version      varchar(20) comment '版本',
    seller       char(8) comment '卖家',
    gmt_create   datetime default NOW() comment '创建时间',
    gmt_modified datetime default NOW() comment '修改时间'
) comment '书籍表';

insert into book
(title, author, price, picture, type, address, account, print_time, publisher, version, seller)
VALUES ('Java编程思想', 'Bruce Eckel', 108,
        'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', 2, '浑南', 10,
        '2023', '机械工业出版社', '第4版', '00000000'),
       ('Java核心技术', 'Cay S. Horstmann', 108,
        'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png', 2, '浑南',
        10, '2023', '机械工业出版社', '第4版', '00000000');

/* book type 表 */
create table book_type
(
    id   integer primary key auto_increment comment '主键',
    type varchar(20) not null comment '类型'
) comment '书籍类型表';

insert into book_type (type)
values ('其他'),
       ('计算机'),
       ('数学');
