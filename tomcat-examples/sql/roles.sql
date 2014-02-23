/*
SQLyog Community v11.31 (64 bit)
MySQL - 5.1.48-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `role_` (
	`roleId` bigint (20),
	`companyId` bigint (20),
	`classNameId` bigint (20),
	`classPK` bigint (20),
	`name` varchar (225),
	`title` text ,
	`description` text ,
	`type_` int (11),
	`subtype` varchar (225)
); 
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10160','10153','10004','10160','Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Administrators are super users who can do anything.</Description></root>','1','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10161','10153','10004','10161','Guest','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Unauthenticated users always have this role.</Description></root>','1','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10162','10153','10004','10162','Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">This is an implied role with respect to the objects users create.</Description></root>','1','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10163','10153','10004','10163','Power User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Power Users have their own personal site.</Description></root>','1','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10164','10153','10004','10164','User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Authenticated users should be assigned this role.</Description></root>','1','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10165','10153','10004','10165','Organization Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Organization Administrators are super users of their organization but cannot make other users into Organization Administrators.</Description></root>','3','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10166','10153','10004','10166','Organization Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Organization Owners are super users of their organization and can assign organization roles to users.</Description></root>','3','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10167','10153','10004','10167','Organization User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">All users who belong to an organization have this role within that organization.</Description></root>','3','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10168','10153','10004','10168','Site Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Site Administrators are super users of their site but cannot make other users into Site Administrators.</Description></root>','2','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10169','10153','10004','10169','Site Member','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">All users who belong to a site have this role within that site.</Description></root>','2','');
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('10170','10153','10004','10170','Site Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Site Owners are super users of their site and can assign site roles to users.</Description></root>','2','');
