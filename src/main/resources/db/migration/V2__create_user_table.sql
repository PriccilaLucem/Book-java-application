CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `validate_user_id` bigint NOT NULL,
  `email` varchar(100) NOT NULL,
  `nick_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ldhnyl1qj4b8vrukghec2fsib` (`validate_user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  CONSTRAINT `FKsem6rtdtyeyntqw07vvx62f9e` FOREIGN KEY (`validate_user_id`) REFERENCES `validate_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;