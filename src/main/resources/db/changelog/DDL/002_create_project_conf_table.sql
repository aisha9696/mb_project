CREATE SEQUENCE configuration_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

create table project_configuration
(
    id        bigint not null
        constraint project_configuration_pk_id
            primary key,
    configuration_name      varchar(100),
    configuration_value    varchar(255)
);