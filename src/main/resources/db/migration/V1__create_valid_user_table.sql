CREATE TABLE `validate_user` (
  `is_valid` bit(1) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `validated_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;