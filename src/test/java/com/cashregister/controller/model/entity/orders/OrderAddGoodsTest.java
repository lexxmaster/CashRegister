package com.cashregister.controller.model.entity.orders;

import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Goods;
import com.cashregister.model.entity.Order;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class OrderAddGoodsTest {
    Order order;
    Warehouse warehouse;
    CheckoutShift checkoutShift;
    Goods goods1;
    Goods goods2;
    double amount1;
    double amount2;
    double availableAmount1;
    double availableAmount2;

    @BeforeEach
    public void setUp(){
        warehouse = new Warehouse(1, "test");
        checkoutShift = new CheckoutShift(warehouse);
        order = new Order(warehouse, checkoutShift);
        goods1 = new Goods(1, "g1");
        goods1.setPrice(1.0);
        goods2 = new Goods(2, "g2");
        goods2.setPrice(2.0);
        amount1 = 2;
        amount2 = 3;
        availableAmount1 = 10;
        availableAmount2 = 2;
    }

    @Test
    public void execute() {
        order.addGoods(goods1, amount1, availableAmount1);
        Assertions.assertEquals(1, order.getGoodsList().size());
        Assertions.assertEquals(1, order.getGoodsList().get(goods1).getAmount());
        order.addGoods(goods2, amount2, availableAmount2);
        Assertions.assertEquals(2, order.getGoodsList().size());
        order.addGoods(goods1, amount1, availableAmount1);
        Assertions.assertEquals(2, order.getGoodsList().size());
        Assertions.assertEquals(2, order.getGoodsList().get(goods1).getAmount());
        order.updateTotal();
        Assertions.assertEquals(4.0, order.getTotal());
    }

    @AfterEach
    public void tearDown(){
        order = null;
        warehouse = null;
        checkoutShift = null;
        goods1 = null;
        goods2 = null;
    }
}
