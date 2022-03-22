--liquibase formatted sql

--changeset ChSergeiG:account_roles context:ddl

CREATE TYPE ACCOUNT_ROLE AS ENUM ('ROLE_ADMIN' ,'ROLE_USER', 'ROLE_GUEST');

--changeset ChSergeiG:account_role_model context:ddl

CREATE TABLE ACCOUNTS_ROLES
(
    ACCOUNT_ID SERIAL       NOT NULL,
    ROLE       ACCOUNT_ROLE NOT NULL DEFAULT 'ROLE_GUEST'
);

ALTER TABLE ACCOUNTS_ROLES
    ADD FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNTS (ID);

ALTER TABLE ACCOUNTS_ROLES
    ADD CONSTRAINT ACCOUNT_ID_ROLE_PK PRIMARY KEY (ACCOUNT_ID, ROLE);
