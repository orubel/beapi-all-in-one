-- MySQL dump 10.16  Distrib 10.1.44-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: lawhq
-- ------------------------------------------------------
-- Server version	10.1.44-MariaDB-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `zip` bigint(20) NOT NULL,
  `city` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `state` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKou0bmf7gx9jjlfohuufsk2fom` (`user_id`),
  CONSTRAINT `FKou0bmf7gx9jjlfohuufsk2fom` FOREIGN KEY (`user_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,0,'30714 null Shawnee',NULL,65355,'Warsaw',56,'MO'),(2,0,'14872 South Sliding Rock Way',NULL,84096,'Herriman',57,'UT'),(3,0,'2414 W. 280 S.',NULL,84601,'Provo',58,'UT'),(4,0,'2413 W. 280 S.',NULL,84601,'Provo',59,'UT'),(5,0,'878 null Wymount',NULL,84604,'Provo',60,'UT'),(6,0,'8537 South Johnson Way Circle',NULL,84094,'Sandy',61,'UT'),(7,0,'4246 SW Highway T',NULL,64735,'Clinton',62,'MO'),(8,0,'1133 North 1880 West',NULL,84604,'Provo',63,'UT'),(9,0,'4076 W Shinnecock',NULL,84062,'Cedar Hills',64,'UT'),(10,0,'9407 23rd Ave NE Apt 6',NULL,98115,'Seattle',65,'WA'),(11,0,'2579 N 320 E',NULL,84604,'Provo',66,'UT'),(12,0,'419 s 1000 e',NULL,84097,'Orem',67,'UT'),(13,0,'722 E 1720',NULL,84097,'Orem',68,'UT'),(14,0,'504 W 750 S',NULL,84058,'OREM',69,'UT'),(15,0,'10782 n la costa',NULL,84062,'Cedar Hills',70,'UT'),(16,0,'7711 O’Connor Dr.','Apt 1801',78681,'Round Rock',71,'TX'),(17,0,'117 E Center St',NULL,84010,'Bountiful',72,'UT'),(18,0,'980 E 2680 N',NULL,84604,'Provo',73,'UT'),(19,0,'1133 N 1880',NULL,84604,'Provo',74,'UT'),(20,0,'4246 SW Highway T',NULL,64735,'Clinton',75,'MO'),(21,0,'553 N 100 E',NULL,84660,'Spanish Fork',76,'UT'),(22,0,'9986 Tamara St',NULL,84094,'Sandy',77,'UT'),(23,0,'12938 S Riverton farms way',NULL,84065,'Riverton',78,'UT'),(24,0,'5436 S Black Mica Cir',NULL,84118,'Salt Lake City',79,'UT'),(25,0,'2442 martini way',NULL,30620,'Bethlehem',80,'GA'),(26,0,'10782 n la costa',NULL,84062,'cedar hills',82,'UT'),(27,0,'4444 null Wimbledon',NULL,84604,'Provo',83,'UT'),(28,0,'5282 s 575 w',NULL,84405,'Ogden',84,'UT'),(29,0,'3901 SW 154th',NULL,33331,'Davie',85,'FL'),(30,0,'13545 S. Villa Rosa Way',NULL,84020,'Draper',86,'UT'),(31,0,'718 E Clinton',NULL,64735,'Clinton',87,'MO'),(32,0,'3144 null Kelly',NULL,20032,'Bolling AFB',88,'DC'),(33,0,'1 central avenue','Albany',12212,'Albany',89,'NY'),(34,0,'2314 Washington Blvd',NULL,84401,'Ogden',90,'UT'),(35,0,'948 E 900 S',NULL,84663,'Springville',91,'UT'),(36,0,'7914 n sparrowhawk cir',NULL,84005,'eagle mountain',92,'UT'),(37,0,'233 E Alhambra',NULL,84045,'Saratoga Springs',93,'UT'),(38,0,'10543 N Sage Vista Ln',NULL,84062,'Cedar Hills',94,'UT'),(39,0,'4246 SW Highway T',NULL,64735,'Clinton',95,'MO'),(40,0,'826 Churchhill Downs Dr',NULL,84037,'Kaysville',96,'UT'),(41,0,'3202 SE 3rd Terrace',NULL,64063,'Lees Summit',82,'MO'),(42,0,'11320 S. Ensign Point Lane #5108',NULL,84020,'Draper',97,'UT'),(43,0,'3202 SE 3rd Ter',NULL,64063,'Lee’s Summit',82,'MO'),(44,0,'4432',NULL,65649,'Fair Play',98,'MO'),(45,0,'2529 W Cactus','apt 1034',85029,'Phoenix',99,'AZ'),(46,0,'12938 s Riverton Farms Way',NULL,84065,'Riverton',100,'UT'),(47,0,'819 S 800 W',NULL,84104,'Salt Lake City',101,'UT'),(48,0,'9938 N Outlook',NULL,84005,'Eagle Mountain',102,'UT'),(49,0,'g',NULL,44420,'Girardddd',103,'OH'),(50,0,'3049 W 12825 S',NULL,84065,'Riverton',104,'UT'),(51,0,'915 N 350',NULL,84057,'Orem',105,'UT'),(52,0,'1403 null Charleston',NULL,64093,'Warrensburg',106,'MO'),(53,0,'1403 null Charleston',NULL,64093,'Warrensburg',107,'MO'),(54,0,'12938 S Riverton Farms Way',NULL,84065,'Riverton',108,'UT'),(55,0,'878 null Wymount',NULL,84604,'Provo',109,'UT'),(56,0,'848 W Morby St',NULL,84780,'Washington',110,'UT'),(57,0,'10782 N La Cost',NULL,84062,'Cedar Hills',111,'UT'),(58,0,'3901 SW 154th',NULL,33331,'Davie',112,'FL'),(59,0,'4653 S 166th',NULL,98188,'Seatac',113,'WA'),(60,0,'3472 w 2550 n',NULL,84043,'Lehi',114,'UT'),(61,0,'1683 east 1450 south',NULL,84404,'ogden',115,'UT'),(62,0,'3901 SW 154th',NULL,33331,'Davie',116,'FL'),(63,0,'30714 null Shawnee',NULL,65355,'Warsaw',56,'MO'),(64,0,'14872 South Sliding Rock Way',NULL,84096,'Herriman',57,'UT'),(65,0,'2414 W. 280 S.',NULL,84601,'Provo',58,'UT'),(66,0,'2413 W. 280 S.',NULL,84601,'Provo',59,'UT'),(67,0,'878 null Wymount',NULL,84604,'Provo',60,'UT'),(68,0,'8537 South Johnson Way Circle',NULL,84094,'Sandy',61,'UT'),(69,0,'4246 SW Highway T',NULL,64735,'Clinton',62,'MO'),(70,0,'1133 North 1880 West',NULL,84604,'Provo',63,'UT'),(71,0,'4076 W Shinnecock',NULL,84062,'Cedar Hills',64,'UT'),(72,0,'9407 23rd Ave NE Apt 6',NULL,98115,'Seattle',65,'WA'),(73,0,'2579 N 320 E',NULL,84604,'Provo',66,'UT'),(74,0,'419 s 1000 e',NULL,84097,'Orem',67,'UT'),(75,0,'722 E 1720',NULL,84097,'Orem',68,'UT'),(76,0,'504 W 750 S',NULL,84058,'OREM',69,'UT'),(77,0,'10782 n la costa',NULL,84062,'Cedar Hills',70,'UT'),(78,0,'7711 O’Connor Dr.','Apt 1801',78681,'Round Rock',71,'TX'),(79,0,'117 E Center St',NULL,84010,'Bountiful',72,'UT'),(80,0,'980 E 2680 N',NULL,84604,'Provo',73,'UT'),(81,0,'1133 N 1880',NULL,84604,'Provo',74,'UT'),(82,0,'4246 SW Highway T',NULL,64735,'Clinton',75,'MO'),(83,0,'553 N 100 E',NULL,84660,'Spanish Fork',76,'UT'),(84,0,'9986 Tamara St',NULL,84094,'Sandy',77,'UT'),(85,0,'12938 S Riverton farms way',NULL,84065,'Riverton',78,'UT'),(86,0,'5436 S Black Mica Cir',NULL,84118,'Salt Lake City',79,'UT'),(87,0,'2442 martini way',NULL,30620,'Bethlehem',80,'GA'),(88,0,'10782 n la costa',NULL,84062,'cedar hills',82,'UT'),(89,0,'4444 null Wimbledon',NULL,84604,'Provo',83,'UT'),(90,0,'5282 s 575 w',NULL,84405,'Ogden',84,'UT'),(91,0,'3901 SW 154th',NULL,33331,'Davie',85,'FL'),(92,0,'13545 S. Villa Rosa Way',NULL,84020,'Draper',86,'UT'),(93,0,'718 E Clinton',NULL,64735,'Clinton',87,'MO'),(94,0,'3144 null Kelly',NULL,20032,'Bolling AFB',88,'DC'),(95,0,'1 central avenue','Albany',12212,'Albany',89,'NY'),(96,0,'2314 Washington Blvd',NULL,84401,'Ogden',90,'UT'),(97,0,'948 E 900 S',NULL,84663,'Springville',91,'UT'),(98,0,'7914 n sparrowhawk cir',NULL,84005,'eagle mountain',92,'UT'),(99,0,'233 E Alhambra',NULL,84045,'Saratoga Springs',93,'UT'),(100,0,'10543 N Sage Vista Ln',NULL,84062,'Cedar Hills',94,'UT'),(101,0,'4246 SW Highway T',NULL,64735,'Clinton',95,'MO'),(102,0,'826 Churchhill Downs Dr',NULL,84037,'Kaysville',96,'UT'),(103,0,'3202 SE 3rd Terrace',NULL,64063,'Lees Summit',82,'MO'),(104,0,'11320 S. Ensign Point Lane #5108',NULL,84020,'Draper',97,'UT'),(105,0,'3202 SE 3rd Ter',NULL,64063,'Lee’s Summit',82,'MO'),(106,0,'4432',NULL,65649,'Fair Play',98,'MO'),(107,0,'2529 W Cactus','apt 1034',85029,'Phoenix',99,'AZ'),(108,0,'12938 s Riverton Farms Way',NULL,84065,'Riverton',100,'UT'),(109,0,'819 S 800 W',NULL,84104,'Salt Lake City',101,'UT'),(110,0,'9938 N Outlook',NULL,84005,'Eagle Mountain',102,'UT'),(111,0,'g',NULL,44420,'Girardddd',103,'OH'),(112,0,'3049 W 12825 S',NULL,84065,'Riverton',104,'UT'),(113,0,'915 N 350',NULL,84057,'Orem',105,'UT'),(114,0,'1403 null Charleston',NULL,64093,'Warrensburg',106,'MO'),(115,0,'1403 null Charleston',NULL,64093,'Warrensburg',107,'MO'),(116,0,'12938 S Riverton Farms Way',NULL,84065,'Riverton',108,'UT'),(117,0,'878 null Wymount',NULL,84604,'Provo',109,'UT'),(118,0,'848 W Morby St',NULL,84780,'Washington',110,'UT'),(119,0,'10782 N La Cost',NULL,84062,'Cedar Hills',111,'UT'),(120,0,'3901 SW 154th',NULL,33331,'Davie',112,'FL'),(121,0,'4653 S 166th',NULL,98188,'Seatac',113,'WA'),(122,0,'3472 w 2550 n',NULL,84043,'Lehi',114,'UT'),(123,0,'1683 east 1450 south',NULL,84404,'ogden',115,'UT'),(124,0,'3901 SW 154th',NULL,33331,'Davie',116,'FL');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `arch`
