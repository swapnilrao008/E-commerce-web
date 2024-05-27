ALTER TABLE product_categeory
    ADD `desc` VARCHAR(255) NULL;

ALTER TABLE product_categeory
    ADD pid INT NULL;

ALTER TABLE product_categeory
    MODIFY pid INT NOT NULL;