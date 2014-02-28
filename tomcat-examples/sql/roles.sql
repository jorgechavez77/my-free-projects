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
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('11603',NULL,NULL,NULL,'SAT',NULL,NULL,'1',NULL);
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('11604',NULL,NULL,NULL,'Clientes',NULL,NULL,'1',NULL);
insert into `role_` (`roleId`, `companyId`, `classNameId`, `classPK`, `name`, `title`, `description`, `type_`, `subtype`) values('11745',NULL,NULL,NULL,'Cliente restringido',NULL,NULL,'2',NULL);
