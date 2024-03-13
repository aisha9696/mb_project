create table catalog_unit (

                                  id uuid not null
                                      constraint catalog_unit_pk_id
                                          primary key,
                                  value_ru   varchar(20),
                                  value_kz   varchar(20),
                                  type varchar(255),
                                  archived   boolean)
