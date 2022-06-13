create table shop (
    id bigint identity primary key,
    name varchar(128) not null,
    owner_id bigint not null,
    regon varchar(32) default null null,
    krs varchar(32) default null null,
    foreign key (owner_id) references city_user (id)
);

create table shop_employee (
    id bigint identity primary key,
    shop_id bigint not null,
    first_name varchar(64) not null,
    last_name varchar(64) default null null,
    contact_phone varchar(16) default null null,
    contact_email varchar(128) default null null,
    foreign key (shop_id) references shop(id)
);
