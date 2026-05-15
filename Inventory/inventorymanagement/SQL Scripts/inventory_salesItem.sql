USE `inventory_directory`;

CREATE TABLE `sales_item` (
    `id` int(11) AUTO_INCREMENT PRIMARY KEY,
    `sales_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `quantity` INT DEFAULT NULL,
    `unit_price` DECIMAL(10,2) DEFAULT NULL,
    `total_price` DECIMAL(10,2) DEFAULT NULL,

    FOREIGN KEY (`sales_id`) REFERENCES sales(`id`),
    FOREIGN KEY (`product_id`) REFERENCES product(`id`)
    
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;