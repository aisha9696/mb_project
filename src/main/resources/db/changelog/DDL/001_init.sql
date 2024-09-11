create schema if not exists project;
create schema if not exists project_settings;

create table if not exists project.user_detail
(
    id        uuid        not null
        constraint user_detail_pk_id
            primary key,
    username  varchar(50) not null unique,
    email     varchar(150),
    firstname varchar(150),
    lastname  varchar(150),
    temporal  bool        not null
);

cREATE TABLE IF NOT EXISTS project_settings.business_type
(
    id       uuid not null
        constraint business_type_pk_id
            primary key,
    value_ru varchar(255),
    value_kz varchar(255),
    archived boolean
);

CREATE TABLE IF NOT EXISTS project.business
(
    id               uuid not null
        constraint business_pk_id
            primary key,
    name             varchar(255),
    address          varchar(255),
    payment_types    text[],
    business_type_id uuid
        constraint business_fk_business_type_id references project_settings.business_type,
    created_by_user  varchar(255),
    updated_by_user  varchar(255),
    created_at       timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at       timestamp DEFAULT CURRENT_TIMESTAMP,
    archived         boolean
);

CREATE TABLE IF NOT EXISTS project.user_business
(
    id          uuid not null
        constraint user_business_pk_id primary key,
    user_roles  VARCHAR(255),
    user_id     uuid
        constraint user_business_fk_user_id references project.user_detail,
    business_id uuid
        constraint user_business_fk_business_id references project.business
);

CREATE TABLE IF NOT EXISTS project_settings.otp
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    phone_number       VARCHAR(255),
    otp_hash           VARCHAR(500),
    attempts_available int,
    deletion_date      timestamp,
    message_text       VARCHAR(255),
    verified           boolean
);
