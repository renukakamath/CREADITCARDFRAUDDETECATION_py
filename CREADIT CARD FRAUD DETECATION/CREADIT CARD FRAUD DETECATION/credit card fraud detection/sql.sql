/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - credit_card_py
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`credit_card_py` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `credit_card_py`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `accountnumber` varchar(100) DEFAULT NULL,
  `balance` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `account` */

insert  into `account`(`account_id`,`user_id`,`accountnumber`,`balance`,`status`) values 
(1,1,'50702933515','1400','amount added'),
(2,2,'182787844371','4997500','pending'),
(3,2,'692086273622','10000','amount added'),
(4,3,'12345678901234567','1400','amount added');

/*Table structure for table `creditcard` */

DROP TABLE IF EXISTS `creditcard`;

CREATE TABLE `creditcard` (
  `creditcard_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `cardnum` varchar(100) DEFAULT NULL,
  `month` varchar(100) DEFAULT NULL,
  `pin_no` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `balance` varchar(100) DEFAULT NULL,
  `ifsc_code` varchar(100) DEFAULT NULL,
  `bank_num` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`creditcard_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `creditcard` */

insert  into `creditcard`(`creditcard_id`,`user_id`,`cardnum`,`month`,`pin_no`,`date`,`balance`,`ifsc_code`,`bank_num`) values 
(1,1,'1223214214111111','2022-07','2345','2022-10-17','5900','QL24','212378279'),
(2,3,'1234567891234568','12/2002','123','2023-04-10','0','rwgv','12745188'),
(3,3,'1234567891234567','12/2002','123','2023-04-10','0','rwgv','12745188'),
(4,3,'1234567891234567','12/2002','123','2023-04-10','4352352','rwgv','12745188'),
(5,3,'1234567891234567','12/2002','123','2023-04-10','0','rwgv','12745188'),
(6,3,'1234567891234567','12/2002','123','2023-04-10','0','rwgv','12745188'),
(7,3,'1234567891234567','12/2002','123','2023-04-10','0','rwgv','12745188'),
(8,2,'1264567891234567','12/2042','123','2023-04-10','2423424','dggdgs','5555582588'),
(9,1,'12345678901234567','12/2004','5445','2023-04-17','5900','fsj','646188446'),
(10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'4342442784');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(5,'hai','hai','user'),
(2,'user1','user1','user'),
(3,'admin','admin','admin'),
(4,'user2','user2','user');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`user_id`,`details`,`date`,`status`) values 
(1,1,'HGKJGJ','2022-09-23','accept'),
(2,2,'HGKJGJ','2022-09-16','accept'),
(3,2,'HGKJGJ','2022-09-25','accept'),
(4,1,'HGKJGJ','2022-10-13','accept'),
(5,3,'dhdbdhd','848484','pending'),
(6,3,'dhdbdhd','848484','pending'),
(7,3,'dhdbdhd','848484','pending');

/*Table structure for table `transaction` */

DROP TABLE IF EXISTS `transaction`;

CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `faccount` varchar(100) DEFAULT NULL,
  `taccount` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `transaction` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`) values 
(1,2,'user1','user1','ernakulam','1234567890','aparnabhaasi@gmail.com'),
(2,4,'user2','user2','ernakulam','5432545111','employee@gmail.com'),
(3,5,'Renuka','Kamath','gdgdgdgh','84848176181','renukakamath2@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
