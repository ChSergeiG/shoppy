--liquibase formatted sql

--changeset ChSergeiG:order_guid context:ddl

ALTER TABLE ORDERS
    ADD COLUMN GUID VARCHAR(36) DEFAULT NULL;
