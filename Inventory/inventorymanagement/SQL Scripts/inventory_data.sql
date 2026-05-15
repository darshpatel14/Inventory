USE inventory_directory;

DROP TABLE IF EXISTS inventory;

CREATE TABLE inventory (
    
    id INT PRIMARY KEY AUTO_INCREMENT,

    product_id INT NOT NULL UNIQUE,

    stock_in INT DEFAULT 0,

    stock_out INT DEFAULT 0,

    current_stock INT DEFAULT 0,

    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_inventory_product
    FOREIGN KEY (product_id)
    REFERENCES product(id)
    ON DELETE CASCADE
);