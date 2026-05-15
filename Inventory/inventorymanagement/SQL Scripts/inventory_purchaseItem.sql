USE `inventory_directory`;

CREATE TABLE `purchase_item` (
    `id` int(11) AUTO_INCREMENT PRIMARY KEY,
    `purchase_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `quantity` INT DEFAULT NULL,
    `unit_cost` DECIMAL(10,2) DEFAULT NULL,
    `total_cost` DECIMAL(10,2) DEFAULT NULL,

    FOREIGN KEY (`purchase_id`) REFERENCES purchase(`id`),
    FOREIGN KEY (`product_id`) REFERENCES product(`id`)
    
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;