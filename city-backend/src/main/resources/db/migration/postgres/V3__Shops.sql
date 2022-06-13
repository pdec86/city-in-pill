create table shop (
    id bigserial primary key,
    name varchar(128) not null,
    owner_id bigint not null,
    regon varchar(32) null default null,
    krs varchar(32) null default null,
    foreign key (owner_id) references city_user (id)
);

create table shop_employee (
    id bigserial primary key,
    shop_id bigint not null,
    first_name varchar(64) not null,
    last_name varchar(64) null default null,
    contact_phone varchar(16) null default null,
    contact_email varchar(128) null default null,
    foreign key (shop_id) references shop(id)
);