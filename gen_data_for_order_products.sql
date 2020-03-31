DELIMITER $$
CREATE PROCEDURE generate_data_for_order_products()
BEGIN
  DECLARE i INT DEFAULT 0;
  WHILE i < 9999999999999 DO
    replace INTO order_products ( order_id, product_id, add_to_cart_order, reordered) VALUES (round(rand()*POWER(10, (FLOOR(RAND()*(18-1+1))+1))) , round(rand() * POWER(10, (FLOOR(RAND()*(18-1+1))+1))), round(rand()*POWER(10, (FLOOR(RAND()*(18-1+1))+1))), round(rand()*POWER(10, (FLOOR(RAND()*(18-1+1))+1))));
    SET i = i + 1;
  END WHILE;
END$$
DELIMITER ;

CALL generate_data_for_order_products();

