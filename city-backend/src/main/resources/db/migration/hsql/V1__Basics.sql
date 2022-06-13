create table city_user (
    id bigint identity primary key,
    username varchar(64) not null unique,
    password varchar(255) not null,
    credentials_expired boolean default false not null,
    account_expired boolean default false not null,
    account_locked boolean default false not null,
    is_enabled boolean default false not null,
    created_on timestamp default now() not null,
    updated_on timestamp default null null
);

create table user_contact (
    user_id bigint identity not null primary key,
    first_name varchar(64) not null,
    last_name varchar(64) default null null,
    phone_type varchar(32) default null null,
    phone_value varchar(16) default null null,
    email varchar(128) default null null,
    foreign key (user_id) references city_user (id)
);

create trigger user_updated
    after update on city_user
    REFERENCING NEW ROW AS newrow
    for each row
    update city_user set updated_on = now() where id = newrow.id;


create table user_authority (
    id bigint identity primary key,
    authority varchar(32) not null,
    user_id bigint not null,
    created_on timestamp default now() not null,
    foreign key (user_id) references city_user (id)
);
