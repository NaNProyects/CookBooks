CREATE DATABASE  IF NOT EXISTS `cookbooksbase` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cookbooksbase`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: cookbooksbase
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `autor`
--

DROP TABLE IF EXISTS `autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autor` (
  `idAutor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  PRIMARY KEY (`idAutor`),
  UNIQUE KEY `apNom` (`nombre`,`apellido`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor`
--

LOCK TABLES `autor` WRITE;
/*!40000 ALTER TABLE `autor` DISABLE KEYS */;
INSERT INTO `autor` VALUES (1,'Carmin','Valldejuli'),(7,'Cecilia','Fassardi'),(5,'Christine','Bailey'),(2,'Kristen','Feola'),(3,'Mirta G.','Carabajal'),(4,'Petrona c.','de Gandulfo'),(6,'Toni','Rodriguez');
/*!40000 ALTER TABLE `autor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libro` (
  `ISBN` varchar(15) NOT NULL,
  `titulo` varchar(45) NOT NULL,
  `precio` float NOT NULL,
  `genero` varchar(45) NOT NULL,
  `reseña` text NOT NULL,
  `vistazo` text NOT NULL,
  `editorial` varchar(45) NOT NULL,
  `idioma` varchar(45) NOT NULL,
  `autor` int(11) NOT NULL,
  PRIMARY KEY (`ISBN`),
  UNIQUE KEY `ISBN_UNIQUE` (`ISBN`),
  KEY `libroAutor_idx` (`autor`),
  CONSTRAINT `libroAutor` FOREIGN KEY (`autor`) REFERENCES `autor` (`idAutor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
INSERT INTO `libro` VALUES ('1234567890','La guia óptima para el ayuno de Daniel',69,'guia','reseña reseña','vistazo vistazo vistazo ','Sudamericana','Español',2),('1478523698','CUPCAKES VEGANOS',47.8,'cupcakes','reseña reseña','vistazo vistazo vistazo ','Sudamericana','Español',6),('8521479632','EL LIBRO DE LAS VIANDAS PARA PEQUENOS',79.84,'viandas','reseña reseña','vistazo vistazo vistazo ','Planeta','Español',7),('8789876554','LA DIETA DE LOS ZUMOS',99.99,'jugos','reseña reseña','vistazo vistazo vistazo ','Sudamericana','Español',1),('8795484816','LAS MEJORES RECETAS DE RICO Y ABUNDANTE',87.45,'receta','reseña reseña','vistazo vistazo vistazo ','Debate','Español',1),('8828942931','Cocina criolla',58.99,'criolla','reseña reseña','vistazo vistazo vistazo ','Debate','Español',1),('8884447779','COCINA CON CALOR DE HOGAR - RUSTICA',152.21,'rustica','reseña reseña','vistazo vistazo vistazo ','Planeta','Español',4);
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libropedido`
--

DROP TABLE IF EXISTS `libropedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libropedido` (
  `idLibroPedido` int(11) NOT NULL AUTO_INCREMENT,
  `pedido` int(11) NOT NULL,
  `libro` varchar(15) NOT NULL,
  PRIMARY KEY (`idLibroPedido`),
  KEY `pedidoPedidoLibro_idx` (`pedido`),
  KEY `libroPedidoLibro_idx` (`libro`),
  CONSTRAINT `libroPedidoLibro` FOREIGN KEY (`libro`) REFERENCES `libro` (`ISBN`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pedidoPedidoLibro` FOREIGN KEY (`pedido`) REFERENCES `pedido` (`idPedido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libropedido`
--

LOCK TABLES `libropedido` WRITE;
/*!40000 ALTER TABLE `libropedido` DISABLE KEYS */;
INSERT INTO `libropedido` VALUES (1,1,'8828942931'),(2,2,'1234567890'),(3,3,'8795484816'),(4,4,'8884447779'),(5,5,'8789876554'),(6,6,'1478523698');
/*!40000 ALTER TABLE `libropedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `idPedido` int(11) NOT NULL AUTO_INCREMENT,
  `total` float NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `fecha` date NOT NULL,
  `usuario` int(11) NOT NULL,
  PRIMARY KEY (`idPedido`),
  UNIQUE KEY `idPedido_UNIQUE` (`idPedido`),
  KEY `pedidoUsuario_idx` (`usuario`),
  CONSTRAINT `pedidoUsuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`DNI`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,58.99,0,'2013-03-31',11454789),(2,69,0,'2013-08-25',10222333),(3,87.45,0,'2013-07-24',30876961),(4,152.21,0,'2013-06-06',2968741),(5,99.99,0,'2013-05-03',3478987),(6,47.8,1,'2011-01-02',12547897),(7,79.84,1,'2012-01-01',14879564);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `DNI` int(11) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `tarjeta` varchar(20) NOT NULL,
  `pin` varchar(4) NOT NULL,
  `fechaRegistro` date NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `contraseña` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `DNI_UNIQUE` (`DNI`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `idUsuario_UNIQUE` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,7777777,'7777777','77777777777','0000','1990-06-10','21 nº777','21232f297a57a5a743894a0e4a801fc3','admin','Administrador','  '),(2,3478987,'4708987','3478123456788987','0000','1999-03-05','34 nº987','d8578edf8458ce06fbc5bb76a58c5ca4','sebastianeguren@cookbooks.com','Sebastian','Eguren'),(3,10222333,'4702333','1022123456782333','0000','2001-08-25','10 nº2333','d8578edf8458ce06fbc5bb76a58c5ca4','robertojuarez@cookbooks.com','Roberto','Juarez'),(4,11454789,'4704789','1145123456784789','0000','1983-03-31','11 nº4789','d8578edf8458ce06fbc5bb76a58c5ca4','carlossanchez@cookbooks.com','Carlos','Sanchez'),(5,12547897,'4707897','1254123456787897','0000','2011-02-01','12 nº897','d8578edf8458ce06fbc5bb76a58c5ca4','marialopez@cookbooks.com','Maria','Lopez'),(6,14879564,'4709564','1487123456789564','0000','2012-01-01','14 nº564','d8578edf8458ce06fbc5bb76a58c5ca4','catalinaperez@cookbooks.com','Catalina','Perez'),(7,30876961,'4706961','3087123456786961','0000','2012-07-24','30 nº961','d8578edf8458ce06fbc5bb76a58c5ca4','arielpasini@cookbooks.com','Ariel','Pasini'),(8,2968741,'4708741','2968123456788741','0000','2006-06-06','29 nº741','d8578edf8458ce06fbc5bb76a58c5ca4','nicolasgaldamez@cookbooks.com','Nicolas','Galdamez');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-22  2:12:50
