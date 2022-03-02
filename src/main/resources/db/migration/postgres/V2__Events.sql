create table event_source (
    id bigserial primary key,
    aggregate_id uuid not null,
    event_data text not null,
    event_type varchar(128) not null,
    metadata text null default null,
    occurred_datetime timestamp not null,
    event_version int not null
);

create table v_event (
    id uuid primary key,
    name varchar(128) not null,
    start_date_time varchar(64) not null,
    end_date_time varchar(64) not null,
    user_id bigint not null,
    owner_first_name varchar(64) not null,
    owner_last_name varchar(64) null default null,
    owner_phone varchar(16) null default null,
    owner_email varchar(128) null default null,
    total_price numeric(16,4) not null,
    foreign key (user_id) references city_user (id)
);

create table v_event_person (
    id bigserial primary key,
    event_id uuid not null,
    first_name varchar(64) not null,
    last_name varchar(64) null default null,
    contact_phone varchar(16) null default null,
    contact_email varchar(128) null default null,
    foreign key (event_id) references v_event(id)
);