-- Adicionar coluna `book_id` na tabela `users`
ALTER TABLE `users`
ADD COLUMN `book_id` bigint;

-- Criar chave estrangeira para a tabela `books` na coluna `book_id` da tabela `users`
ALTER TABLE `users`
ADD CONSTRAINT `FK_users_books`
FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);

-- Criar tabela `users_books` para a relação many-to-many
CREATE TABLE IF NOT EXISTS `users_books` (
  `user_id` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `book_id`),
  CONSTRAINT `FK_users_books_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_users_books_books` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
