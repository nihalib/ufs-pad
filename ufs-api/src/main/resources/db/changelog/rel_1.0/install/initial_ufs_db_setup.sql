--liquibase formatted sql
CREATE SCHEMA ufs_pad;

-- create CIAM table
CREATE SEQUENCE ufs_pad.ufs_pad_ciam_sqc INCREMENT 10 START 1;

create table ufs_pad.ciam
(
    id                      bigint          not null    default     nextval(('ufs_pad.ufs_pad_ciam_sqc'::text)::regclass),
    ciam_id                 bigint          not null,
    created_at              timestamp with time zone not null,
    updated_at              timestamp with time zone not null,
    timezone                varchar(50),
    location                varchar(50),
    currency_code           varchar(50),
    is_test_data            boolean default false not null
);
ALTER SEQUENCE ufs_pad.ufs_pad_ciam_sqc OWNED BY ufs_pad.ciam.id;

ALTER TABLE ufs_pad.ciam ADD CONSTRAINT pk_ciam PRIMARY KEY (id);
ALTER TABLE ufs_pad.ciam ADD CONSTRAINT unique_ciam_id UNIQUE (ciam_id);

create index idx_ciam_id on ufs_pad.ciam (ciam_id);

-- create Vehicle table
CREATE SEQUENCE ufs_pad.ufs_pad_vehicle_sqc INCREMENT 10 START 1;

create table ufs_pad.vehicle
(
    id                      bigint          not null    default     nextval(('ufs_pad.ufs_pad_vehicle_sqc'::text)::regclass),
    ciam_id                 bigint          not null,
    provider_id             varchar(16)     not null,
    location                varchar(50),
    currency_code           varchar(50),
    vin                     varchar(16)     not null,
    charging_mode           varchar(3),
    registered_at           timestamp with time zone not null,
    timezone                varchar(50),
    is_test_data            boolean default false not null
);
ALTER SEQUENCE ufs_pad.ufs_pad_vehicle_sqc OWNED BY ufs_pad.vehicle.id;

ALTER TABLE ufs_pad.vehicle ADD CONSTRAINT pk_vehicle PRIMARY KEY (id);
ALTER TABLE ufs_pad.vehicle ADD CONSTRAINT unique_ciam_vehicle UNIQUE (ciam_id, vin);
ALTER TABLE ufs_pad.vehicle ADD CONSTRAINT fk_ciam_vehicle FOREIGN KEY (ciam_id) REFERENCES ufs_pad.ciam(id)
    ON UPDATE CASCADE ON DELETE CASCADE;

create index idx_vehicle_id on ufs_pad.vehicle (vin);

-- create Profile table
CREATE SEQUENCE ufs_pad.ufs_pad_profile_sqc INCREMENT 10 START 1;

create table ufs_pad.profile
(
    id                      bigint          not null    default     nextval(('ufs_pad.ufs_pad_profile_sqc'::text)::regclass),
    ciam_id                 bigint          not null,
    salute                  varchar(6),
    first_name              varchar(32)     not null,
    last_name               varchar(32)     not null,
    ssn                     varchar(32),
    pincode                 varchar(16),
    created_at              timestamp with time zone not null,
    updated_at              timestamp with time zone not null,
    timezone                varchar(50),
    is_test_data            boolean default false not null
);
ALTER SEQUENCE ufs_pad.ufs_pad_profile_sqc OWNED BY ufs_pad.profile.id;

ALTER TABLE ufs_pad.profile ADD CONSTRAINT pk_profile PRIMARY KEY (id);
ALTER TABLE ufs_pad.profile ADD CONSTRAINT unique_name_ssn UNIQUE (first_name, ssn);
ALTER TABLE ufs_pad.profile ADD CONSTRAINT fk_ciam_profile FOREIGN KEY (ciam_id) REFERENCES ufs_pad.ciam(id)
    ON UPDATE CASCADE ON DELETE CASCADE;

create index idx_profile_id on ufs_pad.profile (id);