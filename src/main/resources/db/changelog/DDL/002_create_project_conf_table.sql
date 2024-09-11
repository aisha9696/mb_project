CREATE TABLE IF NOT EXISTS project_settings.project_configuration
(
    id                  BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    configuration_name  varchar(100),
    configuration_value varchar(255)
);
