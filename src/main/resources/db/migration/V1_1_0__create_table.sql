
CREATE TABLE `agenda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pauta_id` int DEFAULT NULL,
  `opening` datetime NOT NULL,
  `time` int DEFAULT '1',
  `state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pauta_id_UNIQUE` (`pauta_id`),
  CONSTRAINT `fk_agenda_id` FOREIGN KEY (`pauta_id`) REFERENCES `agenda` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `vote` (
  `id` int NOT NULL AUTO_INCREMENT,
  `agree` varchar(1) DEFAULT NULL,
  `member_id` int NOT NULL,
  `date` datetime DEFAULT NULL,
  `session_id` int DEFAULT NULL,
  `is_counted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_session_id_idx` (`session_id`),
  KEY `fk_member_id` (`member_id`),
  CONSTRAINT `fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `fk_session_id` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vote_couting` (
  `session_id` int NOT NULL,
  `yes_votes` int DEFAULT NULL,
  `no_votes` int DEFAULT NULL,
  `percent_yes_votes` float DEFAULT NULL,
  `percent_no_votes` float DEFAULT NULL,
  `total` int DEFAULT NULL,
  `winner` varchar(45) DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_id_UNIQUE` (`session_id`),
  CONSTRAINT `fk_session__vc_id` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
