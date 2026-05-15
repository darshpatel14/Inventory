USE `inventory_directory`;

CREATE TABLE `product_supplier` (
    `product_id` INT,
    `supplier_id` INT,
    PRIMARY KEY(`product_id`, `supplier_id`),
    FOREIGN KEY (`product_id`) REFERENCES product(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`supplier_id`) REFERENCES supplier(`supplier_id`)
);