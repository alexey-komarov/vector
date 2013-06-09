-- MySQL dump 10.13  Distrib 5.1.44, for Win32 (ia32)
--
-- Host: localhost    Database: etalon
-- ------------------------------------------------------
-- Server version	5.1.44-community

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
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colors` (
  `ID_Color` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Color`),
  UNIQUE KEY `ID_Color` (`ID_Color`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_groups`
--

DROP TABLE IF EXISTS `customer_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_groups` (
  `ID_Customer_Group` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(512) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Customer_Group`),
  UNIQUE KEY `ID_Customer_Group` (`ID_Customer_Group`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_groups`
--

LOCK TABLES `customer_groups` WRITE;
/*!40000 ALTER TABLE `customer_groups` DISABLE KEYS */;
INSERT INTO `customer_groups` VALUES (1,'Конвертор','\0');
/*!40000 ALTER TABLE `customer_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `ID_Customer` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT 'Псевдоним',
  `ID_Customer_Group` int(11) NOT NULL,
  `FullName` varchar(512) DEFAULT NULL,
  `Phone` varchar(512) DEFAULT NULL,
  `Floor` int(11) DEFAULT NULL,
  `Lift` bit(1) DEFAULT b'0',
  `Address` longtext,
  `Contact` varchar(512) DEFAULT NULL,
  `Comment` longtext,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Customer`),
  UNIQUE KEY `ID_Customer` (`ID_Customer`),
  KEY `customers_fk` (`ID_Customer_Group`),
  CONSTRAINT `customers_fk` FOREIGN KEY (`ID_Customer_Group`) REFERENCES `customer_groups` (`ID_Customer_Group`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COMMENT='Зазазчики';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `directories`
--

DROP TABLE IF EXISTS `directories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `directories` (
  `ID_Directory` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET cp1251 DEFAULT NULL,
  `ID_Scheme` varchar(100) CHARACTER SET cp1251 DEFAULT NULL,
  `Is_System` bit(1) DEFAULT b'1',
  `Table_Name` varchar(255) CHARACTER SET cp1251 NOT NULL,
  `Anul` bit(1) DEFAULT b'0',
  `Parameters` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Directory`),
  KEY `FK_directories_ID_Scheme` (`ID_Scheme`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='Справочник справочников';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `directories`
--

LOCK TABLES `directories` WRITE;
/*!40000 ALTER TABLE `directories` DISABLE KEYS */;
INSERT INTO `directories` VALUES (-1,'Роли пользователей','Users_Roles','','users_roles','\0',''),(1,'Справочник справочников','Directories','','Directories','\0','\0'),(2,'Схемы таблиц','XML_Schemes','','xml_schemes','\0','\0'),(3,'Пользователи','Users','','Users','\0',''),(4,'Роли','Roles','','roles','\0',''),(5,'Заказчики','Customers','','customers','\0',''),(6,'Фурнитура','Furniture','','furniture','\0',''),(7,'Мебель','Mebel','','mebel','\0',''),(8,'Сотрудники','Employers','','employers','\0',''),(100,'Единицы измерения','Units','\0','units','\0',''),(101,'Материалы','Materials','\0','materials','\0',''),(102,'Цвета','Colors','\0','colors','\0',''),(103,'Группы фурнитуры','Furniture_Groups','\0','furniture_groups','\0',''),(104,'Группы мебели','Mebel_Groups','\0','mebel_groups','\0',''),(105,'Должности','Professions','\0','professions','\0',''),(106,'Группы заказчиков','Customer_Groups','\0','customer_groups','\0','');
/*!40000 ALTER TABLE `directories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employers`
--

DROP TABLE IF EXISTS `employers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employers` (
  `ID_Employee` int(11) NOT NULL AUTO_INCREMENT,
  `Family` varchar(255) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Patronymic` varchar(255) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT '',
  `ID_Profession` int(11) DEFAULT NULL,
  `ID_User` int(11) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Employee`),
  UNIQUE KEY `ID_Employee` (`ID_Employee`),
  KEY `ID_Profession` (`ID_Profession`),
  KEY `ID_User` (`ID_User`),
  CONSTRAINT `Employers_fk` FOREIGN KEY (`ID_Profession`) REFERENCES `professions` (`ID_Profession`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employers`
--

LOCK TABLES `employers` WRITE;
/*!40000 ALTER TABLE `employers` DISABLE KEYS */;
/*!40000 ALTER TABLE `employers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furniture`
--

DROP TABLE IF EXISTS `furniture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `furniture` (
  `ID_Furniture` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Furniture_Group` int(11) NOT NULL,
  `Art` varchar(64) DEFAULT NULL,
  `Name` varchar(512) DEFAULT NULL,
  `ID_Unit` int(11) NOT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Furniture`),
  UNIQUE KEY `ID_Furniture` (`ID_Furniture`),
  KEY `ID_Furniture_Group` (`ID_Furniture_Group`),
  KEY `ID_Unit` (`ID_Unit`),
  CONSTRAINT `furniture_fk` FOREIGN KEY (`ID_Furniture_Group`) REFERENCES `furniture_groups` (`ID_Furniture_Group`),
  CONSTRAINT `furniture_fk1` FOREIGN KEY (`ID_Unit`) REFERENCES `units` (`ID_Unit`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furniture`
--

LOCK TABLES `furniture` WRITE;
/*!40000 ALTER TABLE `furniture` DISABLE KEYS */;
/*!40000 ALTER TABLE `furniture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furniture_groups`
--

DROP TABLE IF EXISTS `furniture_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `furniture_groups` (
  `ID_Furniture_Group` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(512) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Furniture_Group`),
  UNIQUE KEY `ID_Furniture_Group` (`ID_Furniture_Group`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furniture_groups`
--

LOCK TABLES `furniture_groups` WRITE;
/*!40000 ALTER TABLE `furniture_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `furniture_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `home_orders`
--

DROP TABLE IF EXISTS `home_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `home_orders` (
  `ID_Order` int(11) NOT NULL,
  `ID_Material` int(11) DEFAULT NULL,
  `ID_Color_Base` int(11) DEFAULT NULL,
  `ID_Color_Box` int(11) DEFAULT NULL,
  `ID_Color_Facade` int(11) DEFAULT NULL,
  `ID_Edge` int(11) DEFAULT NULL,
  `ID_Handle` int(11) DEFAULT NULL,
  `ID_Glass` int(11) DEFAULT NULL,
  `ID_Guide` int(11) DEFAULT NULL,
  `Mebel_Info` longtext,
  `Pack_Info` longtext,
  `Client_Info` longtext,
  PRIMARY KEY (`ID_Order`),
  KEY `ID_Material` (`ID_Material`),
  KEY `ID_Color_Base` (`ID_Color_Base`),
  KEY `ID_Color_Box` (`ID_Color_Box`),
  KEY `ID_Color_Facade` (`ID_Color_Facade`),
  KEY `ID_Edge` (`ID_Edge`),
  KEY `ID_Handle` (`ID_Handle`),
  KEY `ID_Glass` (`ID_Glass`),
  KEY `ID_Guide` (`ID_Guide`),
  CONSTRAINT `home_orders_fk` FOREIGN KEY (`ID_Order`) REFERENCES `orders` (`ID_Order`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `home_order_fk` FOREIGN KEY (`ID_Material`) REFERENCES `materials` (`ID_Material`),
  CONSTRAINT `home_order_fk1` FOREIGN KEY (`ID_Color_Base`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `home_order_fk2` FOREIGN KEY (`ID_Color_Box`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `home_order_fk3` FOREIGN KEY (`ID_Color_Facade`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `home_order_fk4` FOREIGN KEY (`ID_Edge`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `home_order_fk5` FOREIGN KEY (`ID_Handle`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `home_order_fk6` FOREIGN KEY (`ID_Glass`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `home_order_fk7` FOREIGN KEY (`ID_Guide`) REFERENCES `furniture` (`ID_Furniture`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `home_orders`
--

LOCK TABLES `home_orders` WRITE;
/*!40000 ALTER TABLE `home_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `home_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `ID_Image` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Order` int(11) NOT NULL,
  `Name` varchar(512) DEFAULT NULL,
  `Image` mediumblob,
  PRIMARY KEY (`ID_Image`),
  UNIQUE KEY `ID_Image` (`ID_Image`),
  KEY `ID_Order` (`ID_Order`),
  CONSTRAINT `images_fk` FOREIGN KEY (`ID_Order`) REFERENCES `orders` (`ID_Order`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kitchen_orders`
--

DROP TABLE IF EXISTS `kitchen_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kitchen_orders` (
  `ID_Order` int(11) NOT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `ID_Box_Material` int(11) DEFAULT NULL,
  `ID_Facade_Material` int(11) DEFAULT NULL,
  `ID_Facade_Glass_Material` int(11) DEFAULT NULL,
  `ID_Color` int(11) DEFAULT NULL,
  `ID_Box_Color` int(11) DEFAULT NULL,
  `ID_Facade_Color` int(11) DEFAULT NULL,
  `ID_Table_Color` int(11) DEFAULT NULL,
  `ID_Table` int(11) DEFAULT NULL,
  `ID_Wall_Panel` int(11) DEFAULT NULL,
  `ID_Plinth` int(11) DEFAULT NULL,
  `ID_Socle` int(11) DEFAULT NULL,
  `ID_Drying` int(11) DEFAULT NULL,
  `ID_Lighting` int(11) DEFAULT NULL,
  `ID_Bottle_Holder` int(11) DEFAULT NULL,
  `ID_Metall` int(11) DEFAULT NULL,
  `ID_Top` int(11) DEFAULT NULL,
  `ID_Handle` int(11) DEFAULT NULL,
  `ID_Glass` int(11) DEFAULT NULL,
  `ID_Guide` int(11) DEFAULT NULL,
  `Notes` longtext,
  `Installation` longtext,
  PRIMARY KEY (`ID_Order`),
  UNIQUE KEY `ID_Order` (`ID_Order`),
  KEY `ID_Box_Material` (`ID_Box_Material`),
  KEY `ID_Facade_Material` (`ID_Facade_Material`),
  KEY `ID_Facade_Glass_Material` (`ID_Facade_Glass_Material`),
  KEY `ID_Color` (`ID_Color`),
  KEY `ID_Box_Color` (`ID_Box_Color`),
  KEY `ID_Facade_Color` (`ID_Facade_Color`),
  KEY `ID_Table_Color` (`ID_Table_Color`),
  KEY `ID_Table` (`ID_Table`),
  KEY `ID_Wall_Panel` (`ID_Wall_Panel`),
  KEY `ID_Plinth` (`ID_Plinth`),
  KEY `ID_Socle` (`ID_Socle`),
  KEY `ID_Drying` (`ID_Drying`),
  KEY `ID_Lighting` (`ID_Lighting`),
  KEY `ID_Bottle_Holder` (`ID_Bottle_Holder`),
  KEY `ID_Metall` (`ID_Metall`),
  KEY `ID_Top` (`ID_Top`),
  KEY `ID_Handle` (`ID_Handle`),
  KEY `ID_Glass` (`ID_Glass`),
  KEY `ID_Guide` (`ID_Guide`),
  CONSTRAINT `kitchen_orders_fk` FOREIGN KEY (`ID_Order`) REFERENCES `orders` (`ID_Order`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `kitchen_orders_fk1` FOREIGN KEY (`ID_Box_Material`) REFERENCES `materials` (`ID_Material`),
  CONSTRAINT `kitchen_orders_fk10` FOREIGN KEY (`ID_Plinth`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk11` FOREIGN KEY (`ID_Socle`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk12` FOREIGN KEY (`ID_Drying`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk13` FOREIGN KEY (`ID_Lighting`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk14` FOREIGN KEY (`ID_Bottle_Holder`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk15` FOREIGN KEY (`ID_Metall`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk16` FOREIGN KEY (`ID_Top`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk17` FOREIGN KEY (`ID_Handle`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk18` FOREIGN KEY (`ID_Glass`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk19` FOREIGN KEY (`ID_Guide`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk2` FOREIGN KEY (`ID_Facade_Material`) REFERENCES `materials` (`ID_Material`),
  CONSTRAINT `kitchen_orders_fk3` FOREIGN KEY (`ID_Color`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `kitchen_orders_fk4` FOREIGN KEY (`ID_Facade_Glass_Material`) REFERENCES `materials` (`ID_Material`),
  CONSTRAINT `kitchen_orders_fk5` FOREIGN KEY (`ID_Box_Color`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `kitchen_orders_fk6` FOREIGN KEY (`ID_Facade_Color`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `kitchen_orders_fk7` FOREIGN KEY (`ID_Table_Color`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `kitchen_orders_fk8` FOREIGN KEY (`ID_Table`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `kitchen_orders_fk9` FOREIGN KEY (`ID_Wall_Panel`) REFERENCES `furniture` (`ID_Furniture`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kitchen_orders`
--

LOCK TABLES `kitchen_orders` WRITE;
/*!40000 ALTER TABLE `kitchen_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `kitchen_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materials`
--

DROP TABLE IF EXISTS `materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `materials` (
  `ID_Material` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(512) DEFAULT NULL,
  `ID_Unit` int(11) NOT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Material`),
  UNIQUE KEY `ID_Material` (`ID_Material`),
  KEY `ID_Unit` (`ID_Unit`),
  CONSTRAINT `materials_fk` FOREIGN KEY (`ID_Unit`) REFERENCES `units` (`ID_Unit`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materials`
--

LOCK TABLES `materials` WRITE;
/*!40000 ALTER TABLE `materials` DISABLE KEYS */;
/*!40000 ALTER TABLE `materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mebel`
--

DROP TABLE IF EXISTS `mebel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mebel` (
  `ID_Mebel` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Mebel_Group` int(11) NOT NULL,
  `Art` varchar(64) DEFAULT NULL,
  `Name` varchar(512) DEFAULT NULL,
  `Square` float(9,3) DEFAULT NULL,
  `Price` float(9,3) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Mebel`),
  UNIQUE KEY `ID_Mebel` (`ID_Mebel`),
  KEY `ID_Mebel_Group` (`ID_Mebel_Group`),
  CONSTRAINT `mebel_fk` FOREIGN KEY (`ID_Mebel_Group`) REFERENCES `mebel_groups` (`ID_Mebel_Group`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mebel`
--

LOCK TABLES `mebel` WRITE;
/*!40000 ALTER TABLE `mebel` DISABLE KEYS */;
/*!40000 ALTER TABLE `mebel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mebel_groups`
--

DROP TABLE IF EXISTS `mebel_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mebel_groups` (
  `ID_Mebel_Group` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(512) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Mebel_Group`),
  UNIQUE KEY `ID_Mebel_Group` (`ID_Mebel_Group`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mebel_groups`
--

LOCK TABLES `mebel_groups` WRITE;
/*!40000 ALTER TABLE `mebel_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `mebel_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_furniture`
--

DROP TABLE IF EXISTS `order_furniture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_furniture` (
  `ID_Order_Furniture` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Order` int(11) DEFAULT NULL,
  `ID_Furniture` int(11) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_Order_Furniture`),
  KEY `ID_Order` (`ID_Order`),
  KEY `ID_Furniture` (`ID_Furniture`),
  CONSTRAINT `order_furniture_fk1` FOREIGN KEY (`ID_Order`) REFERENCES `orders` (`ID_Order`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_furniture_fk2` FOREIGN KEY (`ID_Furniture`) REFERENCES `furniture` (`ID_Furniture`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_furniture`
--

LOCK TABLES `order_furniture` WRITE;
/*!40000 ALTER TABLE `order_furniture` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_furniture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_mebel`
--

DROP TABLE IF EXISTS `order_mebel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_mebel` (
  `ID_Order_Mebel` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Order` int(11) DEFAULT NULL,
  `ID_Mebel` int(11) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Square` float(9,3) DEFAULT NULL,
  `Price` float(9,3) DEFAULT NULL,
  `ID_Employee` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_Order_Mebel`),
  UNIQUE KEY `ID_Order_Mebel` (`ID_Order_Mebel`),
  KEY `ID_Employee` (`ID_Employee`),
  KEY `ID_Order` (`ID_Order`),
  KEY `ID_Mebel` (`ID_Mebel`),
  CONSTRAINT `order_mebel_fk` FOREIGN KEY (`ID_Employee`) REFERENCES `employers` (`ID_Employee`),
  CONSTRAINT `order_mebel_fk1` FOREIGN KEY (`ID_Order`) REFERENCES `orders` (`ID_Order`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_mebel_fk2` FOREIGN KEY (`ID_Mebel`) REFERENCES `mebel` (`ID_Mebel`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_mebel`
--

LOCK TABLES `order_mebel` WRITE;
/*!40000 ALTER TABLE `order_mebel` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_mebel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_sanitary`
--

DROP TABLE IF EXISTS `order_sanitary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_sanitary` (
  `ID_Order_Furniture` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Order` int(11) DEFAULT NULL,
  `ID_Furniture` int(11) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_Order_Furniture`),
  KEY `ID_Order` (`ID_Order`),
  KEY `ID_Furniture` (`ID_Furniture`),
  CONSTRAINT `order_sanitary_fk1` FOREIGN KEY (`ID_Order`) REFERENCES `orders` (`ID_Order`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_sanitary_fk2` FOREIGN KEY (`ID_Furniture`) REFERENCES `furniture` (`ID_Furniture`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_sanitary`
--

LOCK TABLES `order_sanitary` WRITE;
/*!40000 ALTER TABLE `order_sanitary` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_sanitary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_statuses`
--

DROP TABLE IF EXISTS `order_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_statuses` (
  `ID_Order_Status` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID_Order_Status`),
  UNIQUE KEY `ID_Order_Status` (`ID_Order_Status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_statuses`
--

LOCK TABLES `order_statuses` WRITE;
/*!40000 ALTER TABLE `order_statuses` DISABLE KEYS */;
INSERT INTO `order_statuses` VALUES (1,'Новый'),(2,'В работе'),(3,'Готов к доставке'),(4,'Доставлен');
/*!40000 ALTER TABLE `order_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_types`
--

DROP TABLE IF EXISTS `order_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_types` (
  `ID_Order_Type` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID_Order_Type`),
  UNIQUE KEY `ID_Order_Type` (`ID_Order_Type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_types`
--

LOCK TABLES `order_types` WRITE;
/*!40000 ALTER TABLE `order_types` DISABLE KEYS */;
INSERT INTO `order_types` VALUES (1,'Офис'),(2,'Для дома'),(3,'Кухня');
/*!40000 ALTER TABLE `order_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `ID_Order` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Order_Type` int(11) NOT NULL,
  `Number` int(11) DEFAULT NULL,
  `Date_Order_Create` date DEFAULT NULL,
  `Date_Order_Modify` date DEFAULT NULL,
  `Date_Order_Begin` date DEFAULT NULL,
  `Date_Order_End` date DEFAULT NULL,
  `Date_Shiping_Wanting` date DEFAULT NULL,
  `Date_Shiping_Max` date DEFAULT NULL,
  `ID_Customer` int(11) DEFAULT NULL,
  `ID_Order_Status` int(11) DEFAULT NULL,
  `Address` longtext,
  `Date_Order_Finish` date DEFAULT NULL,
  `ID_User` int(11) DEFAULT NULL,
  `ID_Employee` int(11) DEFAULT NULL,
  `Sum` float(9,3) DEFAULT NULL,
  `Prepay` float(9,3) DEFAULT NULL,
  `Floor` int(11) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  `Lift` bit(1) DEFAULT b'0',
  `Contact` varchar(512) DEFAULT NULL,
  `Quantity` int(11) DEFAULT '1',
  PRIMARY KEY (`ID_Order`),
  UNIQUE KEY `ID_Order` (`ID_Order`),
  KEY `Date_Order_Create` (`Date_Order_Create`),
  KEY `ID_Order_Type` (`ID_Order_Type`),
  KEY `ID_Customer` (`ID_Customer`),
  KEY `ID_Order_Satus` (`ID_Order_Status`),
  KEY `ID_User` (`ID_User`),
  KEY `ID_Employee` (`ID_Employee`),
  CONSTRAINT `orders_fk` FOREIGN KEY (`ID_Order_Type`) REFERENCES `order_types` (`ID_Order_Type`),
  CONSTRAINT `orders_fk1` FOREIGN KEY (`ID_Customer`) REFERENCES `customers` (`ID_Customer`),
  CONSTRAINT `orders_fk2` FOREIGN KEY (`ID_Order_Status`) REFERENCES `order_statuses` (`ID_Order_Status`),
  CONSTRAINT `orders_fk3` FOREIGN KEY (`ID_User`) REFERENCES `users` (`ID_User`),
  CONSTRAINT `orders_fk4` FOREIGN KEY (`ID_Employee`) REFERENCES `employers` (`ID_Employee`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameters`
--

DROP TABLE IF EXISTS `parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameters` (
  `ID_Role` int(11) NOT NULL,
  `Parameter` varchar(512) NOT NULL,
  `BoolValue` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ID_Role`,`Parameter`),
  KEY `ID_Role` (`ID_Role`),
  CONSTRAINT `parameters_fk` FOREIGN KEY (`ID_Role`) REFERENCES `roles` (`ID_Role`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameters`
--

LOCK TABLES `parameters` WRITE;
/*!40000 ALTER TABLE `parameters` DISABLE KEYS */;
/*!40000 ALTER TABLE `parameters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professions`
--

DROP TABLE IF EXISTS `professions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professions` (
  `ID_Profession` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(512) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Profession`),
  UNIQUE KEY `ID_Profession` (`ID_Profession`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professions`
--

LOCK TABLES `professions` WRITE;
/*!40000 ALTER TABLE `professions` DISABLE KEYS */;
INSERT INTO `professions` VALUES (1,'Менеджер','\0');
/*!40000 ALTER TABLE `professions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `ID_Role` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Role`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Администратор','\0');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `standard_orders`
--

DROP TABLE IF EXISTS `standard_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `standard_orders` (
  `ID_Order` int(11) NOT NULL,
  `ID_Material` int(11) DEFAULT NULL,
  `ID_Color` int(11) DEFAULT NULL,
  `ID_Edge` int(11) DEFAULT NULL,
  `ID_Handle` int(11) DEFAULT NULL,
  `ID_Glass` int(11) DEFAULT NULL,
  `Mebel_Info` longtext,
  `Pack_Info` longtext,
  `Client_Info` longtext,
  PRIMARY KEY (`ID_Order`),
  KEY `ID_Material` (`ID_Material`),
  KEY `ID_Edge` (`ID_Edge`),
  KEY `ID_Handle` (`ID_Handle`),
  KEY `ID_Glass` (`ID_Glass`),
  CONSTRAINT `standard_orders_fk` FOREIGN KEY (`ID_Order`) REFERENCES `orders` (`ID_Order`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `standard_orders_fk1` FOREIGN KEY (`ID_Material`) REFERENCES `colors` (`ID_Color`),
  CONSTRAINT `standard_orders_fk2` FOREIGN KEY (`ID_Material`) REFERENCES `materials` (`ID_Material`),
  CONSTRAINT `standard_orders_fk3` FOREIGN KEY (`ID_Edge`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `standard_orders_fk4` FOREIGN KEY (`ID_Handle`) REFERENCES `furniture` (`ID_Furniture`),
  CONSTRAINT `standard_orders_fk5` FOREIGN KEY (`ID_Glass`) REFERENCES `furniture` (`ID_Furniture`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `standard_orders`
--

LOCK TABLES `standard_orders` WRITE;
/*!40000 ALTER TABLE `standard_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `standard_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `units` (
  `ID_Unit` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `FullName` varchar(255) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_Unit`),
  UNIQUE KEY `ID_Unit` (`ID_Unit`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES (1,'шт.','Штуки','\0'),(2,'м.','Метры','\0'),(3,'м. кв.','Метры квадратные','\0'),(4,'м. пог.','Метры погонные','\0');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID_User` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(20) NOT NULL,
  `Family` varchar(255) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Patronymic` varchar(255) DEFAULT NULL,
  `ID_Profession` int(11) DEFAULT NULL,
  `Anul` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_User`),
  UNIQUE KEY `ID_User` (`ID_User`),
  UNIQUE KEY `Login` (`Login`),
  KEY `ID_Profession` (`ID_Profession`),
  CONSTRAINT `users_fk` FOREIGN KEY (`ID_Profession`) REFERENCES `professions` (`ID_Profession`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'root','Администратор','','',1,'\0'),(2,'Нет','Пользователь для привязки сотрудников без логина',NULL,NULL,NULL,'\0'),(3,'devel','Комаров','Алексей','Аркадьевич',1,'\0'),(4,'operator','','','',1,'\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `ID_User` int(11) NOT NULL,
  `ID_Role` int(11) NOT NULL,
  `Position` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_User`,`ID_Role`),
  KEY `ID_User` (`ID_User`),
  KEY `ID_Role` (`ID_Role`),
  CONSTRAINT `users_roles_fk` FOREIGN KEY (`ID_User`) REFERENCES `users` (`ID_User`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_roles_fk1` FOREIGN KEY (`ID_Role`) REFERENCES `roles` (`ID_Role`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1,2),(3,1,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `vhome_orders`
--

DROP TABLE IF EXISTS `vhome_orders`;
/*!50001 DROP VIEW IF EXISTS `vhome_orders`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vhome_orders` (
  `ID_Order` int(11),
  `ID_Material` int(11),
  `Material` varchar(512),
  `ID_Color_Base` int(11),
  `Color_Base` varchar(255),
  `ID_Color_Box` int(11),
  `Color_Box` varchar(255),
  `ID_Color_Facade` int(11),
  `Color_Facade` varchar(255),
  `ID_Edge` int(11),
  `Edge` varchar(512),
  `ID_Handle` int(11),
  `Handle` varchar(512),
  `ID_Glass` int(11),
  `Glass` varchar(512),
  `ID_Guide` int(11),
  `Guide` varchar(512),
  `Mebel_Info` longtext,
  `Pack_Info` longtext,
  `Client_Info` longtext
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `vkitchen_orders`
--

DROP TABLE IF EXISTS `vkitchen_orders`;
/*!50001 DROP VIEW IF EXISTS `vkitchen_orders`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vkitchen_orders` (
  `ID_Order` int(11),
  `Model` varchar(100),
  `ID_Box_Material` int(11),
  `Box_Material` varchar(512),
  `ID_Facade_Material` int(11),
  `Facade_Material` varchar(512),
  `ID_Facade_Glass_Material` int(11),
  `Facade_Glass_Material` varchar(512),
  `ID_Color` int(11),
  `Color` varchar(255),
  `ID_Box_Color` int(11),
  `Box_Color` varchar(255),
  `ID_Facade_Color` int(11),
  `Facade_Color` varchar(255),
  `ID_Table_Color` int(11),
  `Table_Color` varchar(255),
  `ID_Table` int(11),
  `Table` varchar(512),
  `ID_Wall_Panel` int(11),
  `Wall_Panel` varchar(512),
  `ID_Plinth` int(11),
  `Plinth` varchar(512),
  `ID_Socle` int(11),
  `Socle` varchar(512),
  `ID_Drying` int(11),
  `Drying` varchar(512),
  `ID_Lighting` int(11),
  `Lighting` varchar(512),
  `ID_Bottle_Holder` int(11),
  `Bottle_Holder` varchar(512),
  `ID_Metall` int(11),
  `Metall` varchar(512),
  `ID_Top` int(11),
  `Top` varchar(512),
  `ID_Handle` int(11),
  `Handle` varchar(512),
  `ID_Glass` int(11),
  `Glass` varchar(512),
  `ID_Guide` int(11),
  `Guide` varchar(512),
  `Notes` longtext,
  `Installation` longtext
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `vorders`
--

DROP TABLE IF EXISTS `vorders`;
/*!50001 DROP VIEW IF EXISTS `vorders`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vorders` (
  `ID_Order` int(11),
  `ID_Order_Type` int(11),
  `Number` int(11),
  `Date_Order_Create` date,
  `Date_Order_Modify` date,
  `Date_Order_Begin` date,
  `Date_Order_End` date,
  `Date_Shiping_Wanting` date,
  `Date_Shiping_Max` date,
  `ID_Customer` int(11),
  `ID_Order_Status` int(11),
  `Address` longtext,
  `Date_Order_Finish` date,
  `ID_User` int(11),
  `ID_Employee` int(11),
  `Phone` varchar(255),
  `Lift` bit(1),
  `Floor` int(11),
  `Contact` varchar(512),
  `Sum` float(9,3),
  `Prepay` float(9,3),
  `Quantity` int(11),
  `Customer` varchar(255),
  `mName` varchar(255),
  `mFamily` varchar(255),
  `mPatronymic` varchar(255),
  `mPhone` varchar(255)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `vstandard_orders`
--

DROP TABLE IF EXISTS `vstandard_orders`;
/*!50001 DROP VIEW IF EXISTS `vstandard_orders`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vstandard_orders` (
  `ID_Order` int(11),
  `ID_Material` int(11),
  `Material` varchar(512),
  `ID_Color` int(11),
  `Color` varchar(255),
  `ID_Edge` int(11),
  `Edge` varchar(512),
  `ID_Handle` int(11),
  `Handle` varchar(512),
  `ID_Glass` int(11),
  `Glass` varchar(512),
  `Mebel_Info` longtext,
  `Pack_Info` longtext,
  `Client_Info` longtext
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `xml_schemes`
--

DROP TABLE IF EXISTS `xml_schemes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xml_schemes` (
  `ID_Scheme` varchar(100) NOT NULL,
  `ID_User` int(11) NOT NULL DEFAULT '1',
  `xml` longtext NOT NULL,
  PRIMARY KEY (`ID_Scheme`,`ID_User`),
  KEY `ID_User` (`ID_User`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 AVG_ROW_LENGTH=16384;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xml_schemes`
--

LOCK TABLES `xml_schemes` WRITE;
/*!40000 ALTER TABLE `xml_schemes` DISABLE KEYS */;
INSERT INTO `xml_schemes` VALUES ('Colors',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Color\" Visible=\"1\" Width=\"7\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"93\"/>\r\n   <field Name=\"Anul\" Visible=\"0\"/>\r\n</table>\r\n'),('Customers',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Customer\" Type=\"Integer\" Width=\"13\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"26\"/>\r\n   <field Caption=\"Группа\" Hidden=\"1\" Name=\"ID_Customer_Group\" Type=\"Integer\"/>\r\n   <field Caption=\"Полное наименование\" Name=\"FullName\" Type=\"String\" Visible=\"0\" Width=\"11\"/>\r\n   <field Caption=\"Телефон\" Name=\"Phone\" Type=\"String\" Width=\"27\"/>\r\n   <field Caption=\"Этаж\" Name=\"Floor\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Лифт\" Name=\"Lift\" Type=\"Boolean\" Visible=\"0\"/>\r\n   <field Caption=\"Адрес\" Name=\"Address\" Type=\"Text\" Width=\"18\"/>\r\n   <field Caption=\"Контактное лицо\" Name=\"Contact\" Type=\"String\" Width=\"17\"/>\r\n   <field Caption=\"Комментарий\" Name=\"Comment\" Type=\"Text\" Visible=\"0\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>'),('Customer_Groups',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Customer_Group\" Visible=\"1\" Width=\"20\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"80\"/>\r\n   <field Name=\"Anul\" Visible=\"0\"/>\r\n</table>'),('Directories',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Name=\"ID_Directory\" Type=\"Integer\" Visible=\"1\" Width=\"34\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Visible=\"1\" Width=\"66\"/>\r\n   <field Caption=\"Системный\" Hidden=\"1\" Name=\"Is_System\" Type=\"Boolean\" Visible=\"0\"/>\r\n   <field Caption=\"Схема\" Hidden=\"1\" Name=\"ID_Scheme\" Type=\"String\" Visible=\"0\" Width=\"31\"/>\r\n   <field Caption=\"Таблица\" Hidden=\"1\" Name=\"Table_Name\" Type=\"String\" Visible=\"0\"/>\r\n   <field Caption=\"Параметры\" Hidden=\"1\" Name=\"Parameters\" Visible=\"0\"/>\r\n   <field Name=\"Anul\" Visible=\"0\"/>\r\n</table>\r\n'),('Employers',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Employee\" Width=\"17\"/>\r\n   <field Caption=\"Фамилия\" Name=\"Family\" Type=\"String\" Width=\"27\"/>\r\n   <field Caption=\"Имя\" Name=\"Name\" Type=\"String\" Width=\"28\"/>\r\n   <field Caption=\"Отчество\" Name=\"Patronymic\" Type=\"String\" Width=\"28\"/>\r\n   <field Caption=\"Телефон\" Name=\"Phone\" Type=\"String\" Visible=\"1\"/>\r\n   <field Caption=\"Должность\" Directory=\"professions\" Hidden=\"1\" Name=\"ID_Profession\" Type=\"Directory\" Visible=\"0\"/>\r\n   <field Caption=\"Пользователь\" Directory=\"Users\" Name=\"ID_User\" Type=\"Directory\" Visible=\"0\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>'),('Furniture',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Furniture\" Visible=\"1\" Width=\"20\"/>\r\n   <field Caption=\"Группа\" Directory=\"furniture_groups\" Hidden=\"1\" Name=\"ID_Furniture_Group\" Type=\"Directory\" Visible=\"0\" Width=\"81\"/>\r\n   <field Caption=\"Артикул\" Name=\"Art\" Type=\"String\" Width=\"20\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"38\"/>\r\n   <field Caption=\"Ед.изм.\" Directory=\"units\" Name=\"ID_Unit\" Type=\"Directory\" Width=\"21\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>\r\n'),('Furniture_Groups',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Furniture_Group\" Visible=\"1\" Width=\"8\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"92\"/>\r\n   <field Name=\"Anul\" Visible=\"0\"/>\r\n</table>\r\n'),('Images',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Image\" Visible=\"0\"/>\r\n   <field Caption=\"Заказ\" Hidden=\"1\" Name=\"ID_Order\" Visible=\"0\"/>\r\n   <field Caption=\"Имя файла\" Name=\"Name\" Type=\"String\" Width=\"80\"/>\r\n   <field Name=\"Image\" Visible=\"0\"/>\r\n</table>'),('Materials',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Material\" Type=\"Integer\" Width=\"6\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"47\"/>\r\n   <field Caption=\"Ед. изм.\" Directory=\"units\" Name=\"ID_Unit\" Type=\"Directory\" Width=\"47\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>\r\n'),('Mebel',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Mebel\" Visible=\"1\" Width=\"15\"/>\r\n   <field Caption=\"Группа\" Directory=\"mebel_groups\" Hidden=\"1\" Name=\"ID_Mebel_Group\" Type=\"Directory\" Visible=\"0\" Width=\"81\"/>\r\n   <field Caption=\"Артикул\" Name=\"Art\" Type=\"String\" Width=\"16\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"36\"/>\r\n   <field Caption=\"Квадратура\" Name=\"Square\" Type=\"Float\" Width=\"16\"/>\r\n   <field Caption=\"Цена\" Name=\"Price\" Type=\"Float\" Width=\"16\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>\r\n'),('Mebel_Groups',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Mebel_Group\" Visible=\"1\" Width=\"20\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"80\"/>\r\n   <field Name=\"Anul\" Visible=\"0\"/>\r\n</table>\r\n'),('Orders',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Order\" Type=\"Integer\" Visible=\"0\" Width=\"12\"/>\r\n   <field Caption=\"Тип\" Directory=\"order_types\" Name=\"ID_Order_Type\" Type=\"Directory\" Width=\"12\"/>\r\n   <field Caption=\"Номер\" Name=\"Number\" Type=\"Integer\" Width=\"6\"/>\r\n   <field Caption=\"Дата заказа\" Name=\"Date_Order_Create\" Type=\"Date\" Width=\"12\"/>\r\n   <field Caption=\"Дата модификации\" Name=\"Date_Order_Modify\" Type=\"Date\" Visible=\"0\" Width=\"29\"/>\r\n   <field Caption=\"Дата сдачи в работу\" Name=\"Date_Order_Begin\" Type=\"Date\" Width=\"12\"/>\r\n   <field Caption=\"Дата готовности\" Name=\"Date_Order_End\" Type=\"Date\" Width=\"10\"/>\r\n   <field Caption=\"Желаемая дата доставки\" Name=\"Date_Shiping_Wanting\" Type=\"Date\" Visible=\"0\" Width=\"29\"/>\r\n   <field Caption=\"Обязательная дата доставки\" Name=\"Date_Shiping_Max\" Type=\"Date\" Visible=\"0\" Width=\"29\"/>\r\n   <field Caption=\"Клиент\" Directory=\"Customers\" Name=\"ID_Customer\" Type=\"Directory\" Width=\"11\"/>\r\n   <field Caption=\"Статус\" Directory=\"order_statuses\" Name=\"ID_Order_Status\" Type=\"Directory\" Width=\"8\"/>\r\n   <field Caption=\"Адрес\" Name=\"Address\" Type=\"Text\" Visible=\"0\" Width=\"29\"/>\r\n   <field Caption=\"Дата закрытия\" Name=\"Date_Order_Finish\" Type=\"Date\" Width=\"10\"/>\r\n   <field Caption=\"Пользователь\" Directory=\"users\" Name=\"ID_User\" Type=\"Directories\" Visible=\"0\" Width=\"13\"/>\r\n   <field Caption=\"Менеджер\" Directory=\"employers\" Name=\"ID_Employee\" Type=\"Directories\" Visible=\"0\" Width=\"13\"/>\r\n   <field Caption=\"Сумма\" Name=\"Sum\" Type=\"Float\" Width=\"6\"/>\r\n   <field Caption=\"Предоплата\" Name=\"Prepay\" Type=\"Float\" Width=\"6\"/>\r\n   <field Caption=\"Этаж\" Name=\"Floor\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Телефон\" Name=\"Phone\" Type=\"String\" Visible=\"0\"/>\r\n   <field Caption=\"Лифт\" Name=\"Lift\" Type=\"Boolean\" Visible=\"0\"/>\r\n   <field Caption=\"Контактное лицо\" Name=\"Contact\" Type=\"String\" Visible=\"0\"/>\r\n   <field Caption=\"Количество\" Name=\"Quantity\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Полное наименование клиента\" Name=\"FullCustomerName\" Type=\"String\" Visible=\"1\"/>\r\n</table>'),('Order_Furniture',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Артикул\" Name=\"Art\" Type=\"String\" Width=\"21\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"40\"/>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Order_Furniture\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Код заказа\" Hidden=\"1\" Name=\"ID_Order\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Код изделия\" Hidden=\"1\" Name=\"ID_Furniture\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Количество\" Name=\"Quantity\" Type=\"Integer\" Width=\"20\"/>\r\n   <field Caption=\"Ед. изм.\" Name=\"Unit_Name\" Type=\"String\" Width=\"19\"/>\r\n</table>\r\n'),('Order_Mebel',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Артикул\" Name=\"Art\" Type=\"String\" Width=\"16\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"38\"/>\r\n   <field Caption=\"Ответственный менеджер\" Name=\"Manager\" Type=\"String\" Visible=\"0\" Width=\"21\"/>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Order_Mebel\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Код заказа\" Hidden=\"1\" Name=\"ID_Order\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Код изделия\" Hidden=\"1\" Name=\"ID_Mebel\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Количество\" Name=\"Quantity\" Type=\"Integer\" Width=\"11\"/>\r\n   <field Caption=\"Квадратура\" Name=\"Square\" Type=\"Float\" Width=\"11\"/>\r\n   <field Caption=\"Цена\" Name=\"Price\" Type=\"Float\" Width=\"11\"/>\r\n   <field Caption=\"Сумма\" Name=\"psum\" Type=\"float\" Width=\"11\"/>\r\n   <field Caption=\"Код отв. менеджера\" Name=\"ID_Employee\" Type=\"Integer\" Visible=\"0\"/>\r\n</table>\r\n'),('Order_Mebel_Alt',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Артикул\" Name=\"Art\" Type=\"String\" Width=\"13\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"32\"/>\r\n   <field Caption=\"Ответственный менеджер\" Name=\"Manager\" Type=\"String\" Width=\"17\"/>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Order_Mebel\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Код заказа\" Hidden=\"1\" Name=\"ID_Order\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Код изделия\" Hidden=\"1\" Name=\"ID_Mebel\" Type=\"Integer\" Visible=\"0\"/>\r\n   <field Caption=\"Количество\" Name=\"Quantity\" Type=\"Integer\" Width=\"9\"/>\r\n   <field Caption=\"Квадратура\" Name=\"Square\" Type=\"Float\" Width=\"10\"/>\r\n   <field Caption=\"Цена\" Name=\"Price\" Type=\"Float\" Width=\"9\"/>\r\n   <field Caption=\"Сумма\" Name=\"psum\" Type=\"float\" Width=\"9\"/>\r\n   <field Caption=\"Код отв. менеджера\" Name=\"ID_Employee\" Type=\"Integer\" Visible=\"0\"/>\r\n</table>\r\n'),('Professions',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Profession\" Visible=\"1\" Width=\"23\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"77\"/>\r\n   <field Name=\"Anul\" Visible=\"0\"/>\r\n</table>\r\n'),('Roles',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Role\" Visible=\"0\" Width=\"17\"/>\r\n   <field Caption=\"Роль\" Name=\"Name\" Type=\"String\" Width=\"100\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>\r\n'),('Roles',4,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Role\" Visible=\"0\" Width=\"17\"/>\r\n   <field Caption=\"Роль\" Name=\"Name\" Type=\"String\" Width=\"100\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>\r\n'),('tao.FrmTaoCustomers',1,'<frame Width=\"756\" Height=\"370\" X=\"0\" Y=\"0\"></frame>'),('tao.FrmTaoDirectories',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"613\"  Height=\"417\"  X=\"133\"  Y=\"101\"><Splitter Number=\"0\" Location=\"201\"/></frame>'),('tao.FrmTaoEmployers',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"0\"  Height=\"0\"  X=\"0\"  Y=\"0\"><Splitter Number=\"0\" Location=\"201\"/></frame>'),('tao.FrmTaoFurniture',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"0\"  Height=\"0\"  X=\"0\"  Y=\"0\"><Splitter Number=\"0\" Location=\"201\"/></frame>'),('tao.FrmTaoMain',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"112\"  Height=\"27\"  X=\"0\"  Y=\"0\"></frame>'),('tao.FrmTaoMain',4,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"112\"  Height=\"27\"  X=\"0\"  Y=\"0\"></frame>'),('tao.FrmTaoMain',29,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"112\"  Height=\"27\"  X=\"0\"  Y=\"0\"></frame>'),('tao.FrmTaoMebel',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"0\"  Height=\"0\"  X=\"0\"  Y=\"0\"><Splitter Number=\"0\" Location=\"201\"/></frame>'),('tao.FrmTaoOrders',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"1024\"  Height=\"333\"  X=\"0\"  Y=\"0\"></frame>'),('tao.FrmTaoPrint',1,'<frame Width=\"768\" Height=\"512\" X=\"61\" Y=\"18\"></frame>'),('tao.FrmTaoRoles',1,'<frame Width=\"778\" Height=\"352\" X=\"41\" Y=\"59\"></frame>'),('tao.FrmTaoRoles',4,'<frame Width=\"778\" Height=\"352\" X=\"41\" Y=\"59\"></frame>'),('tao.FrmTaoRules',1,'<frame Width=\"795\" Height=\"398\" X=\"130\" Y=\"161\"><Splitter Number=\"0\" Location=\"251\"/></frame>'),('tao.FrmTaoUsers',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"882\"  Height=\"583\"  X=\"85\"  Y=\"37\"></frame>'),('tao.FrmTaoUsers',4,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"882\"  Height=\"583\"  X=\"85\"  Y=\"37\"></frame>'),('tao.order.FrmOrder',1,'<frame State=\"MAXIMIZED_BOTH\"  Width=\"880\"  Height=\"562\"  X=\"66\"  Y=\"16\"></frame>'),('testdir',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID\" Type=\"Integer\" Width=\"16\"/>\r\n   <field Caption=\"Число\" Name=\"test_int\" Type=\"Integer\" Width=\"8\"/>\r\n   <field Caption=\"Строка\" Name=\"test_varchar\" Type=\"String\" Width=\"11\"/>\r\n   <field Caption=\"Текст\" Name=\"test_text\" Type=\"Text\" Width=\"15\"/>\r\n   <field Caption=\"Справочник\" Directory=\"directories\" Name=\"test_dir\" Type=\"Directory\" Width=\"20\"/>\r\n   <field Caption=\"Да/Нет\" Name=\"test_bit\" Type=\"Boolean\" Width=\"19\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n   <field Caption=\"Дробное\" Name=\"testFloat\" Type=\"Float\" Width=\"10\"/>\r\n</table>\r\n'),('Units',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_Unit\" Visible=\"1\" Width=\"6\"/>\r\n   <field Caption=\"Наименование\" Name=\"Name\" Type=\"String\" Width=\"21\"/>\r\n   <field Caption=\"Поное наименование\" Name=\"FullName\" Type=\"String\" Width=\"73\"/>\r\n   <field Name=\"Anul\" Visible=\"0\"/>\r\n</table>\r\n'),('Users',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_User\" Width=\"8\"/>\r\n   <field Caption=\"Логин\" Ident=\"1\" Name=\"Login\" NewOnly=\"1\" Type=\"String\" Width=\"13\"/>\r\n   <field Caption=\"Фамилия\" Name=\"Family\" Type=\"String\" Width=\"21\"/>\r\n   <field Caption=\"Имя\" Name=\"Name\" Type=\"String\" Width=\"18\"/>\r\n   <field Caption=\"Отчество\" Name=\"Patronymic\" Type=\"String\" Width=\"20\"/>\r\n   <field Caption=\"Должность\" Directory=\"professions\" Name=\"ID_Profession\" Type=\"Directory\" Width=\"20\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>\r\n'),('Users',4,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Hidden=\"1\" Name=\"ID_User\" Width=\"8\"/>\r\n   <field Caption=\"Логин\" Ident=\"1\" Name=\"Login\" NewOnly=\"1\" Type=\"String\" Width=\"13\"/>\r\n   <field Caption=\"Фамилия\" Name=\"Family\" Type=\"String\" Width=\"21\"/>\r\n   <field Caption=\"Имя\" Name=\"Name\" Type=\"String\" Width=\"18\"/>\r\n   <field Caption=\"Отчество\" Name=\"Patronymic\" Type=\"String\" Width=\"20\"/>\r\n   <field Caption=\"Должность\" Directory=\"professions\" Name=\"ID_Profession\" Type=\"Directory\" Width=\"20\"/>\r\n   <field Caption=\"Удален\" Hidden=\"1\" Name=\"Anul\" Visible=\"0\" Width=\"17\"/>\r\n</table>\r\n'),('Users_Roles',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Пользователь\" Hidden=\"1\" Name=\"ID_User\" Visible=\"0\" Width=\"17\"/>\r\n   <field Caption=\"Роль\" Directory=\"roles\" Name=\"ID_Role\" NewOnly=\"1\" Type=\"Directory\" Width=\"81\"/>\r\n   <field Caption=\"Номер\" Name=\"Position\" Type=\"Integer\" Width=\"19\"/>\r\n</table>\r\n'),('Users_Roles',4,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Пользователь\" Hidden=\"1\" Name=\"ID_User\" Visible=\"0\" Width=\"17\"/>\r\n   <field Caption=\"Роль\" Directory=\"roles\" Name=\"ID_Role\" NewOnly=\"1\" Type=\"Directory\" Width=\"81\"/>\r\n   <field Caption=\"Номер\" Name=\"Position\" Type=\"Integer\" Width=\"19\"/>\r\n</table>\r\n'),('XML_Schemes',1,'<?xml version=\"1.0\" encoding=\"windows-1251\" standalone=\"no\"?>\r\n<table>\r\n   <field Caption=\"Код\" Name=\"ID_Scheme\" Visible=\"1\" Width=\"17\"/>\r\n   <field Caption=\"Схема\" Name=\"xml\" Visible=\"1\" Width=\"83\"/>\r\n</table>\r\n');
/*!40000 ALTER TABLE `xml_schemes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'etalon'
--
/*!50003 DROP PROCEDURE IF EXISTS `setBooleanParam` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp1251 */ ;
/*!50003 SET character_set_results = cp1251 */ ;
/*!50003 SET collation_connection  = cp1251_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `setBooleanParam`(
        IN aID_Role INTEGER(11),
        IN aKey VARCHAR(512),
        IN aValue INTEGER(11)
    )
BEGIN
  DECLARE aBoolValue BIT DEFAULT 0;

  SET aBoolValue = 0;

  IF aValue = 0 THEN
    DELETE FROM parameters WHERE ID_Role=aID_Role AND Parameter = aKey;
  ELSE
    IF aValue = 1 THEN
      SET aBoolValue = 1;
    END IF;
      
    IF EXISTS(SELECT * FROM parameters WHERE ID_Role=aID_Role AND Parameter = aKey) THEN
      UPDATE parameters SET BoolValue = aBoolValue WHERE ID_Role=aID_Role AND Parameter = aKey;
    ELSE
      INSERT INTO parameters (ID_Role, Parameter, BoolValue) VALUES(aID_Role, aKey, aBoolValue);
    END IF;
  END IF;
  COMMIT;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `setFrame_Settings` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp1251 */ ;
/*!50003 SET character_set_results = cp1251 */ ;
/*!50003 SET collation_connection  = cp1251_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `setFrame_Settings`(
        IN aID_frm VARCHAR(255),
        IN aXML LONGTEXT
    )
    MODIFIES SQL DATA
    SQL SECURITY INVOKER
BEGIN
  IF EXISTS(SELECT * FROM frames_settings WHERE ID_Frm = aID_Frm) THEN
    UPDATE frames_settings SET xml = aXML WHERE ID_Frm = aID_Frm;
  ELSE  
    INSERT INTO frames_settings (ID_Frm, xml) VALUES (aID_Frm, aXML);
  END IF;
  COMMIT;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `setScheme` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp1251 */ ;
/*!50003 SET character_set_results = cp1251 */ ;
/*!50003 SET collation_connection  = cp1251_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `setScheme`(
        IN aID_Scheme VARCHAR(255),
        IN aXML LONGTEXT,
        IN aID_User INTEGER(11)
    )
    MODIFIES SQL DATA
    SQL SECURITY INVOKER
BEGIN
  IF EXISTS(SELECT * FROM XML_Schemes WHERE ID_Scheme = aID_Scheme AND ID_User = aID_User) THEN
    UPDATE XML_Schemes SET xml = aXML WHERE ID_Scheme = aID_Scheme;
  ELSE  
    INSERT INTO XML_Schemes(ID_Scheme, ID_User, XML) VALUES (aID_Scheme, aID_User, aXML);
  END IF;
  COMMIT; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateUsers` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp1251 */ ;
/*!50003 SET character_set_results = cp1251 */ ;
/*!50003 SET collation_connection  = cp1251_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `updateUsers`()
BEGIN
  DECLARE login varchar(255);
  DECLARE anul BOOLEAN;
  DECLARE done BOOLEAN DEFAULT 0;
  
  DECLARE cr CURSOR FOR 
    SELECT u.Login, u.Anul FROM users AS u;

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=1;    
    
  OPEN cr;

  REPEAT
    FETCH cr INTO login, anul;
    IF NOT done THEN
      IF (anul) AND 
         (EXISTS(SELECT * FROM mysql.user WHERE User = login)) 
      THEN
        SET @s = concat('DROP USER ', login);
        PREPARE cru FROM @s;
        EXECUTE cru;
      END IF;     
      IF (NOT anul) AND 
         (NOT EXISTS(SELECT * FROM mysql.user WHERE User = login)) 
      THEN
         SET @s = concat('CREATE USER ', login);
         PREPARE cru FROM @s;
         EXECUTE cru;
         DEALLOCATE PREPARE cru;
         
         SET @s = concat('GRANT ALL PRIVILEGES ON ', 
                         DATABASE(), 
                         '.* TO ', 
                         login,' 
                         WITH GRANT OPTION'
         );
         
         PREPARE cru FROM @s;
         EXECUTE cru;
         DEALLOCATE PREPARE cru;

         set @s = concat('GRANT SELECT ON `mysql`.`proc` TO ', login);
         PREPARE cru FROM @s;
         EXECUTE cru;
         DEALLOCATE PREPARE cru;
       END IF;
    END IF;
    
  UNTIL done END REPEAT;  

  CLOSE cr;
  FLUSH PRIVILEGES;
  COMMIT;
    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `vhome_orders`
--

/*!50001 DROP TABLE IF EXISTS `vhome_orders`*/;
/*!50001 DROP VIEW IF EXISTS `vhome_orders`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = cp1251 */;
/*!50001 SET character_set_results     = cp1251 */;
/*!50001 SET collation_connection      = cp1251_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vhome_orders` AS select `ho`.`ID_Order` AS `ID_Order`,`ho`.`ID_Material` AS `ID_Material`,`m`.`Name` AS `Material`,`ho`.`ID_Color_Base` AS `ID_Color_Base`,`c1`.`Name` AS `Color_Base`,`ho`.`ID_Color_Box` AS `ID_Color_Box`,`c2`.`Name` AS `Color_Box`,`ho`.`ID_Color_Facade` AS `ID_Color_Facade`,`c3`.`Name` AS `Color_Facade`,`ho`.`ID_Edge` AS `ID_Edge`,`f1`.`Name` AS `Edge`,`ho`.`ID_Handle` AS `ID_Handle`,`f2`.`Name` AS `Handle`,`ho`.`ID_Glass` AS `ID_Glass`,`f3`.`Name` AS `Glass`,`ho`.`ID_Guide` AS `ID_Guide`,`f4`.`Name` AS `Guide`,`ho`.`Mebel_Info` AS `Mebel_Info`,`ho`.`Pack_Info` AS `Pack_Info`,`ho`.`Client_Info` AS `Client_Info` from ((((((((`home_orders` `ho` left join `materials` `m` on((`m`.`ID_Material` = `ho`.`ID_Material`))) left join `colors` `c1` on((`c1`.`ID_Color` = `ho`.`ID_Color_Base`))) left join `colors` `c2` on((`c2`.`ID_Color` = `ho`.`ID_Color_Box`))) left join `colors` `c3` on((`c3`.`ID_Color` = `ho`.`ID_Color_Facade`))) left join `furniture` `f1` on((`f1`.`ID_Furniture` = `ho`.`ID_Edge`))) left join `furniture` `f2` on((`f2`.`ID_Furniture` = `ho`.`ID_Handle`))) left join `furniture` `f3` on((`f3`.`ID_Furniture` = `ho`.`ID_Glass`))) left join `furniture` `f4` on((`f4`.`ID_Furniture` = `ho`.`ID_Guide`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vkitchen_orders`
--

/*!50001 DROP TABLE IF EXISTS `vkitchen_orders`*/;
/*!50001 DROP VIEW IF EXISTS `vkitchen_orders`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = cp1251 */;
/*!50001 SET character_set_results     = cp1251 */;
/*!50001 SET collation_connection      = cp1251_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vkitchen_orders` AS select `ko`.`ID_Order` AS `ID_Order`,`ko`.`Model` AS `Model`,`ko`.`ID_Box_Material` AS `ID_Box_Material`,`mb`.`Name` AS `Box_Material`,`ko`.`ID_Facade_Material` AS `ID_Facade_Material`,`mf`.`Name` AS `Facade_Material`,`ko`.`ID_Facade_Glass_Material` AS `ID_Facade_Glass_Material`,`mfg`.`Name` AS `Facade_Glass_Material`,`ko`.`ID_Color` AS `ID_Color`,`c`.`Name` AS `Color`,`ko`.`ID_Box_Color` AS `ID_Box_Color`,`cb`.`Name` AS `Box_Color`,`ko`.`ID_Facade_Color` AS `ID_Facade_Color`,`cf`.`Name` AS `Facade_Color`,`ko`.`ID_Table_Color` AS `ID_Table_Color`,`ct`.`Name` AS `Table_Color`,`ko`.`ID_Table` AS `ID_Table`,`fta`.`Name` AS `Table`,`ko`.`ID_Wall_Panel` AS `ID_Wall_Panel`,`fwa`.`Name` AS `Wall_Panel`,`ko`.`ID_Plinth` AS `ID_Plinth`,`fpl`.`Name` AS `Plinth`,`ko`.`ID_Socle` AS `ID_Socle`,`fso`.`Name` AS `Socle`,`ko`.`ID_Drying` AS `ID_Drying`,`fdr`.`Name` AS `Drying`,`ko`.`ID_Lighting` AS `ID_Lighting`,`fli`.`Name` AS `Lighting`,`ko`.`ID_Bottle_Holder` AS `ID_Bottle_Holder`,`fbo`.`Name` AS `Bottle_Holder`,`ko`.`ID_Metall` AS `ID_Metall`,`fme`.`Name` AS `Metall`,`ko`.`ID_Top` AS `ID_Top`,`fto`.`Name` AS `Top`,`ko`.`ID_Handle` AS `ID_Handle`,`fha`.`Name` AS `Handle`,`ko`.`ID_Glass` AS `ID_Glass`,`fgl`.`Name` AS `Glass`,`ko`.`ID_Guide` AS `ID_Guide`,`fgu`.`Name` AS `Guide`,`ko`.`Notes` AS `Notes`,`ko`.`Installation` AS `Installation` from (((((((((((((((((((`kitchen_orders` `ko` left join `materials` `mb` on((`mb`.`ID_Material` = `ko`.`ID_Box_Material`))) left join `materials` `mf` on((`mf`.`ID_Material` = `ko`.`ID_Facade_Material`))) left join `materials` `mfg` on((`mfg`.`ID_Material` = `ko`.`ID_Facade_Glass_Material`))) left join `colors` `c` on((`c`.`ID_Color` = `ko`.`ID_Color`))) left join `colors` `cb` on((`cb`.`ID_Color` = `ko`.`ID_Box_Color`))) left join `colors` `cf` on((`cf`.`ID_Color` = `ko`.`ID_Facade_Color`))) left join `colors` `ct` on((`ct`.`ID_Color` = `ko`.`ID_Table_Color`))) left join `furniture` `fta` on((`fta`.`ID_Furniture` = `ko`.`ID_Table`))) left join `furniture` `fwa` on((`fwa`.`ID_Furniture` = `ko`.`ID_Wall_Panel`))) left join `furniture` `fpl` on((`fpl`.`ID_Furniture` = `ko`.`ID_Plinth`))) left join `furniture` `fso` on((`fso`.`ID_Furniture` = `ko`.`ID_Socle`))) left join `furniture` `fdr` on((`fdr`.`ID_Furniture` = `ko`.`ID_Drying`))) left join `furniture` `fli` on((`fli`.`ID_Furniture` = `ko`.`ID_Lighting`))) left join `furniture` `fbo` on((`fbo`.`ID_Furniture` = `ko`.`ID_Bottle_Holder`))) left join `furniture` `fme` on((`fme`.`ID_Furniture` = `ko`.`ID_Metall`))) left join `furniture` `fto` on((`fto`.`ID_Furniture` = `ko`.`ID_Top`))) left join `furniture` `fha` on((`fha`.`ID_Furniture` = `ko`.`ID_Handle`))) left join `furniture` `fgl` on((`fgl`.`ID_Furniture` = `ko`.`ID_Glass`))) left join `furniture` `fgu` on((`fgu`.`ID_Furniture` = `ko`.`ID_Guide`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vorders`
--

/*!50001 DROP TABLE IF EXISTS `vorders`*/;
/*!50001 DROP VIEW IF EXISTS `vorders`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = cp1251 */;
/*!50001 SET character_set_results     = cp1251 */;
/*!50001 SET collation_connection      = cp1251_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vorders` AS select `o`.`ID_Order` AS `ID_Order`,`o`.`ID_Order_Type` AS `ID_Order_Type`,`o`.`Number` AS `Number`,`o`.`Date_Order_Create` AS `Date_Order_Create`,`o`.`Date_Order_Modify` AS `Date_Order_Modify`,`o`.`Date_Order_Begin` AS `Date_Order_Begin`,`o`.`Date_Order_End` AS `Date_Order_End`,`o`.`Date_Shiping_Wanting` AS `Date_Shiping_Wanting`,`o`.`Date_Shiping_Max` AS `Date_Shiping_Max`,`o`.`ID_Customer` AS `ID_Customer`,`o`.`ID_Order_Status` AS `ID_Order_Status`,`o`.`Address` AS `Address`,`o`.`Date_Order_Finish` AS `Date_Order_Finish`,`o`.`ID_User` AS `ID_User`,`o`.`ID_Employee` AS `ID_Employee`,`o`.`Phone` AS `Phone`,`o`.`Lift` AS `Lift`,`o`.`Floor` AS `Floor`,`o`.`Contact` AS `Contact`,`o`.`Sum` AS `Sum`,`o`.`Prepay` AS `Prepay`,`o`.`Quantity` AS `Quantity`,`c`.`Name` AS `Customer`,`e`.`Name` AS `mName`,`e`.`Family` AS `mFamily`,`e`.`Patronymic` AS `mPatronymic`,`e`.`Phone` AS `mPhone` from ((`orders` `o` left join `customers` `c` on((`c`.`ID_Customer` = `o`.`ID_Customer`))) left join `employers` `e` on((`e`.`ID_Employee` = `o`.`ID_Employee`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vstandard_orders`
--

/*!50001 DROP TABLE IF EXISTS `vstandard_orders`*/;
/*!50001 DROP VIEW IF EXISTS `vstandard_orders`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = cp1251 */;
/*!50001 SET character_set_results     = cp1251 */;
/*!50001 SET collation_connection      = cp1251_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vstandard_orders` AS select `so`.`ID_Order` AS `ID_Order`,`so`.`ID_Material` AS `ID_Material`,`m`.`Name` AS `Material`,`so`.`ID_Color` AS `ID_Color`,`c`.`Name` AS `Color`,`so`.`ID_Edge` AS `ID_Edge`,`e`.`Name` AS `Edge`,`so`.`ID_Handle` AS `ID_Handle`,`h`.`Name` AS `Handle`,`so`.`ID_Glass` AS `ID_Glass`,`g`.`Name` AS `Glass`,`so`.`Mebel_Info` AS `Mebel_Info`,`so`.`Pack_Info` AS `Pack_Info`,`so`.`Client_Info` AS `Client_Info` from (((((`standard_orders` `so` left join `materials` `m` on((`m`.`ID_Material` = `so`.`ID_Material`))) left join `colors` `c` on((`c`.`ID_Color` = `so`.`ID_Color`))) left join `furniture` `e` on((`e`.`ID_Furniture` = `so`.`ID_Edge`))) left join `furniture` `h` on((`h`.`ID_Furniture` = `so`.`ID_Handle`))) left join `furniture` `g` on((`g`.`ID_Furniture` = `so`.`ID_Glass`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-07-11  9:35:04
