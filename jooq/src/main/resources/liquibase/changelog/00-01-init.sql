--liquibase formatted sql

--changeset ChSergeiG:initial_schema_type context:ddl

CREATE TYPE STATUS AS ENUM ('ADDED' ,'ACTIVE', 'REMOVED', 'DISABLED');

--changeset ChSergeiG:initial_schema_tables context:ddl

CREATE TABLE ACCOUNTS
(
    ID       SERIAL        NOT NULL,
    LOGIN    VARCHAR(1024) NOT NULL,
    PASSWORD VARCHAR(1024) NOT NULL,
    SALTED   BOOLEAN       NOT NULL DEFAULT FALSE,
    STATUS   STATUS        NOT NULL DEFAULT 'ADDED'
);

CREATE TABLE GOODS
(
    ID      SERIAL        NOT NULL,
    NAME    VARCHAR(1024) NOT NULL,
    ARTICLE INT,
    STATUS  STATUS        NOT NULL DEFAULT 'ADDED'
);

CREATE TABLE ORDERS
(
    ID     SERIAL NOT NULL,
    INFO   VARCHAR(16384)  DEFAULT '',
    STATUS STATUS NOT NULL DEFAULT 'ADDED'
);

CREATE TABLE ACCOUNTS_ORDERS
(
    ACCOUNT_ID  SERIAL NOT NULL,
    ORDER_ID SERIAL NOT NULL
);

CREATE TABLE ORDERS_GOODS
(
    ORDER_ID SERIAL NOT NULL,
    GOOD_ID  SERIAL NOT NULL
);

CREATE UNIQUE INDEX ACCOUNT_ID_UINDEX ON ACCOUNTS (ID);

CREATE UNIQUE INDEX ACCOUNT_LOGIN_UINDEX ON ACCOUNTS (LOGIN);

ALTER TABLE GOODS
    ADD CONSTRAINT GOOD_PK PRIMARY KEY (ID);

ALTER TABLE ORDERS
    ADD CONSTRAINT ORDER_PK PRIMARY KEY (ID);

ALTER TABLE ACCOUNTS
    ADD CONSTRAINT ACCOUNT_PK PRIMARY KEY (ID);

ALTER TABLE ACCOUNTS_ORDERS
    ADD FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNTS (ID);

ALTER TABLE ACCOUNTS_ORDERS
    ADD FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID);

ALTER TABLE ORDERS_GOODS
    ADD FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID);

ALTER TABLE ORDERS_GOODS
    ADD FOREIGN KEY (GOOD_ID) REFERENCES GOODS (ID);