--

DROP TABLE IF EXISTS `arch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `has_cert` bit(1) NOT NULL,
  `url` varchar(255) NOT NULL,
  `cert_expiration` datetime DEFAULT NULL,
  `master` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hmprayrslvle6kxh3lt6teyga` (`master`),
  KEY `arch_master_idx` (`master`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arch`
--

LOCK TABLES `arch` WRITE;
/*!40000 ALTER TABLE `arch` DISABLE KEYS */;
INSERT INTO `arch` VALUES (1,0,'\0','http://test.nosegrind.net:8080',NULL,'');
/*!40000 ALTER TABLE `arch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authentication_token`
--

DROP TABLE IF EXISTS `authentication_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authentication_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token_value` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authentication_token`
--

LOCK TABLES `authentication_token` WRITE;
/*!40000 ALTER TABLE `authentication_token` DISABLE KEYS */;
INSERT INTO `authentication_token` VALUES (1,'p7f5g96b2luacn1hk5gjjg41atsjqguf','admin'),(2,'qg11ekhmqsnaansbogioqfvh1cdc3iai','admin'),(3,'j33242ed81c5kuilb2fvg0evp2achgv7','admin'),(4,'7trr254bdp1pa0kpdl61705cmsolmqjd','apidoctest'),(5,'gtviv4eku33cfhvnnkgt32ood1q0kjj3','admin'),(6,'70pkrls7jhuglf7fk0evton7epn0edoo','admin'),(7,'6l1avvpt3cdhvri7hr9hf6n52rbl8hpq','admin'),(8,'joteic2ef6lutool1c90q59mn5f2onla','apidoctest'),(9,'i31q1rm2mlen20mok9jpgtmku26ilfqj','admin'),(10,'f4qp595vpotvjdshcujihv14t4sbgpg1','admin'),(11,'bi57iuh9p91fum39k2nv8e90vbd7b8mg','admin'),(12,'fdu2uot420d5edrh4dhtc7q9opt8274b','apidoctest'),(13,'rcs6jk475mkb29qp0182b8jqn1v41kda','admin'),(14,'12ouaa1o6kq5hug1d2qc701ps6uerpdi','admin'),(15,'3is057sdn1au1j6jlbm90uf1cijasjl6','admin'),(16,'flqbkmqvncndhnmfh4fvtoff99f48ujc','apidoctest'),(17,'f799kfpg2dsuq765ak0rca72qivocvkc','admin'),(18,'d1lc1bofv313tn44n5cbo0gfbk3vp6tt','admin'),(19,'kdf0dt5cp9o0t32phpqk5hjjhel7snr9','admin'),(20,'uivg9dr6kjnu265hremp7mv00ce07ht7','apidoctest'),(21,'gurqjh0d4qdq6jkrh9mp9q0faoqns0j8','admin'),(22,'3tg8026je018vulecobt7plgd7th83o0','admin'),(23,'3rd8hh4ftbi074kq5t6e0f5fqg7ku5aq','admin'),(24,'an48cb3dg5lhi74pa7l3l37gqctoc4e0','apidoctest'),(25,'mek3dvcgngcg169dda31cdf38s5osot0','admin'),(26,'fb1g7egb6rt5e3g04kt0nnmnglellthg','admin'),(27,'8uq5dt1g0vj7hd4bikvk9lrb14jgu56l','admin'),(28,'49555l6kiqvbc00j1er1kf3mssfs0o74','apidoctest'),(29,'n9gh0u82d41n3c56u0a1be8sp1c8eea9','admin'),(30,'ln4rp6qk802nm0jnevga3hv9l81j5fps','admin'),(31,'piitf2m2o4bhh7r4gf3jnuh56ipi0f7b','admin'),(32,'blh0irfbiteiri2kb864srq03jge6cgg','apidoctest');
/*!40000 ALTER TABLE `authentication_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hook`
--

DROP TABLE IF EXISTS `hook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hook` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `service` varchar(255) NOT NULL,
  `last_modified` datetime NOT NULL,
  `url` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `authorization` varchar(255) DEFAULT NULL,
  `attempts` bigint(20) NOT NULL,
  `format` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaihuikmldahp68gmealxg88n5` (`user_id`),
  CONSTRAINT `FKaihuikmldahp68gmealxg88n5` FOREIGN KEY (`user_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hook`
--

LOCK TABLES `hook` WRITE;
/*!40000 ALTER TABLE `hook` DISABLE KEYS */;
/*!40000 ALTER TABLE `hook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hook_role`
--

DROP TABLE IF EXISTS `hook_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hook_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `hook_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4c4vis9cjei2l6c7rklveugxm` (`hook_id`),
  KEY `FK8l9jmkpllw3899fik0unk6h5g` (`role_id`),
  CONSTRAINT `FK4c4vis9cjei2l6c7rklveugxm` FOREIGN KEY (`hook_id`) REFERENCES `hook` (`id`),
  CONSTRAINT `FK8l9jmkpllw3899fik0unk6h5g` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hook_role`
--

LOCK TABLES `hook_role` WRITE;
/*!40000 ALTER TABLE `hook_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `hook_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_login`
--

DROP TABLE IF EXISTS `persistent_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_login` (
  `series` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` datetime NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_login`
--

LOCK TABLES `persistent_login` WRITE;
/*!40000 ALTER TABLE `persistent_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `password_expired` bit(1) NOT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `account_expired` bit(1) NOT NULL,
  `oauth_provider` varchar(255) DEFAULT NULL,
  `email_verified` bit(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `oauth_id` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fwmwi44u55bo4rvwsv0cln012` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,0,'null','\0',NULL,'\0',NULL,'\0','admin','\0','$2a$10$p1.rBJMEPHzNUJfUCKFZhuqEEInUMHSbyvO4mW8g0Dgh8TGeytiEu','null',NULL,'',NULL,'youremail@yourdomain.com'),(2,0,'null','\0',NULL,'\0',NULL,'\0','test','\0','$2a$10$ukgGY9Vd0/hrZKP94BrrceClvt1EoE14T.sATeOhK0YWcrHlmKsfe','null',NULL,'',NULL,'test@yourdomain.com'),(56,0,'Cindy','\0',NULL,'\0','google','','grahams98@embarqmail.com','\0','$2a$10$NrYlEb3LN1b5n3Eu57IoW.Tjgk.eTaFwgRVp9qQBt77FioiI60ifi','Graham',NULL,'',NULL,'grahams98@embarqmail.com'),(57,0,'Thomas','\0',NULL,'\0','null','\0','thomas.n.hutchings@gmail.com','\0','$2a$10$dvZknFMyPUwRbHb9CSxA2euzccT1l20rY1G8SwM3f8.koe001Eaq6','Hutchings',NULL,'',NULL,'thomas.n.hutchings@gmail.com'),(58,0,'Kristin','\0',NULL,'\0','null','\0','walters912@gmail.com','\0','$2a$10$f7oIKpoM8IA10EAW8n63AOG3ciXD6jg7mwQ7UcgKBjGFsfbaqItYq','Walters',NULL,'',NULL,'walters912@gmail.com'),(59,0,'Nicholas','\0',NULL,'\0','null','\0','nmwalters@live.com','\0','$2a$10$xHrm3Ro7AHjDWaO94q6e0eAGhsnLxWEZMC8y0pDSc38hND7L5fLM2','Walters',NULL,'',NULL,'nmwalters@live.com'),(60,0,'Taylor','\0',NULL,'\0','google','\0','taylor.j.smith22@gmail.com','\0','$2a$10$3xDQJt7dbGroGB5367b2cOba.a1G1.rmQGb4WuFHqo9NqIaTUhLMa','Smith',NULL,'',NULL,'taylor.j.smith22@gmail.com'),(61,0,'Theron','\0',NULL,'\0','null','\0','theronstaceysheppick@gmail.com','\0','$2a$10$q9dDPjGYEcy3lhih/Mcs8eyxE5NPBkxzC6TnA7TgmcLDdyJP4/KH6','Sheppick',NULL,'',NULL,'theronstaceysheppick@gmail.com'),(62,0,'Ainsley','\0',NULL,'\0','google','','ainsleywest01@gmail.com','\0','$2a$10$jSmOrQmNLq9x19g.D.837.cF6P1J18UrTehmIEwzVpDjQwAR.QB/i','West',NULL,'',NULL,'ainsleywest01@gmail.com'),(63,0,'Stephanie','\0',NULL,'\0','null','\0','runsteph123@gmail.com','\0','$2a$10$5ywPO.1SYj6u2V1R0IgK1.Vcr3vJpbje7NvVcXMXrYjmZ88zlOfH6','Willmore',NULL,'',NULL,'runsteph123@gmail.com'),(64,0,'Nathaniel','\0',NULL,'\0','null','\0','nate.shields33@gmail.com','\0','$2a$10$U2hkIX7EcQUeThdtbgo9fukQauI0eRkBVNaawxj1MPe95XA9PER0G','Shields',NULL,'',NULL,'nate.shields33@gmail.com'),(65,0,'Brooke','\0',NULL,'\0','null','\0','brookeith92@gmail.com','\0','$2a$10$WOLdz1aREp/s/2QEo2AelegU.ep2L6GoMKc6XSz3j1q0xxnoNlkRS','Alston',NULL,'',NULL,'brookeith92@gmail.com'),(66,0,'Jeff','\0',NULL,'\0','null','\0','jbsmith73@yahoo.com','\0','$2a$10$ZF.mpdJM23L.I0ctgbRnGuR.zZPjXJFPN3FH7kk9Gwv.uNHIr1DjG','Smith',NULL,'',NULL,'jbsmith73@yahoo.com'),(67,0,'Karissa','\0',NULL,'\0','null','\0','karissakenney@gmail.com','\0','$2a$10$l85Fnql0jLlRn6Ccs3pxDeULeM9rz.zkbJwUocUzfVAI188pknGOm','Kenney',NULL,'',NULL,'karissakenney@gmail.com'),(68,0,'Lacey','\0',NULL,'\0','null','\0','laceycferrin@gmail.com','\0','$2a$10$G8iZTxrnvN9og9nwbsMNCOdTN6XYcsDUcP1/lW14p4aI.0KJcj0j6','Ferrin',NULL,'',NULL,'laceycferrin@gmail.com'),(69,0,'Aaron','\0',NULL,'\0','null','\0','ldsaaronb@gmail.com','\0','$2a$10$wg29onT4Xp3iJX9emg0JouVlENiW2LF.Frdp.dEPo2plLmu7/kjfC','Bylund',NULL,'',NULL,'ldsaaronb@gmail.com'),(70,0,'Thomas','\0',NULL,'\0','google','','thomasalvord@gmail.com','\0','$2a$10$TJmAH73gnAqYe3x8QpnV1.extEbGMjmV5Dg3dI6xpsIosPLQFOuXC','Alvord',NULL,'',NULL,'thomasalvord@gmail.com'),(71,0,'Jonathan','\0',NULL,'\0','null','\0','jonweaver2006@yahoo.com','\0','$2a$10$9ClPtTy.AdXTF0sCG3sD0uydU24T2Lu5rYvfVFUCI4iS7elA6K8D6','Weaver',NULL,'',NULL,'jonweaver2006@yahoo.com'),(72,0,'Tyler','\0',NULL,'\0','null','\0','tylergmackay@gmail.com','\0','$2a$10$.Kn9Zf9gotQ3mVe9PNK05ezVVrK/9v2wbyWIwcIuQXYkYpJx1r1/a','MacKay',NULL,'',NULL,'tylergmackay@gmail.com'),(73,0,'Thomas','\0',NULL,'\0','null','\0','tbriscoe@kba.law','\0','$2a$10$Dfrcmlxbj0h9eY0y/4iqUu3b0jDf9BC1w/E5APfY/E8.qbNiADk1e','Briscoe',NULL,'',NULL,'tbriscoe@kba.law'),(74,0,'Brent','\0',NULL,'\0','google','\0','brentwillmore@gmail.com','\0','$2a$10$9/3JCZ/VbLlhO9CbuPVqIOAsqUx8o3fk.z9tgeKwhPDmcCCiD6Tny','Willmore',NULL,'',NULL,'brentwillmore@gmail.com'),(75,0,'Robert','\0',NULL,'\0','google','','robert.c.west1170@gmail.com','\0','$2a$10$ktRp72rDwRkiP5tpOp8nMunGYAq.wrMWOh8zUqLG6wvcMi0Vfk2Bq','West',NULL,'',NULL,'robert.c.west1170@gmail.com'),(76,0,'Craig','\0',NULL,'\0','null','\0','foutz.craig@gmail.com','\0','$2a$10$DIlE6IQ1tESiT6SBU1DL7uJpUwCTGVjWnhUjfbamdn0qLNE1bPskq','Foutz',NULL,'',NULL,'foutz.craig@gmail.com'),(77,0,'Stacy','\0',NULL,'\0','null','\0','namastace11@gmail.com','\0','$2a$10$WBxhXPd5nNqnbu3q4UAbHuwN7hOwrAOWgq8p8aTBATWM0907RxD66','Hansen',NULL,'',NULL,'namastace11@gmail.com'),(78,0,'Liz','\0',NULL,'\0','null','\0','liz.f.evans@gmail.com','\0','$2a$10$soFidl77vSG.w8L58d0vY.bHrdiSDFxdynhegPvFKORrETacUX7Eq','Evans',NULL,'',NULL,'liz.f.evans@gmail.com'),(79,0,'Anthony','\0',NULL,'\0','null','\0','anthonyloubet@outlook.com','\0','$2a$10$SgRhz8Vt04Q8/jN2yV89supiSS8d5WL6mGFuYak5KGQ8yVNYxnOJu','Loubet',NULL,'',NULL,'anthonyloubet@outlook.com'),(80,0,'Braden','\0',NULL,'\0','null','\0','abradenb@gmail.com','\0','$2a$10$NX1ZXCKya92VlPS6yfn8hunvEFJmqqHTE6Q8xTIjhhPFLKFerQ2XS','Bissegger',NULL,'',NULL,'abradenb@gmail.com'),(81,0,'Navid','\0',NULL,'\0','null','\0','navidfa@byulaw.net','\0','$2a$10$T0geXdS09in9kyDzsbVYDeJxLZtsJjPQ4sygd5C8JQ92gODHgB8Mi','Farzan',NULL,'',NULL,'navidfa@byulaw.net'),(82,0,'Thomas','\0',NULL,'\0','null','\0','thomas@lawhq.com','\0','$2a$10$CRrotoooN/eFz3IhJ4Jtr./vKsdHIHZSFZ4JD4zLPpSziW12/QbWK','Alvord',NULL,'',NULL,'thomas@lawhq.com'),(83,0,'Trent','\0',NULL,'\0','google','\0','trentdalvord@gmail.com','\0','$2a$10$Mv9c83SuVSX2pM1ja8lzSeSL.45NUTV4gMQ0p7c5gPPWTilJmuSca','Alvord',NULL,'',NULL,'trentdalvord@gmail.com'),(84,0,'Justin','\0',NULL,'\0','null','\0','justin@cutztreez.com','\0','$2a$10$yusOQKNYdp82lSYYHBSJ7eRXBU45DpVqtALaKdbtmo/x0Zq7mjmBq','baker',NULL,'',NULL,'justin@cutztreez.com'),(85,0,'Rostyslav','\0',NULL,'\0','google','\0','rostyslav.holovko@gmail.com','\0','$2a$10$qP3FLQVae0DqxmWwM.LA0.UpgQXyb9hrxiMB5DpB6TXMf5mlN36Em','Holovko',NULL,'',NULL,'rostyslav.holovko@gmail.com'),(86,0,'Jonathan','\0',NULL,'\0','null','\0','jmhall365@gmail.com','\0','$2a$10$gZQf6VzX5E8buJiiOHeZh.uGipga4RGOFgB7fW3F2oS8wukNPlpKa','Hall',NULL,'',NULL,'jmhall365@gmail.com'),(87,0,'Darrin','\0',NULL,'\0','google','','djmaupin27@gmail.com','\0','$2a$10$dZxG4oi0kgvFs6vDZXJX.e0qluxO7YlxqKRbwRxDT3ULKexnQ5O/6','Maupin',NULL,'',NULL,'djmaupin27@gmail.com'),(88,0,'Joshua','\0',NULL,'\0','google','','susurrus03@gmail.com','\0','$2a$10$VGkoDotqsidEeqYfOPLXxuAhZEXj5iMCmlztSlgJuZoZ6LmImbYk.','Champion',NULL,'',NULL,'susurrus03@gmail.com'),(89,0,'Nasir','\0',NULL,'\0','null','\0','nuddin368@gmail.com','\0','$2a$10$cXPrWJtGK0YZmgGZL.zcu.xsendFJdAaykXIfufZhiiw8yPEG2q/K','Uddin',NULL,'',NULL,'nuddin368@gmail.com'),(90,0,'Scott','\0',NULL,'\0','null','\0','imwiser@gmail.com','\0','$2a$10$APNjYEuVbfaSTGgk.TzLueEq5MrmnnVrMcO69wlgGKJV/J2BzDFUy','Wiser',NULL,'',NULL,'imwiser@gmail.com'),(91,0,'Kyle','\0',NULL,'\0','null','\0','kylej.re@gmail.com','\0','$2a$10$w9.op4F.Nw5PpXhXf0SjMuAG31qRhYv/lZXVZXm5cgtu0cOyzXkoO','Johnson',NULL,'',NULL,'kylej.re@gmail.com'),(92,0,'Lauren','\0',NULL,'\0','null','\0','laurenhoward01@gmail.com','\0','$2a$10$29Z/v/qEjMJsfKD4OB2rE.EKCRI19Jl2.OksSfsDcrZc5I3QtEI..','Coombs',NULL,'',NULL,'laurenhoward01@gmail.com'),(93,0,'Russell','\0',NULL,'\0','google','\0','russellcragun@gmail.com','\0','$2a$10$Rjk9ogJbVEabA93At29qtuwtSXcar.EujECGLR.txTjL0dcTaMcPu','Cragun',NULL,'',NULL,'russellcragun@gmail.com'),(94,0,'Adrian','\0',NULL,'\0','null','\0','atylerjuchau@gmail.com','\0','$2a$10$hqUELDXhswhgptJh6qPSYeWimjmpVaL6tJbel6FgNZ.U6bVLMRVAi','Juchau',NULL,'',NULL,'atylerjuchau@gmail.com'),(95,0,'Tia','\0',NULL,'\0','google','\0','tiakwest@gmail.com','\0','$2a$10$UxAHRUOOQIgv4ndSvzpmNO3oRn9uWYJw2ahb9IT6ySIc8dBVIB4uy','West',NULL,'',NULL,'tiakwest@gmail.com'),(96,0,'JENNIFER','\0',NULL,'\0','null','\0','jiggyjaydub@hotmail.com','\0','$2a$10$xzp/EcxTLVJNPQakJXYNKe198OPgm.5Xh68lrMg5/gkak9NJFB02i','SMITH',NULL,'',NULL,'jiggyjaydub@hotmail.com'),(97,0,'Lindsay','\0',NULL,'\0','null','\0','angeleyes14l@hotmail.com','\0','$2a$10$HexlM3XDz4DdcWw86uVU9.Md9xc/iER0fYGE0OvGKUw8HKU3is8Fu','Lammi',NULL,'',NULL,'angeleyes14l@hotmail.com'),(98,0,'Stephanie','\0',NULL,'\0','null','\0','steph.west74@gmail.com','\0','$2a$10$F0oyj7XuJiecu5KdW6ZG3.PLqK0ZfVNSC8Xo8pV4yDdYfkKTm5DaS','west',NULL,'',NULL,'steph.west74@gmail.com'),(99,0,'Thomas','\0',NULL,'\0','google','\0','sloansterii@gmail.com','\0','$2a$10$xhxu.qdlu7zxfoTcCPPBLOVNmbezW4dwlFTWJGWSj4aDc7zzg7DbO','Sloan',NULL,'',NULL,'sloansterii@gmail.com'),(100,0,'Todd','\0',NULL,'\0','null','\0','tdh134@yahoo.com','\0','$2a$10$mf1ylLU/JZ8TIdVZKsOPM.XV1F4uMf2NG1u3Sb6jXgqRhnolCuVUe','Horne',NULL,'',NULL,'tdh134@yahoo.com'),(101,0,'Nicholas','\0',NULL,'\0','null','\0','nicholasstoddard@gmail.com','\0','$2a$10$3XBVaS4kZsYsuvDxwzueTOMCxKUegupJUaanDK/L10/kPfhb/VCji','Stoddard',NULL,'',NULL,'nicholasstoddard@gmail.com'),(102,0,'Brad','\0',NULL,'\0','google','\0','bradk2376@gmail.com','\0','$2a$10$1xQrMeHSRY2LLL4S01SyOOBPf7k3AJcAWgW.ZrPTpjYzCa93TzrJK','Kitchen',NULL,'',NULL,'bradk2376@gmail.com'),(103,0,'Quentin','\0',NULL,'\0','null','\0','qhalldev@gmail.com','\0','$2a$10$97E1k9YW69Y2aNluoBI/KuHjvIO2jWzHlzHlnwEATFmXHH5MRULvu','hall',NULL,'',NULL,'qhalldev@gmail.com'),(104,0,'Tammy','\0',NULL,'\0','null','\0','tlow0520@yahoo.com','\0','$2a$10$22bO72I/U.hCeMiDyDylNuNuPdGL4Pv0Cr4d9Pl5YqAK7gVSTg1R2','Low',NULL,'',NULL,'tlow0520@yahoo.com'),(105,0,'Cari','\0',NULL,'\0','google','\0','carirobi88@gmail.com','\0','$2a$10$/c6VXQskmKxErc7PYY8oD.nFoPCbT0dnX4VvaiFZFlGKvoT69vtaq','Robison',NULL,'',NULL,'carirobi88@gmail.com'),(106,0,'Robert','\0',NULL,'\0','null','','bobshanerls@gmail.com','\0','$2a$10$c7fSZ3xSNYnaVVVcc4/k2u3Ww8NhsuQRbneg2KFDmZjc1J4HCkPW2','Shane',NULL,'',NULL,'bobshanerls@gmail.com'),(107,0,'Shelli','\0',NULL,'\0','google','','shellishane3@gmail.com','\0','$2a$10$BzJOxRa6R9QfDrcL9S///umjzKsbpQQV6WoI7QfGZ2VCO83gJpwhu','Shane',NULL,'',NULL,'shellishane3@gmail.com'),(108,0,'Rebecca','\0',NULL,'\0','null','\0','rhorne@byulaw.net','\0','$2a$10$i398xUlCDd9MZEmmaS2KOuhohBv8790zIqTM88fgTbc/XzGeF7RUi','Horne',NULL,'',NULL,'rhorne@byulaw.net'),(109,0,'Chrisitne','\0',NULL,'\0','null','\0','christinesimssmith@gmail.com','\0','$2a$10$79.5wAz8kAZgBCjPmJ9ptOaKn4yIehE5M3T/1o1zXfAsTVO5/MJ2a','Smith',NULL,'',NULL,'christinesimssmith@gmail.com'),(110,0,'Haley','\0',NULL,'\0','null','\0','hrevans3@gmail.com','\0','$2a$10$NXtrBnUdFeo0IUwtgfjSverVa52SifSxnpjMowJQg5WTEheMimyU6','Evans',NULL,'',NULL,'hrevans3@gmail.com'),(111,0,'Melanie','\0',NULL,'\0','google','\0','melaniekenney.alvord@gmail.com','\0','$2a$10$2oI2uk/3hLDyaxkehHc6r.dHvYOlcrLwtm9n4bB.QhtP1Tp2YjBRy','Alvord',NULL,'',NULL,'melaniekenney.alvord@gmail.com'),(112,0,'AndWeb','\0',NULL,'\0','google','\0','andstar0428@gmail.com','\0','$2a$10$Q/PpFOiqPufxK9ATSNyGuOJxoNsb7imew8hh23Uf.iqGTGHy8SRUm','Star',NULL,'',NULL,'andstar0428@gmail.com'),(113,0,'Robert','\0',NULL,'\0','null','\0','bobdawson82@gmail.com','\0','$2a$10$DIXSgVZhhJmTBNlFAYyMGuKdNgJMgyjaXrfdMWqDkS2gdAdn9PB4G','Dawson',NULL,'',NULL,'bobdawson82@gmail.com'),(114,0,'chase','\0',NULL,'\0','null','\0','ccporter085@gmail.com','\0','$2a$10$JQKTr/.U7mUiXSKDKUPrTeWr6abiDFwvoKx4n7OAOtaxtYC92mYXm','porter',NULL,'',NULL,'ccporter085@gmail.com'),(115,0,'Mike','\0',NULL,'\0','null','\0','caudillogroup@gmail.com','\0','$2a$10$/m126r5AKEXVBcD0S/HP0u5c6paor6/4I0ScuIHo3fLGu68OPRlte','Caudillo',NULL,'',NULL,'caudillogroup@gmail.com'),(116,0,'Tester','\0',NULL,'\0','null','\0','android@lawhq.com','\0','$2a$10$nQW8VoQvO2SIjJP1YN00Ue5w26JqCb8ZBKjsZ/xEVpRvQknvefida','Android',NULL,'',NULL,'android@lawhq.com');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_role`
--

DROP TABLE IF EXISTS `person_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_role` (
  `person_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`person_id`,`role_id`),
  KEY `FKs7asxi8amiwjjq1sonlc4rihn` (`role_id`),
  CONSTRAINT `FKhyx1efsls0f00lxs6xd4w2b3j` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKs7asxi8amiwjjq1sonlc4rihn` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_role`
--

LOCK TABLES `person_role` WRITE;
/*!40000 ALTER TABLE `person_role` DISABLE KEYS */;
INSERT INTO `person_role` VALUES (1,2,0),(2,1,0);
/*!40000 ALTER TABLE `person_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone`
--

DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `phone_type` varchar(4) NOT NULL,
  `is_primary` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKey2cq0qc015v33h50nyccogde` (`user_id`),
  CONSTRAINT `FKey2cq0qc015v33h50nyccogde` FOREIGN KEY (`user_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone`
--

LOCK TABLES `phone` WRITE;
/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
INSERT INTO `phone` VALUES (1,0,'6607232894',56,'CELL','\0'),(2,0,'8018975636',57,'CELL','\0'),(3,0,'8015925581',58,'CELL','\0'),(4,0,'8013189685',59,'CELL','\0'),(5,0,'8012000119',60,'CELL','\0'),(6,0,'3853379120',61,'CELL','\0'),(7,0,'6603516270',62,'CELL','\0'),(8,0,'8013618478',63,'CELL','\0'),(9,0,'3852042213',64,'CELL','\0'),(10,0,'2083515779',65,'CELL','\0'),(11,0,'8013764672',66,'CELL','\0'),(12,0,'8017879750',67,'CELL','\0'),(13,0,'8013603071',68,'CELL','\0'),(14,0,'8016694994',69,'CELL','\0'),(15,0,'8017351968',70,'CELL','\0'),(16,0,'7376150273',71,'CELL','\0'),(17,0,'8016984348',72,'CELL','\0'),(18,0,'8017871757',73,'CELL','\0'),(19,0,'8016160062',74,'CELL','\0'),(20,0,'4178493983',75,'CELL','\0'),(21,0,'8016870465',76,'CELL','\0'),(22,0,'8016335037',77,'CELL','\0'),(23,0,'4076837840',78,'CELL','\0'),(24,0,'8056601854',79,'CELL','\0'),(25,0,'5714473642',80,'CELL','\0'),(26,0,'9259970918',81,'CELL','\0'),(27,0,'3852512065',82,'CELL','\0'),(28,0,'6615375038',83,'CELL','\0'),(29,0,'8016457912',84,'CELL','\0'),(30,0,'6505552345',85,'CELL','\0'),(31,0,'8014004524',86,'CELL','\0'),(32,0,'7025330904',87,'CELL','\0'),(33,0,'2024920336',88,'CELL','\0'),(34,0,'5185435786',89,'CELL','\0'),(35,0,'8013917559',90,'CELL','\0'),(36,0,'8013722449',91,'CELL','\0'),(37,0,'8013805806',92,'CELL','\0'),(38,0,'8018912857',93,'CELL','\0'),(39,0,'8016412622',94,'CELL','\0'),(40,0,'6603510170',95,'CELL','\0'),(41,0,'8017556069',96,'CELL','\0'),(42,0,'8016240636',97,'CELL','\0'),(43,0,'4172985115',98,'CELL','\0'),(44,0,'6196189658',99,'CELL','\0'),(45,0,'8016341834',100,'CELL','\0'),(46,0,'8017124970',101,'CELL','\0'),(47,0,'8018650853',102,'CELL','\0'),(48,0,'2343390027',103,'CELL','\0'),(49,0,'8015802548',104,'CELL','\0'),(50,0,'9498788975',105,'CELL','\0'),(51,0,'6602876363',106,'CELL','\0'),(52,0,'8162045154',107,'CELL','\0'),(53,0,'8018347770',108,'CELL','\0'),(54,0,'8016787886',109,'CELL','\0'),(55,0,'7578978979',110,'CELL','\0'),(56,0,'8016576576',111,'CELL','\0'),(57,0,'6505551234',112,'CELL','\0'),(58,0,'4254428008',113,'CELL','\0'),(59,0,'8017176151',114,'CELL','\0'),(60,0,'8019201712',115,'CELL','\0'),(61,0,'6505554567',116,'CELL','\0');
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,0,'ROLE_USER'),(2,0,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stat`
--

DROP TABLE IF EXISTS `stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `code` int(11) NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `month` smallint(6) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `day` smallint(6) NOT NULL,
  `wk_of_yr` smallint(6) NOT NULL,
  `hour` smallint(6) NOT NULL,
  `year` bigint(20) NOT NULL,
  `uri` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stat_month_idx` (`month`),
  KEY `stat_day_idx` (`day`),
  KEY `stat_wkOfYr_idx` (`wk_of_yr`),
  KEY `stat_hour_idx` (`hour`),
  KEY `stat_year_idx` (`year`),
  KEY `FKexabl68jcxy4fi9m8v4ljn92f` (`user_id`),
  CONSTRAINT `FKexabl68jcxy4fi9m8v4ljn92f` FOREIGN KEY (`user_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stat`
--

LOCK TABLES `stat` WRITE;
/*!40000 ALTER TABLE `stat` DISABLE KEYS */;
/*!40000 ALTER TABLE `stat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `code` varchar(2) NOT NULL,
  `name` varchar(255) NOT NULL,
  `abbrev` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (1,0,'AL','Alabama','Ala.'),(2,0,'AK','Alaska','Alaska'),(3,0,'AZ','Arizona','Ariz.'),(4,0,'AR','Arkansas','Ark.'),(5,0,'CA','California','Calif.'),(6,0,'CO','Colorado','Colo.'),(7,0,'CT','Connecticut','Conn.'),(8,0,'DE','Delaware','Del.'),(9,0,'DC','District of Columbia','D.C.'),(10,0,'FL','Florida','Fla.'),(11,0,'GA','Georgia','Ga.'),(12,0,'HI','Hawaii','Hawaii'),(13,0,'ID','Idaho','Idaho'),(14,0,'IL','Illinois','Ill.'),(15,0,'IN','Indiana','Ind.'),(16,0,'IA','Iowa','Iowa'),(17,0,'KS','Kansas','Kans.'),(18,0,'KY','Kentucky','Ky.'),(19,0,'LA','Louisiana','La.'),(20,0,'ME','Maine','Maine'),(21,0,'MD','Maryland','Md.'),(22,0,'MA','Massachusetts','Mass.'),(23,0,'MI','Michigan','Mich.'),(24,0,'MN','Minnesota','Minn.'),(25,0,'MS','Mississippi','Miss.'),(26,0,'MO','Missouri','Mo.'),(27,0,'MT','Montana','Mont.'),(28,0,'NE','Nebraska','Nebr.'),(29,0,'NV','Nevada','Nev.'),(30,0,'NH','New Hampshire','N.H.'),(31,0,'NJ','New Jersey','N.J.'),(32,0,'NM','New Mexico','N.M.'),(33,0,'NY','New York','N.Y.'),(34,0,'NC','North Carolina','N.C.'),(35,0,'ND','North Dakota','N.D.'),(36,0,'OH','Ohio','Ohio'),(37,0,'OK','Oklahoma','Okla.'),(38,0,'OR','Oregon','Ore.'),(39,0,'PA','Pennsylvania','Pa.'),(40,0,'RI','Rhode Island','R.I.'),(41,0,'SC','South Carolina','S.C.'),(42,0,'SD','South Dakota','S.D.'),(43,0,'TN','Tennessee','Tenn.'),(44,0,'TX','Texas','Tex.'),(45,0,'UT','Utah','Utah'),(46,0,'VT','Vermont','Vt.'),(47,0,'VA','Virginia','Va.'),(48,0,'WA','Washington','Wash.'),(49,0,'WV','West Virginia','W.Va.'),(50,0,'WI','Wisconsin','Wis.'),(51,0,'WY','Wyoming','Wyo.'),(52,0,'AL','Alabama','Ala.'),(53,0,'AK','Alaska','Alaska'),(54,0,'AZ','Arizona','Ariz.'),(55,0,'AR','Arkansas','Ark.'),(56,0,'CA','California','Calif.'),(57,0,'CO','Colorado','Colo.'),(58,0,'CT','Connecticut','Conn.'),(59,0,'DE','Delaware','Del.'),(60,0,'DC','District of Columbia','D.C.'),(61,0,'FL','Florida','Fla.'),(62,0,'GA','Georgia','Ga.'),(63,0,'HI','Hawaii','Hawaii'),(64,0,'ID','Idaho','Idaho'),(65,0,'IL','Illinois','Ill.'),(66,0,'IN','Indiana','Ind.'),(67,0,'IA','Iowa','Iowa'),(68,0,'KS','Kansas','Kans.'),(69,0,'KY','Kentucky','Ky.'),(70,0,'LA','Louisiana','La.'),(71,0,'ME','Maine','Maine'),(72,0,'MD','Maryland','Md.'),(73,0,'MA','Massachusetts','Mass.'),(74,0,'MI','Michigan','Mich.'),(75,0,'MN','Minnesota','Minn.'),(76,0,'MS','Mississippi','Miss.'),(77,0,'MO','Missouri','Mo.'),(78,0,'MT','Montana','Mont.'),(79,0,'NE','Nebraska','Nebr.'),(80,0,'NV','Nevada','Nev.'),(81,0,'NH','New Hampshire','N.H.'),(82,0,'NJ','New Jersey','N.J.'),(83,0,'NM','New Mexico','N.M.'),(84,0,'NY','New York','N.Y.'),(85,0,'NC','North Carolina','N.C.'),(86,0,'ND','North Dakota','N.D.'),(87,0,'OH','Ohio','Ohio'),(88,0,'OK','Oklahoma','Okla.'),(89,0,'OR','Oregon','Ore.'),(90,0,'PA','Pennsylvania','Pa.'),(91,0,'RI','Rhode Island','R.I.'),(92,0,'SC','South Carolina','S.C.'),(93,0,'SD','South Dakota','S.D.'),(94,0,'TN','Tennessee','Tenn.'),(95,0,'TX','Texas','Tex.'),(96,0,'UT','Utah','Utah'),(97,0,'VT','Vermont','Vt.'),(98,0,'VA','Virginia','Va.'),(99,0,'WA','Washington','Wash.'),(100,0,'WV','West Virginia','W.Va.'),(101,0,'WI','Wisconsin','Wis.'),(102,0,'WY','Wyoming','Wyo.');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `stat_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (7,0,'test1'),(8,0,'test2'),(9,0,'test3'),(10,0,'test4'),(11,0,'test5'),(19,0,'test1'),(20,0,'test2'),(21,0,'test3'),(22,0,'test4'),(23,0,'test5'),(31,0,'test1'),(32,0,'test2'),(33,0,'test3'),(34,0,'test4'),(35,0,'test5'),(43,0,'test1'),(44,0,'test2'),(45,0,'test3'),(46,0,'test4'),(47,0,'test5'),(55,0,'test1'),(56,0,'test2'),(57,0,'test3'),(58,0,'test4'),(59,0,'test5'),(67,0,'test1'),(68,0,'test2'),(69,0,'test3'),(70,0,'test4'),(71,0,'test5'),(79,0,'test1'),(80,0,'test2'),(81,0,'test3'),(82,0,'test4'),(83,0,'test5'),(91,0,'test1'),(92,0,'test2'),(93,0,'test3'),(94,0,'test4'),(95,0,'test5');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-08  8:27:16
