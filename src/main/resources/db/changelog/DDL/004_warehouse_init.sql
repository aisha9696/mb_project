create table product_category (

                                  id uuid not null
                                      constraint product_category_pk_id
                                          primary key,
                                  value_ru   varchar(255),
                                  value_kz   varchar(255),
                                  type varchar(255),
                                  archived   boolean,
                                  business_type_id uuid constraint product_category_fk_business_type_id references business_type,
    parent_id uuid constraint parent_product_category_fk_id references product_category
);

create table product_category_business_type (
    product_category_id uuid not null constraint product_category_business_type_fk_product_category_id references product_category,
    business_type_id uuid not null constraint product_category_business_type_fk_business_type_id references business_type
);

