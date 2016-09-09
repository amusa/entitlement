-- MySQL dump 10.13  Distrib 5.7.12, for Linux (x86_64)
--
-- Host: localhost    Database: wildfly
-- ------------------------------------------------------
-- Server version	5.7.12-0ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `username` varchar(255) NOT NULL,
  `passwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('admin','o1xfY5Fv/0E2l1THoBzEqC6ePl8eBWKHkbX1dwQ11rA='),('user','o1xfY5Fv/0E2l1THoBzEqC6ePl8eBWKHkbX1dwQ11rA=');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserRole`
--

DROP TABLE IF EXISTS `UserRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRole` (
  `username` varchar(255) DEFAULT NULL,
  `role` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRole`
--

LOCK TABLES `UserRole` WRITE;
/*!40000 ALTER TABLE `UserRole` DISABLE KEYS */;
INSERT INTO `UserRole` VALUES ('admin','ADMIN'),('user','USER');
/*!40000 ALTER TABLE `UserRole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-06 21:45:37


-- MySQL dump 10.13  Distrib 5.7.12, for Linux (x86_64)
--
-- Host: localhost    Database: entitlement
-- ------------------------------------------------------
-- Server version	5.7.12-0ubuntu1.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `COMPANY`
--

DROP TABLE IF EXISTS `COMPANY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPANY` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPANY`
--

LOCK TABLES `COMPANY` WRITE;
/*!40000 ALTER TABLE `COMPANY` DISABLE KEYS */;
INSERT INTO `COMPANY` VALUES (1,'SPDC'),(2,'Cheveron'),(3,'Mobil');
/*!40000 ALTER TABLE `COMPANY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CONTRACT`
--

DROP TABLE IF EXISTS `CONTRACT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CONTRACT` (
  `DTYPE` varchar(31) NOT NULL,
  `CRUDETYPECODE` varchar(255) NOT NULL,
  `FISCALARRANGEMENTID` bigint(20) NOT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `sharedOilRatio` double DEFAULT NULL,
  `terminalPeriod` double DEFAULT NULL,
  `terminalSharedOil` double DEFAULT NULL,
  PRIMARY KEY (`CRUDETYPECODE`,`FISCALARRANGEMENTID`),
  KEY `FKaju99v3iv71e1f51fgebp6uge` (`FISCALARRANGEMENTID`),
  CONSTRAINT `FKaju99v3iv71e1f51fgebp6uge` FOREIGN KEY (`FISCALARRANGEMENTID`) REFERENCES `FISCAL_ARRANGEMENT` (`ID`),
  CONSTRAINT `FKeeyg8234rbcthtby3xfamaj0m` FOREIGN KEY (`CRUDETYPECODE`) REFERENCES `CRUDE_TYPE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CONTRACT`
--

LOCK TABLES `CONTRACT` WRITE;
/*!40000 ALTER TABLE `CONTRACT` DISABLE KEYS */;
INSERT INTO `CONTRACT` VALUES ('REG','BL',1,NULL,NULL,NULL,NULL),('CA','YH',1,NULL,35,5,NULL);
/*!40000 ALTER TABLE `CONTRACT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRUDE_TYPE`
--

DROP TABLE IF EXISTS `CRUDE_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRUDE_TYPE` (
  `CODE` varchar(255) NOT NULL,
  `CRUDE_TYPE` varchar(255) NOT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRUDE_TYPE`
--

LOCK TABLES `CRUDE_TYPE` WRITE;
/*!40000 ALTER TABLE `CRUDE_TYPE` DISABLE KEYS */;
INSERT INTO `CRUDE_TYPE` VALUES ('BL','Bonny Light'),('FB','Forcados Blend'),('YH','Yoho');
/*!40000 ALTER TABLE `CRUDE_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EQUITY_TYPE`
--

DROP TABLE IF EXISTS `EQUITY_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EQUITY_TYPE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `ownEquity` double NOT NULL,
  `partnerEquity` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EQUITY_TYPE`
--

LOCK TABLES `EQUITY_TYPE` WRITE;
/*!40000 ALTER TABLE `EQUITY_TYPE` DISABLE KEYS */;
INSERT INTO `EQUITY_TYPE` VALUES (2,'60/40',60,40),(3,'55/45',55,45),(4,'50/50',50,50);
/*!40000 ALTER TABLE `EQUITY_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FISCAL_ARRANGEMENT`
--

DROP TABLE IF EXISTS `FISCAL_ARRANGEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FISCAL_ARRANGEMENT` (
  `DTYPE` varchar(31) NOT NULL,
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(255) NOT NULL,
  `OPERATOR_ID` int(11) NOT NULL,
  `equityType_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKboeh0f97kttlf0ke33xlvunnn` (`OPERATOR_ID`),
  KEY `FKo457frf88fcl4a7knd5qyct8` (`equityType_id`),
  CONSTRAINT `FKboeh0f97kttlf0ke33xlvunnn` FOREIGN KEY (`OPERATOR_ID`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FKo457frf88fcl4a7knd5qyct8` FOREIGN KEY (`equityType_id`) REFERENCES `EQUITY_TYPE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FISCAL_ARRANGEMENT`
--

LOCK TABLES `FISCAL_ARRANGEMENT` WRITE;
/*!40000 ALTER TABLE `FISCAL_ARRANGEMENT` DISABLE KEYS */;
INSERT INTO `FISCAL_ARRANGEMENT` VALUES ('JV',1,'NIG/SPDC JV',1,3),('JV',2,'NIG/MOBIL JV',3,2);
/*!40000 ALTER TABLE `FISCAL_ARRANGEMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FORECAST`
--

DROP TABLE IF EXISTS `FORECAST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FORECAST` (
  `FTYPE` varchar(31) NOT NULL,
  `PERIOD_MONTH` int(11) NOT NULL,
  `PERIOD_YEAR` int(11) NOT NULL,
  `AVAILABILITY` double DEFAULT NULL,
  `CARGOES` int(11) DEFAULT NULL,
  `CLOSING_STOCK` double DEFAULT NULL,
  `GROSS_PRODUCTION` double DEFAULT NULL,
  `LIFTING` double DEFAULT NULL,
  `OPENING_STOCK` double NOT NULL,
  `OWN_SHARE_ENTITLEMENT` double NOT NULL,
  `PARTNER_AVAILABILITY` double DEFAULT NULL,
  `PARTNER_CARGOES` int(11) DEFAULT NULL,
  `PARTNER_CLOSING_STOCK` double DEFAULT NULL,
  `PARTNER_LIFTING` double DEFAULT NULL,
  `PARTNER_OPENING_STOCK` double DEFAULT NULL,
  `PARTNER_SHARE_ENTITLEMENT` double DEFAULT NULL,
  `PRODUCTION_VOLUME` double NOT NULL,
  `capitalCarryCostAmortized` double DEFAULT NULL,
  `capitalCarryCostAmortizedCum` double DEFAULT NULL,
  `carryOil` double DEFAULT NULL,
  `carryOilCum` double DEFAULT NULL,
  `carryOilReceived` double DEFAULT NULL,
  `carryOilReceivedCum` double DEFAULT NULL,
  `carryTaxExpenditure` double DEFAULT NULL,
  `carryTaxExpenditureCum` double DEFAULT NULL,
  `carryTaxRelief` double DEFAULT NULL,
  `carryTaxReliefCum` double DEFAULT NULL,
  `guaranteedNotionalMargin` double DEFAULT NULL,
  `intangibleCost` double DEFAULT NULL,
  `residualCarryExpenditure` double DEFAULT NULL,
  `residualCarryExpenditureCum` double DEFAULT NULL,
  `sharedOil` double DEFAULT NULL,
  `sharedOilCum` double DEFAULT NULL,
  `tangibleCost` double DEFAULT NULL,
  `CRUDETYPE_CODE` varchar(255) NOT NULL,
  `FISCALARRANGEMENT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`CRUDETYPE_CODE`,`FISCALARRANGEMENT_ID`,`PERIOD_MONTH`,`PERIOD_YEAR`),
  CONSTRAINT `FKl5v678fjttg53t4400pfivpg3` FOREIGN KEY (`CRUDETYPE_CODE`, `FISCALARRANGEMENT_ID`) REFERENCES `CONTRACT` (`CRUDETYPECODE`, `FISCALARRANGEMENTID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FORECAST`
--

LOCK TABLES `FORECAST` WRITE;
/*!40000 ALTER TABLE `FORECAST` DISABLE KEYS */;
INSERT INTO `FORECAST` VALUES ('REG',1,2016,588225,0,588225,1069500,0,0,588225,481275,0,481275,0,0,481275,34500,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BL',1),('CA',1,2016,505764.0321557601,0,505764.0321557601,1416049,0,0,778826.9500000001,910284.96784424,0,910284.96784424,0,0,637222.05,45679,43707,43707,728.4389911383785,728.4389911383785,0,0,48277,48277,41035.45,41035.45,3.6675,34567,728.4389911383785,728.4389911383785,272334.4788531016,272334.4788531016,45700,'YH',1);
/*!40000 ALTER TABLE `FORECAST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FORECAST_ENTITLEMENT`
--

DROP TABLE IF EXISTS `FORECAST_ENTITLEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FORECAST_ENTITLEMENT` (
  `PERIOD_MONTH` int(11) NOT NULL,
  `PERIOD_YEAR` int(11) NOT NULL,
  `availability` double DEFAULT NULL,
  `closingStock` double DEFAULT NULL,
  `equityEntitlement` double DEFAULT NULL,
  `liftableCargos` int(11) DEFAULT NULL,
  `liftableVolume` double DEFAULT NULL,
  `openingStock` double DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `CRUDETYPE_CODE` varchar(255) NOT NULL,
  `FISCALARRANGEMENT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`company_id`,`CRUDETYPE_CODE`,`FISCALARRANGEMENT_ID`,`PERIOD_MONTH`,`PERIOD_YEAR`),
  UNIQUE KEY `UK_4uedf915ftu0ixtkgit66flke` (`company_id`),
  UNIQUE KEY `UK_jef1evbdikw4wvoddsxtv1qa1` (`CRUDETYPE_CODE`,`FISCALARRANGEMENT_ID`),
  KEY `FK6x9ctq0jl873crt0o0ir8okk0` (`CRUDETYPE_CODE`,`FISCALARRANGEMENT_ID`,`PERIOD_MONTH`,`PERIOD_YEAR`),
  CONSTRAINT `FK6x9ctq0jl873crt0o0ir8okk0` FOREIGN KEY (`CRUDETYPE_CODE`, `FISCALARRANGEMENT_ID`, `PERIOD_MONTH`, `PERIOD_YEAR`) REFERENCES `FORECAST` (`CRUDETYPE_CODE`, `FISCALARRANGEMENT_ID`, `PERIOD_MONTH`, `PERIOD_YEAR`),
  CONSTRAINT `FKmblqphao0nxf18hsjxslxtvnd` FOREIGN KEY (`company_id`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FKrnb7bvof44j1umt2430dojsg1` FOREIGN KEY (`CRUDETYPE_CODE`, `FISCALARRANGEMENT_ID`) REFERENCES `CONTRACT` (`CRUDETYPECODE`, `FISCALARRANGEMENTID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FORECAST_ENTITLEMENT`
--

LOCK TABLES `FORECAST_ENTITLEMENT` WRITE;
/*!40000 ALTER TABLE `FORECAST_ENTITLEMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `FORECAST_ENTITLEMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIFTING`
--

DROP TABLE IF EXISTS `LIFTING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LIFTING` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `periodMonth` int(11) NOT NULL,
  `periodYear` int(11) NOT NULL,
  `quantity` double DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `contract_CRUDETYPECODE` varchar(255) DEFAULT NULL,
  `contract_FISCALARRANGEMENTID` bigint(20) DEFAULT NULL,
  `terminal_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8m1m9mbmep4qe35ytqw49cix2` (`company_id`),
  KEY `FKl0xhpl6ejb9c7ewqofk32udai` (`contract_CRUDETYPECODE`,`contract_FISCALARRANGEMENTID`),
  KEY `FKll691hyps8xgtkwf5xgr9kiki` (`terminal_code`),
  CONSTRAINT `FK8m1m9mbmep4qe35ytqw49cix2` FOREIGN KEY (`company_id`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FKl0xhpl6ejb9c7ewqofk32udai` FOREIGN KEY (`contract_CRUDETYPECODE`, `contract_FISCALARRANGEMENTID`) REFERENCES `CONTRACT` (`CRUDETYPECODE`, `FISCALARRANGEMENTID`),
  CONSTRAINT `FKll691hyps8xgtkwf5xgr9kiki` FOREIGN KEY (`terminal_code`) REFERENCES `TERMINAL` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIFTING`
--

LOCK TABLES `LIFTING` WRITE;
/*!40000 ALTER TABLE `LIFTING` DISABLE KEYS */;
/*!40000 ALTER TABLE `LIFTING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRICE`
--

DROP TABLE IF EXISTS `PRICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRICE` (
  `periodMonth` int(11) NOT NULL,
  `periodYear` int(11) NOT NULL,
  `realizablePrice` double NOT NULL,
  PRIMARY KEY (`periodMonth`,`periodYear`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRICE`
--

LOCK TABLES `PRICE` WRITE;
/*!40000 ALTER TABLE `PRICE` DISABLE KEYS */;
INSERT INTO `PRICE` VALUES (1,2016,30);
/*!40000 ALTER TABLE `PRICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCTION`
--

DROP TABLE IF EXISTS `PRODUCTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCTION` (
  `PTYPE` varchar(31) NOT NULL,
  `crudeTypeCode` varchar(255) NOT NULL,
  `fiscalArrangementId` bigint(20) NOT NULL,
  `periodMonth` int(11) NOT NULL,
  `periodYear` int(11) NOT NULL,
  `availability` double DEFAULT NULL,
  `cargos` int(11) DEFAULT NULL,
  `closingStock` double DEFAULT NULL,
  `grossProduction` double DEFAULT NULL,
  `lifting` double DEFAULT NULL,
  `openingStock` double NOT NULL,
  `overlift` double DEFAULT NULL,
  `ownShareEntitlement` double NOT NULL,
  `partnerAvailability` double DEFAULT NULL,
  `partnerCargos` int(11) DEFAULT NULL,
  `partnerClosingStock` double DEFAULT NULL,
  `partnerLifting` double DEFAULT NULL,
  `partnerOpeningStock` double DEFAULT NULL,
  `partnerOverlift` double DEFAULT NULL,
  `partnerShareEntitlement` double DEFAULT NULL,
  `stockAdjustment` double DEFAULT NULL,
  `capitalCarryCostAmortized` double DEFAULT NULL,
  `capitalCarryCostAmortizedCum` double DEFAULT NULL,
  `carryOil` double DEFAULT NULL,
  `carryOilCum` double DEFAULT NULL,
  `carryOilReceived` double DEFAULT NULL,
  `carryOilReceivedCum` double DEFAULT NULL,
  `carryTaxExpenditure` double DEFAULT NULL,
  `carryTaxExpenditureCum` double DEFAULT NULL,
  `carryTaxRelief` double DEFAULT NULL,
  `carryTaxReliefCum` double DEFAULT NULL,
  `guaranteedNotionalMargin` double DEFAULT NULL,
  `intangibleCost` double DEFAULT NULL,
  `residualCarryExpenditure` double DEFAULT NULL,
  `residualCarryExpenditureCum` double DEFAULT NULL,
  `sharedOil` double DEFAULT NULL,
  `sharedOilCum` double DEFAULT NULL,
  `tangibleCost` double DEFAULT NULL,
  PRIMARY KEY (`crudeTypeCode`,`fiscalArrangementId`,`periodMonth`,`periodYear`),
  CONSTRAINT `FK8reo2d71gvqc6y4wxegp40p0a` FOREIGN KEY (`crudeTypeCode`, `fiscalArrangementId`) REFERENCES `CONTRACT` (`CRUDETYPECODE`, `FISCALARRANGEMENTID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCTION`
--

LOCK TABLES `PRODUCTION` WRITE;
/*!40000 ALTER TABLE `PRODUCTION` DISABLE KEYS */;
INSERT INTO `PRODUCTION` VALUES ('REG','BL',1,1,2016,30074,0,30074,54680,0,0,NULL,30074,24606,0,24606,0,0,NULL,24606,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `PRODUCTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCTION_EQUITY`
--

DROP TABLE IF EXISTS `PRODUCTION_EQUITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCTION_EQUITY` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `availability` double DEFAULT NULL,
  `cargos` int(11) DEFAULT NULL,
  `closingStock` double DEFAULT NULL,
  `entitlement` double NOT NULL,
  `lifting` double DEFAULT NULL,
  `openingStock` double NOT NULL,
  `production_crudeTypeCode` varchar(255) DEFAULT NULL,
  `production_fiscalArrangementId` bigint(20) DEFAULT NULL,
  `production_periodMonth` int(11) DEFAULT NULL,
  `production_periodYear` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm5hx31acnl34lqq44w2jpk083` (`production_crudeTypeCode`,`production_fiscalArrangementId`,`production_periodMonth`,`production_periodYear`),
  CONSTRAINT `FKm5hx31acnl34lqq44w2jpk083` FOREIGN KEY (`production_crudeTypeCode`, `production_fiscalArrangementId`, `production_periodMonth`, `production_periodYear`) REFERENCES `PRODUCTION` (`crudeTypeCode`, `fiscalArrangementId`, `periodMonth`, `periodYear`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCTION_EQUITY`
--

LOCK TABLES `PRODUCTION_EQUITY` WRITE;
/*!40000 ALTER TABLE `PRODUCTION_EQUITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `PRODUCTION_EQUITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TERMINAL`
--

DROP TABLE IF EXISTS `TERMINAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TERMINAL` (
  `code` varchar(255) NOT NULL,
  `capacity` double DEFAULT NULL,
  `deadStockVolume` double DEFAULT NULL,
  `lineFillVolume` double DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `openingBalance` double DEFAULT NULL,
  `stockVolume` double DEFAULT NULL,
  `crudeType_CODE` varchar(255) NOT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `FKbgket7nqiql1xmfi5iyklph1y` (`crudeType_CODE`),
  KEY `FK6d1v90s6cqc1uomjqkhu6yp50` (`operator_id`),
  CONSTRAINT `FK6d1v90s6cqc1uomjqkhu6yp50` FOREIGN KEY (`operator_id`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FKbgket7nqiql1xmfi5iyklph1y` FOREIGN KEY (`crudeType_CODE`) REFERENCES `CRUDE_TYPE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TERMINAL`
--

LOCK TABLES `TERMINAL` WRITE;
/*!40000 ALTER TABLE `TERMINAL` DISABLE KEYS */;
/*!40000 ALTER TABLE `TERMINAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TERMINAL_BLEND`
--

DROP TABLE IF EXISTS `TERMINAL_BLEND`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TERMINAL_BLEND` (
  `periodMonth` int(11) NOT NULL,
  `periodYear` int(11) NOT NULL,
  `volume` double NOT NULL,
  `contract_CRUDETYPECODE` varchar(255) NOT NULL,
  `contract_FISCALARRANGEMENTID` bigint(20) NOT NULL,
  PRIMARY KEY (`contract_CRUDETYPECODE`,`contract_FISCALARRANGEMENTID`,`periodMonth`,`periodYear`),
  CONSTRAINT `FKonygib1m56cvrhis1f28aws4o` FOREIGN KEY (`contract_CRUDETYPECODE`, `contract_FISCALARRANGEMENTID`) REFERENCES `CONTRACT` (`CRUDETYPECODE`, `FISCALARRANGEMENTID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TERMINAL_BLEND`
--

LOCK TABLES `TERMINAL_BLEND` WRITE;
/*!40000 ALTER TABLE `TERMINAL_BLEND` DISABLE KEYS */;
/*!40000 ALTER TABLE `TERMINAL_BLEND` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-08 14:53:49