drop database tp_3_db;
create schema if not exists tp_3_db;

create table if not exists client
(
    age     int default 0 null,
    email   varchar(50)   null,
    address varchar(50)   null,
    name    varchar(50)   null,
    id      int auto_increment
    primary key
    );

create table if not exists product
(
    name  varchar(50)     null,
    price float default 0 null,
    stock int   default 0 null,
    id    int auto_increment
    primary key
    );

create table if not exists `order`
(
    product int           not null,
    client  int           not null,
    amount  int default 0 null,
    id      int auto_increment
    primary key,
    constraint order_client_id_fk
    foreign key (client) references client (id),
    constraint order_product_id_fk
    foreign key (product) references product (id)
    );

