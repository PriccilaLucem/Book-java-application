CREATE TABLE IF NOT EXISTS `user_book_list` (
  `book_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`book_id`,`user_id`),
  KEY `FK38xjqxk5mk97n8mby1q9m44x` (`user_id`),
  CONSTRAINT `FK38xjqxk5mk97n8mby1q9m44x` FOREIGN KEY (`user_id`) REFERENCES `books` (`id`),
  CONSTRAINT `FKfuqusdgdmw3b3d6qgk0pe1ipo` FOREIGN KEY (`book_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;