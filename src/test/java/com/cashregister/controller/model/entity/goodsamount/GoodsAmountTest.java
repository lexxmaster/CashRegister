package com.cashregister.controller.model.entity.goodsamount;

import com.cashregister.model.entity.GoodsAmount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoodsAmountTest {
    @Test
    public void execute(){
        GoodsAmount goodsAmount = new GoodsAmount(1.0, 10.0);
        Assertions.assertEquals(goodsAmount.getTotal(), 10.0);
        goodsAmount.setAmount(2.0);
        Assertions.assertEquals(goodsAmount.getTotal(), 20.0);
        goodsAmount.setPrice(20.0);
        Assertions.assertEquals(goodsAmount.getTotal(), 40.0);
    }
}
