package com.cashregister.model.dao;

public class TableFields {
    public static final String GOODS_NAME = "goods_name";
    public static final String GOODS_WEIGHT = "weight";
    public static final String GOODS_SCANCODE = "scancode";

    public static final String GOODS_AMOUNT_GOOD_ID = "good_id";
    public static final String GOODS_AMOUNT_WAREHOUSE_ID = "warehouse_id";
    public static final String GOODS_AMOUNT_AMOUNT = "amount";

    public static final String GOODS_PRICE_GOOD_ID = "good_id";
    public static final String GOODS_PRICE_PRICE = "price";

    public static final String ORDER_GOODS_ORDER_ID = "order_id";
    public static final String ORDER_GOODS_GOOD_ID = "good_id";
    public static final String ORDER_GOODS_AMOUNT = "amount";
    public static final String ORDER_GOODS_PRICE = "price";
    public static final String ORDER_GOODS_TOTAL = "total";

    public static final String ORDER_WAREHOUSES_ID = "warehouses_id";
    public static final String ORDER_CHECKOUT_ID = "checkout_id";
    public static final String ORDER_ORDER_DATE = "order_date";
    public static final String ORDER_CLOSED = "closed";
    public static final String ORDER_USER_ID = "user_id";

    public static final String ROLES_NAME = "roles_name";

    public static final String USERS_LOGIN = "login";
    public static final String USERS_PASSWD = "passwd";
    public static final String USERS_ROLE = "role";

    public static final String WAREHOUSES_NAME = "warehouses_name";

    public static final String CHECKOUT_WAREHOUSE_ID = "warehouse_id";
    public static final String CHECKOUT_DATE = "checkout_date";
    public static final String CHECKOUT_CLOSED = "closed";
    public static final String CHECKOUT_USER_ID = "user_id";

    public static final String REPORT_CHECK_AMOUNT = "check_amount";
    public static final String REPORT_SUM = "check_sum";
    public static final String REPORT_USER_ID = "user_id";
    public static final String REPORT_CHECKOUT_ID = "checkout_id";
}
