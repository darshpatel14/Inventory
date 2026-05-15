USE `inventory_directory`;

CREATE TABLE `purchase` (
    `id` int(11) AUTO_INCREMENT PRIMARY KEY,
    `supplier_id` INT NOT NULL,
    `purchase_date` DATE,
    `total_amount` DECIMAL(10,2),
    `purchase_status` VARCHAR(50),

    FOREIGN KEY (`supplier_id`) REFERENCES supplier(`supplier_id`)
    
    
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;