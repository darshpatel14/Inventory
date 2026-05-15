CREATE TABLE `supplier` (
    `supplier_id` INT NOT NULL AUTO_INCREMENT,
    `supplier_name` VARCHAR(100) NOT NULL,
    `contact_person` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(15) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `gst_no` VARCHAR(50) NOT NULL,
    `state` VARCHAR(50) NOT NULL,
    `city` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`supplier_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;