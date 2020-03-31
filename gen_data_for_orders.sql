DELIMITER $$
CREATE PROCEDURE generate_data()
BEGIN
  DECLARE i INT DEFAULT 0;
  WHILE i < 9999999999999 DO
    replace INTO orders ( order_id, user_id, eval_set, order_number, order_dow, order_hour_of_day, days_since_prior_order) VALUES (rand(), round(rand() * POWER(10, (FLOOR(RAND()*(18-1+1))+1))) , rand(), rand(), rand(), rand(), rand());
    SET i = i + 1;
  END WHILE;
END$$
DELIMITER ;

CALL generate_data();

