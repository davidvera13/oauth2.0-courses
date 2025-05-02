-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: oauth2
-- ------------------------------------------------------
-- Server version	8.4.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorization_consents`
--

DROP TABLE IF EXISTS `authorization_consents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorization_consents` (
  `principal_name` varchar(255) NOT NULL,
  `registered_client_id` varchar(255) NOT NULL,
  `authorities` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`principal_name`,`registered_client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorization_consents`
--

LOCK TABLES `authorization_consents` WRITE;
/*!40000 ALTER TABLE `authorization_consents` DISABLE KEYS */;
INSERT INTO `authorization_consents` (`principal_name`, `registered_client_id`, `authorities`) VALUES ('admin@mail.com','client','SCOPE_openid,SCOPE_email,SCOPE_profile');
INSERT INTO `authorization_consents` (`principal_name`, `registered_client_id`, `authorities`) VALUES ('admin@mail.com','gateway','SCOPE_openid,SCOPE_profile');
INSERT INTO `authorization_consents` (`principal_name`, `registered_client_id`, `authorities`) VALUES ('davidvera.javaspring@gmail.com','client','SCOPE_openid,SCOPE_email,SCOPE_profile');
INSERT INTO `authorization_consents` (`principal_name`, `registered_client_id`, `authorities`) VALUES ('user@mail.com','client','SCOPE_openid,SCOPE_email,SCOPE_profile');
INSERT INTO `authorization_consents` (`principal_name`, `registered_client_id`, `authorities`) VALUES ('user@mail.com','gateway','SCOPE_openid,SCOPE_profile');
/*!40000 ALTER TABLE `authorization_consents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorization_grant_types`
--

DROP TABLE IF EXISTS `authorization_grant_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorization_grant_types` (
  `client_id` bigint NOT NULL,
  `authorization_grant_types` varchar(255) DEFAULT NULL,
  KEY `FK55sxksdxx45ch1oq7rqh4u6ms` (`client_id`),
  CONSTRAINT `FK55sxksdxx45ch1oq7rqh4u6ms` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorization_grant_types`
--

