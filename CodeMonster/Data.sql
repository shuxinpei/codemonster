/*
SQLyog Job Agent Version 10.2 Copyright(c) Webyog Inc. All Rights Reserved.


MySQL - 5.5.49 : Database - codemonster
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`codemonster` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `codemonster`;

/*Table structure for table `usermonthcode` */

DROP TABLE IF EXISTS `usermonthcode`;

CREATE TABLE `usermonthcode` (
  `UserID` int(11) DEFAULT NULL,
  `MonthNumber` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `usermonthcode` */

insert  into `usermonthcode` values (16,364),(17,10142),(18,0);

/*Table structure for table `userweekcode` */

DROP TABLE IF EXISTS `userweekcode`;

CREATE TABLE `userweekcode` (
  `UserID` int(11) DEFAULT NULL,
  `WeekNumber` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `userweekcode` */

insert  into `userweekcode` values (16,1157),(17,1316),(18,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
