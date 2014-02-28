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
INSERT INTO user_ (userId, password_, screenName) VALUES (11645, '', 'tecnico01');
INSERT INTO user_ (userId, password_, screenName) VALUES (13065, '', 'tecnico02');
INSERT INTO user_ (userId, password_, screenName) VALUES (13075, '', 'tecnico03');
INSERT INTO user_ (userId, password_, screenName) VALUES (13205, '', 'cliente01');
INSERT INTO user_ (userId, password_, screenName) VALUES (13212, '', 'cliente02');
INSERT INTO user_ (userId, password_, screenName) VALUES (13219, '', 'cliente03');
INSERT INTO user_ (userId, password_, screenName) VALUES (13226, '', 'cliente04');
INSERT INTO user_ (userId, password_, screenName) VALUES (13233, '', 'cliente05');

