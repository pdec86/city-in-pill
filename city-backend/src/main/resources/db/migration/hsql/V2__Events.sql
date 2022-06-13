create table event_source (
    id bigint identity primary key,
    aggregate_id uuid not null,
    event_data longvarchar not null,
    event_type varchar(128) not null,
    metadata longvarchar default null null,
    occurred_datetime timestamp not null,
    event_version int not null
);

create table v_event (
    id uuid primary key,
    name varchar(128) not null,
    start_date_time varchar(64) not null,
    end_date_time varchar(64) not null,
    owner_id bigint not null,
    total_price numeric(16,4) not null,
    foreign key (owner_id) references city_user (id)
);

create table v_event_person (
    id bigint primary key,
    event_id uuid not null,
    first_name varchar(64) not null,
    last_name varchar(64) default null null,
    contact_phone varchar(16) default null null,
    contact_email varchar(128) default null null,
    foreign key (event_id) references v_event(id)
);
