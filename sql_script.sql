CREATE TABLE parking_lot (
    ID varchar(255),
    CELLID varchar(255),
    NAME varchar(255),
    DAY varchar(255),
    HOUR varchar(255),
	PAY varchar(255),
	PAYCASH varchar(255),
	MEMO varchar(255),
	ROADID varchar(255),
	CELLSTATUS varchar(255),
	ISNOWCASH varchar(255),
	ParkingStatus varchar(255),
	lat varchar(255),
	lon varchar(255)
);

CREATE TABLE web_access(
	userId varchar(255),
	online varchar(255),
	city varchar(255),
	loginTime varchar(255),
	phoneName varchar(255),
	processTime varchar(255)
);

CREATE TABLE orders(
	order_id varchar(255),
	user_id varchar(255),
	eval_set varchar(255),
	order_number varchar(255),
	order_dow varchar(255),
	order_hour_of_day varchar(255),
	days_since_prior_order varchar(255)
);
load data local infile '/home/ec2-user/instacart-market-basket-analysis/orders.csv' into table orders fields terminated by ',' lines terminated by '\n' ignore 1 rows ( order_id,user_id,eval_set,order_number,order_dow,order_hour_of_day,days_since_prior_order);
-- used for generate data for table orders
drop procedure generate_data;
source /home/ec2-user/instacart-market-basket-analysis/gen_data_for_orders.sql;


CREATE TABLE order_products(
	order_id varchar(255),
	product_id varchar(255),
	add_to_cart_order varchar(255),
	reordered varchar(255)
);
load data local infile '/home/ec2-user/instacart-market-basket-analysis/order_products__prior.csv' into table order_products fields terminated by ',' lines terminated by '\n' ignore 1 rows (order_id,product_id,add_to_cart_order,reordered);
load data local infile '/home/ec2-user/instacart-market-basket-analysis/order_products__train.csv' into table order_products fields terminated by ',' lines terminated by '\n' ignore 1 rows (order_id,product_id,add_to_cart_order,reordered);
-- used for generate data for table orders
drop procedure generate_data_for_order_products;
source /home/ec2-user/instacart-market-basket-analysis/gen_data_for_order_products.sql;




-- join query across three table orders
select * from order_products op join orders o on op.order_id=o.order_id join web_access wb on o.user_id=wb.userId limit 1;
