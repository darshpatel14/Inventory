USE `inventory_directory`;

DROP TABLE IF EXISTS `sales`;

CREATE TABLE `sales` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`customer_id` int(11) NOT NULL,
    `sales_date` DATE DEFAULT NULL,
    `amount` DECIMAL(10,2) DEFAULT NULL,
    `payment_mode` varchar(50) DEFAULT NULL,
    `created_by` int(11) NOT NULL,
    primary key(`id`),
 
	CONSTRAINT `fk_customer` FOREIGN KEY (`customer_id`) REFERENCES customer(`id`),

    CONSTRAINT `fk_user` FOREIGN KEY (`created_by`) REFERENCES users(`user_id`)
    
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
    