CREATE TABLE IF NOT EXISTS `course2` (
  `code` varchar(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `weekday` int DEFAULT NULL,
  `start_time` int DEFAULT NULL,
  `end_time` int DEFAULT NULL,
  `credits` int DEFAULT NULL,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `student2` (
  `id` varchar(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `selected_code` varchar(100) DEFAULT NULL,
  `credits` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);