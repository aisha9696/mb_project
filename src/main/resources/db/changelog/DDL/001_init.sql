CREATE SEQUENCE file_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;
CREATE SEQUENCE business_type_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

create table file
(
    id        bigint not null
        constraint file_pk_id
            primary key,
    name      varchar(255),
    extension varchar(255),
    data      bytea
);

create table if not exists user_detail
(
    id       uuid        not null
        constraint user_detail_pk_id
            primary key,
    username varchar(50) not null,
    temporal bool not null,
    photo_id bigint
        constraint user_detail_fk_photo_id references file
);


create table business_type
(
    id uuid not null
        constraint business_type_pk_id
            primary key,
    value_ru   varchar(255),
    value_kz   varchar(255),
    created_by_id uuid
        constraint business_type_fk_created_by_id references user_detail,
    updated_by_id uuid
        constraint business_type_fk_updated_by_id references user_detail,
    created_at timestamp,
    updated_at timestamp,
    archived   boolean
);


create table business(
                         id uuid not null
                             constraint business_pk_id
                                 primary key,
                         name varchar(255),
                         address varchar(255),
                         payment_types text[],
                         business_type_id uuid constraint business_fk_business_type_id references business_type,
                         created_by_id uuid
                             constraint business_fk_created_by_id references user_detail,
                         updated_by_id uuid
                             constraint business_fk_updated_by_id references user_detail,
                         created_at timestamp,
                         updated_at timestamp,
                         archived boolean
);

create table user_business ( id uuid not null constraint user_business_pk_id primary key,
                             user_roles VARCHAR(255),
                             user_id uuid constraint user_business_fk_user_id references user_detail,
                             business_id uuid constraint user_business_fk_business_id REFERENCES business
);


create table otp ( id uuid not null constraint otp_pk_id primary key,
                   phone_number VARCHAR(255),
                   otp_hash VARCHAR(500),
                   attempts_available int,
                   deletion_date timestamp,
                   message_text VARCHAR(255),
                   verified boolean
);
