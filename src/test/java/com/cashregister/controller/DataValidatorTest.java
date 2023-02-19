package com.cashregister.controller;

import com.cashregister.model.entity.Goods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataValidatorTest {
    @Test
    public void execute(){
        Assertions.assertEquals(true, DataValidator.validateLogin("SuperUser"));
        Assertions.assertEquals(false, DataValidator.validateLogin("11"));
        Assertions.assertEquals(true, DataValidator.validatePassword("Qwe1234"));
        Assertions.assertEquals(false, DataValidator.validatePassword("1"));
        Goods goods = new Goods("test", "001");
        goods.setWeight(false);
        Assertions.assertEquals(true, DataValidator.validateAmount(1.0 ,goods));
        Assertions.assertEquals(false, DataValidator.validateAmount(1.1 ,goods));
        goods.setWeight(true);
        Assertions.assertEquals(true, DataValidator.validateAmount(1.1 ,goods));
        Assertions.assertEquals(true, DataValidator.validatePrice(1.1));
        Assertions.assertEquals(false, DataValidator.validatePrice(-1.1));
        Assertions.assertEquals(false, DataValidator.validatePrice(10000000.1));
        Assertions.assertEquals(true, DataValidator.validateScancode("0001"));
        Assertions.assertEquals(false, DataValidator.validateScancode("0T01"));
        Assertions.assertEquals(false, DataValidator.validateScancode("01"));
        Assertions.assertEquals(true, DataValidator.validateGoodsName("Bread"));
        Assertions.assertEquals(false, DataValidator.validateGoodsName("s"));

    }
}
