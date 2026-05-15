USE `inventory_directory`;

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`customer_name` varchar(50) DEFAULT NULL,
    `phone` varchar(20) DEFAULT NULL,
    `email` varchar(20) DEFAULT NULL,
    `state` varchar(50) DEFAULT NULL,
    `city` varchar(50) DEFAULT NULL,
    primary key(`id`)
    
    )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;