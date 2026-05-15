USE `inventory_directory`;

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `product_name` VARCHAR(100) NOT NULL,
    `product_description` VARCHAR(255),
    `price` DECIMAL(10,2) NOT NULL,
    `stock` INT NOT NULL,

    `category_id` INT,

    PRIMARY KEY (`id`),

    FOREIGN KEY (`category_id`) REFERENCES category(`category_id`)
) ENGINE=InnoDB;