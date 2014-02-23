/*
SQLyog Community v11.31 (64 bit)
MySQL - 5.1.48-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `user_` (
	`uuid_` varchar (225),
	`userId` bigint (20),
	`companyId` bigint (20),
	`createDate` datetime ,
	`modifiedDate` datetime ,
	`defaultUser` tinyint (4),
	`contactId` bigint (20),
	`password_` varchar (225),
	`passwordEncrypted` tinyint (4),
	`passwordReset` tinyint (4),
	`passwordModifiedDate` datetime ,
	`digest` varchar (765),
	`reminderQueryQuestion` varchar (225),
	`reminderQueryAnswer` varchar (225),
	`graceLoginCount` int (11),
	`screenName` varchar (225),
	`emailAddress` varchar (225),
	`facebookId` bigint (20),
	`openId` varchar (3072),
	`portraitId` bigint (20),
	`languageId` varchar (225),
	`timeZoneId` varchar (225),
	`greeting` varchar (765),
	`comments` text ,
	`firstName` varchar (225),
	`middleName` varchar (225),
	`lastName` varchar (225),
	`jobTitle` varchar (300),
	`loginDate` datetime ,
	`loginIP` varchar (225),
	`lastLoginDate` datetime ,
	`lastLoginIP` varchar (225),
	`lastFailedLoginDate` datetime ,
	`failedLoginAttempts` int (11),
	`lockout` tinyint (4),
	`lockoutDate` datetime ,
	`agreedToTermsOfUse` tinyint (4),
	`emailAddressVerified` tinyint (4),
	`status` int (11)
); 
insert into `user_` (`uuid_`, `userId`, `companyId`, `createDate`, `modifiedDate`, `defaultUser`, `contactId`, `password_`, `passwordEncrypted`, `passwordReset`, `passwordModifiedDate`, `digest`, `reminderQueryQuestion`, `reminderQueryAnswer`, `graceLoginCount`, `screenName`, `emailAddress`, `facebookId`, `openId`, `portraitId`, `languageId`, `timeZoneId`, `greeting`, `comments`, `firstName`, `middleName`, `lastName`, `jobTitle`, `loginDate`, `loginIP`, `lastLoginDate`, `lastLoginIP`, `lastFailedLoginDate`, `failedLoginAttempts`, `lockout`, `lockoutDate`, `agreedToTermsOfUse`, `emailAddressVerified`, `status`) values('d8e892cd-5a20-49b2-9c3f-9276be56ba20','10157','10153','2014-02-22 20:57:24','2014-02-22 20:57:24','1','10158','password','0','0',NULL,'5533ed38b5e33c076a804bb4bca644f9,0347dafacbca54a43d2f7e97bbae2275,0347dafacbca54a43d2f7e97bbae2275','','','0','10157','default@liferay.com','0','','0','en_US','UTC','Welcome!','','','','','','2014-02-22 20:57:24','',NULL,'',NULL,'0','0',NULL,'1','0','0');
insert into `user_` (`uuid_`, `userId`, `companyId`, `createDate`, `modifiedDate`, `defaultUser`, `contactId`, `password_`, `passwordEncrypted`, `passwordReset`, `passwordModifiedDate`, `digest`, `reminderQueryQuestion`, `reminderQueryAnswer`, `graceLoginCount`, `screenName`, `emailAddress`, `facebookId`, `openId`, `portraitId`, `languageId`, `timeZoneId`, `greeting`, `comments`, `firstName`, `middleName`, `lastName`, `jobTitle`, `loginDate`, `loginIP`, `lastLoginDate`, `lastLoginIP`, `lastFailedLoginDate`, `failedLoginAttempts`, `lockout`, `lockoutDate`, `agreedToTermsOfUse`, `emailAddressVerified`, `status`) values('3a2c45cc-d518-4159-b9e3-8a2131580b9c','10195','10153','2014-02-22 20:57:28','2014-02-22 20:57:28','0','10197','qUqP5cyxm6YcTAhz05Hph5gvu9M=','1','0',NULL,'','','','0','test','test@liferay.com','0','','0','en_US','UTC','Welcome Test Test!','','Test','','Test','','2014-02-22 20:57:29','','2014-02-22 20:57:29','',NULL,'0','0',NULL,'0','1','5');
insert into `user_` (`uuid_`, `userId`, `companyId`, `createDate`, `modifiedDate`, `defaultUser`, `contactId`, `password_`, `passwordEncrypted`, `passwordReset`, `passwordModifiedDate`, `digest`, `reminderQueryQuestion`, `reminderQueryAnswer`, `graceLoginCount`, `screenName`, `emailAddress`, `facebookId`, `openId`, `portraitId`, `languageId`, `timeZoneId`, `greeting`, `comments`, `firstName`, `middleName`, `lastName`, `jobTitle`, `loginDate`, `loginIP`, `lastLoginDate`, `lastLoginIP`, `lastFailedLoginDate`, `failedLoginAttempts`, `lockout`, `lockoutDate`, `agreedToTermsOfUse`, `emailAddressVerified`, `status`) values('945fb3e6-f4e0-4acb-a382-39e68bde40a9','10406','10153','2014-02-22 20:57:45','2014-02-22 20:57:45','0','10407','qUqP5cyxm6YcTAhz05Hph5gvu9M=','1','1',NULL,'','','','0','admin','admin@liferay.com','0','','0','en_US','UTC','Welcome Admin Admin!','','Admin','','Admin','','2014-02-22 20:57:45','','2014-02-22 20:57:45','',NULL,'0','0',NULL,'0','1','0');