LOCK TABLES `authorization_grant_types` WRITE;
/*!40000 ALTER TABLE `authorization_grant_types` DISABLE KEYS */;
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (2,'refresh_token');
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (2,'client_credentials');
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (2,'authorization_code');
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (5,'refresh_token');
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (5,'authorization_code');
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (5,'client_credentials');
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (13,'refresh_token');
INSERT INTO `authorization_grant_types` (`client_id`, `authorization_grant_types`) VALUES (13,'authorization_code');
/*!40000 ALTER TABLE `authorization_grant_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_authentication_methods`
--

DROP TABLE IF EXISTS `client_authentication_methods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_authentication_methods` (
  `client_id` bigint NOT NULL,
  `client_authentication_methods` varchar(255) DEFAULT NULL,
  KEY `FK5wubst9l8tryd1gom02854g1n` (`client_id`),
  CONSTRAINT `FK5wubst9l8tryd1gom02854g1n` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_authentication_methods`
--

LOCK TABLES `client_authentication_methods` WRITE;
/*!40000 ALTER TABLE `client_authentication_methods` DISABLE KEYS */;
INSERT INTO `client_authentication_methods` (`client_id`, `client_authentication_methods`) VALUES (2,'client_secret_basic');
INSERT INTO `client_authentication_methods` (`client_id`, `client_authentication_methods`) VALUES (5,'client_secret_basic');
INSERT INTO `client_authentication_methods` (`client_id`, `client_authentication_methods`) VALUES (13,'client_secret_basic');
/*!40000 ALTER TABLE `client_authentication_methods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `require_proof_key` bit(1) NOT NULL,
  `client_id_issued_at` datetime(6) DEFAULT NULL,
  `client_secret_expires_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` (`require_proof_key`, `client_id_issued_at`, `client_secret_expires_at`, `id`, `client_id`, `client_secret`) VALUES (_binary '',NULL,NULL,2,'client','$2a$10$Rs3bOXZQXiSrrqRW28l7Xeq0m/Xng6vJgPLvwXvkL8wOJqCGLukGq');
INSERT INTO `clients` (`require_proof_key`, `client_id_issued_at`, `client_secret_expires_at`, `id`, `client_id`, `client_secret`) VALUES (_binary '',NULL,NULL,5,'oidc-client','$2a$10$3Glyd7O6g6ZIVbcvD7T3mODlVPh0SOENPWQjmKakLG32b1HuJAFtK');
INSERT INTO `clients` (`require_proof_key`, `client_id_issued_at`, `client_secret_expires_at`, `id`, `client_id`, `client_secret`) VALUES (_binary '\0',NULL,NULL,13,'gateway','$2a$10$S.xSApaRWFX6yKZkUne93uFrH8VGnv1vx50RF1bGiqXLUCLmxxj7m');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `google_users`
--

DROP TABLE IF EXISTS `google_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `google_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `family_name` varchar(255) DEFAULT NULL,
  `given_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `google_users`
--

LOCK TABLES `google_users` WRITE;
/*!40000 ALTER TABLE `google_users` DISABLE KEYS */;
INSERT INTO `google_users` (`id`, `email`, `family_name`, `given_name`, `name`, `picture_url`) VALUES (2,'davidvera.javaspring@gmail.com','Vera','David','David Vera','https://lh3.googleusercontent.com/a/ACg8ocJA0vqyawgVCOrRQHCeoggrO8JYpusWzZPjSIIlfgTaNS8ung=s96-c');
/*!40000 ALTER TABLE `google_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `redirect_uris`
--

DROP TABLE IF EXISTS `redirect_uris`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `redirect_uris` (
  `client_id` bigint NOT NULL,
  `redirect_uris` varchar(255) DEFAULT NULL,
  KEY `FK4r8frqc4paldqckd85ej510hu` (`client_id`),
  CONSTRAINT `FK4r8frqc4paldqckd85ej510hu` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `redirect_uris`
--

LOCK TABLES `redirect_uris` WRITE;
/*!40000 ALTER TABLE `redirect_uris` DISABLE KEYS */;
INSERT INTO `redirect_uris` (`client_id`, `redirect_uris`) VALUES (2,'http://localhost:5200/authorized');
INSERT INTO `redirect_uris` (`client_id`, `redirect_uris`) VALUES (5,'https://oauthdebugger.com/debug');
INSERT INTO `redirect_uris` (`client_id`, `redirect_uris`) VALUES (13,'http://127.0.0.1:8765/login/oauth2/code/gateway');
/*!40000 ALTER TABLE `redirect_uris` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` enum('ROLE_ADMIN','ROLE_USER') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `roles` (`id`, `name`) VALUES (2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_authorities`
--

DROP TABLE IF EXISTS `roles_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_authorities` (
  `authorities_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  KEY `FKe4pjsn2c2ttg8bpbe1yk29snn` (`authorities_id`),
  KEY `FKcj918h3ee3qad1xwbx4jvvcgc` (`roles_id`),
  CONSTRAINT `FKcj918h3ee3qad1xwbx4jvvcgc` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKe4pjsn2c2ttg8bpbe1yk29snn` FOREIGN KEY (`authorities_id`) REFERENCES `authorities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_authorities`
--

LOCK TABLES `roles_authorities` WRITE;
/*!40000 ALTER TABLE `roles_authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scopes`
--

DROP TABLE IF EXISTS `scopes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scopes` (
  `client_id` bigint NOT NULL,
  `scopes` varchar(255) DEFAULT NULL,
  KEY `FKtdkrlvq23c2ayaycl49sqlcp0` (`client_id`),
  CONSTRAINT `FKtdkrlvq23c2ayaycl49sqlcp0` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scopes`
--

LOCK TABLES `scopes` WRITE;
/*!40000 ALTER TABLE `scopes` DISABLE KEYS */;
INSERT INTO `scopes` (`client_id`, `scopes`) VALUES (2,'openid');
INSERT INTO `scopes` (`client_id`, `scopes`) VALUES (5,'openid');
INSERT INTO `scopes` (`client_id`, `scopes`) VALUES (2,'profile');
INSERT INTO `scopes` (`client_id`, `scopes`) VALUES (2,'email');
INSERT INTO `scopes` (`client_id`, `scopes`) VALUES (13,'openid');
INSERT INTO `scopes` (`client_id`, `scopes`) VALUES (13,'profile');
/*!40000 ALTER TABLE `scopes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `disabled` bit(1) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`account_expired`, `account_locked`, `credentials_expired`, `disabled`, `id`, `password`, `username`) VALUES (_binary '\0',_binary '\0',_binary '\0',_binary '\0',1,'$2a$10$8ZZhFJuw2Ctbl2HQDv8nReENI/buP1VpQ92d8db4M542Te1gyyP1y','user@mail.com');
INSERT INTO `users` (`account_expired`, `account_locked`, `credentials_expired`, `disabled`, `id`, `password`, `username`) VALUES (_binary '\0',_binary '\0',_binary '\0',_binary '\0',2,'$2a$10$La1GsRihAnghAIlG49F2jODqhp4TOC5nYG/pbqg3dO/faMTtPuLXe','admin@mail.com');
INSERT INTO `users` (`account_expired`, `account_locked`, `credentials_expired`, `disabled`, `id`, `password`, `username`) VALUES (_binary '\0',_binary '\0',_binary '\0',_binary '\0',3,'$2a$10$byyHpgsMRmaIiR6mV.B22uc6zIsMmYtY3XQRirK5v1cmCNQhWm/HK','any@mail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`role_id`, `user_id`) VALUES (2,1);
INSERT INTO `users_roles` (`role_id`, `user_id`) VALUES (1,2);
INSERT INTO `users_roles` (`role_id`, `user_id`) VALUES (2,3);
INSERT INTO `users_roles` (`role_id`, `user_id`) VALUES (1,3);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-02  8:27:39
