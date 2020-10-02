--DROP DATABASE IF EXISTS testdb;
--CREATE DATABASE testdb DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS UserAccount;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Permission;
DROP TABLE IF EXISTS UserAccount_Role;
DROP TABLE IF EXISTS Role_Permission;

CREATE TABLE UserAccount
(
   userName             VARCHAR(32) NOT NULL,
   password             VARCHAR(255) NOT NULL,
   isDeleted            BOOLEAN NOT NULL,
   isLocked             BOOLEAN NOT NULL,
   PRIMARY KEY (userName)
);

CREATE TABLE Role
(
   roleName       VARCHAR(32) NOT NULL,
   description    VARCHAR(255) NOT NULL,
   PRIMARY KEY (roleName)
);

CREATE TABLE Permission
(
   permissionName       VARCHAR(32) NOT NULL,
   description          VARCHAR(255) NOT NULL,
   PRIMARY KEY (permissionName)
);

CREATE TABLE UserAccount_Role
(
   userName             VARCHAR(32) NOT NULL,
   roleName       VARCHAR(32) NOT NULL,
   primary key (userName, roleName)
);

CREATE TABLE Role_Permission
(
   roleName             VARCHAR(32) NOT NULL,
   permissionName       VARCHAR(32) NOT NULL,
   primary key (roleName, permissionName)
);


