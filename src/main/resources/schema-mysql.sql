CREATE TABLE IF NOT EXISTS `course` (
  `code` varchar(10) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `weekday` int DEFAULT NULL,
  `start_time` int DEFAULT NULL,
  `end_time` int DEFAULT NULL,
  `credits` int DEFAULT NULL,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `student` (
  `id` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `selected_class` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
