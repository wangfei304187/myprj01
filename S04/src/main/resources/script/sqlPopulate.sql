INSERT INTO UserAccount(userName, password, isDeleted, isLocked) VALUES ('lili', '123456', false, false);
INSERT INTO UserAccount(userName, password, isDeleted, isLocked) VALUES ('lucy', '123456', false, false);

INSERT INTO Role(roleName, description) VALUES ('DBA', 'this is role dba');
INSERT INTO Role(roleName, description) VALUES ('Admin', 'this is role admin');
INSERT INTO Role(roleName, description) VALUES ('Operator', 'this is role operator');

INSERT INTO Permission(permissionName, description) VALUES ('sys:read', 'read permission');
INSERT INTO Permission(permissionName, description) VALUES ('sys:write', 'write permission');
INSERT INTO Permission(permissionName, description) VALUES ('db:read,write', 'db read & writes permission');


INSERT INTO Role_Permission(roleName, permissionName) VALUES ('DBA', 'db:read,write');
INSERT INTO Role_Permission(roleName, permissionName) VALUES ('DBA', 'sys:write');
INSERT INTO Role_Permission(roleName, permissionName) VALUES ('DBA', 'sys:read');

INSERT INTO Role_Permission(roleName, permissionName) VALUES ('Admin', 'sys:write');
INSERT INTO Role_Permission(roleName, permissionName) VALUES ('Admin', 'sys:read');

INSERT INTO Role_Permission(roleName, permissionName) VALUES ('Operator', 'sys:read');

INSERT INTO UserAccount_Role(userName, roleName) VALUES ('lili', 'DBA');
INSERT INTO UserAccount_Role(userName, roleName) VALUES ('lili', 'Admin');
INSERT INTO UserAccount_Role(userName, roleName) VALUES ('lucy', 'Operator');

