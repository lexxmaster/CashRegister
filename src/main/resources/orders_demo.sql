INSERT INTO `cash_register`.`checkout_shift` (`id`, `checkout_date`, `closed`, `warehouse_id`, `user_id`) VALUES ('1', '22.12.23 12:32:03', '0', '1', '1');

INSERT INTO `cash_register`.`orders` (`id`, `checkout_id`, `warehouses_id`, `order_date`, `closed`, `user_id`) VALUES ('1', '1','1', '22.12.23 12:32:03', '1', '1');
INSERT INTO `cash_register`.`orders` (`id`, `checkout_id`, `warehouses_id`, `order_date`, `closed`, `user_id`) VALUES ('2', '1', '1', '22.12.25 09:13:31', '0', '1');

INSERT INTO `cash_register`.`order_goods` (`order_id`, `good_id`, `amount`, `price`, `total`) VALUES ('1', '1', '2', '5', '10');
INSERT INTO `cash_register`.`order_goods` (`order_id`, `good_id`, `amount`, `price`, `total`) VALUES ('1', '5', '1', '4.1', '4.1');
INSERT INTO `cash_register`.`order_goods` (`order_id`, `good_id`, `amount`, `price`, `total`) VALUES ('2', '2', '1', '10', '10');
