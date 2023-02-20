package com.cashregister.controller.model.entity.orders;

import com.cashregister.model.entity.GoodsAmount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GoodsAmountTest {
    GoodsAmount goodsAmount;
    @BeforeEach
    public void setUp(){
        goodsAmount = new GoodsAmount(1.0, 2.5);
    }
    @Test
    public void execute() {
        Assertions.assertEquals(goodsAmount.getTotal(), 2.5);
        goodsAmount.setAmount(2.0);
        Assertions.assertEquals(goodsAmount.getTotal(), 5.0);
        goodsAmount.setPrice(5.0);
        Assertions.assertEquals(goodsAmount.getTotal(), 10.0);
    }
    @AfterEach
    public void tearDown(){
        goodsAmount = null;
    }
}
