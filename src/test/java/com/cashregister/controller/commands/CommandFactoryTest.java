package com.cashregister.controller.commands;

import com.cashregister.controller.commands.checkoutshift.CheckoutClose;
import com.cashregister.controller.commands.checkoutshift.CheckoutCreate;
import com.cashregister.controller.commands.checkoutshift.CheckoutList;
import com.cashregister.controller.commands.checkoutshift.CheckoutOpen;
import com.cashregister.controller.commands.common.ChangeLanguage;
import com.cashregister.controller.commands.common.Home;
import com.cashregister.controller.commands.common.Login;
import com.cashregister.controller.commands.common.Logout;
import com.cashregister.controller.commands.goods.GoodsCreate;
import com.cashregister.controller.commands.goods.GoodsList;
import com.cashregister.controller.commands.goods.GoodsUpdateAmount;
import com.cashregister.controller.commands.goods.GoodsUpdatePrice;
import com.cashregister.controller.commands.orders.*;
import com.cashregister.controller.commands.orders.goods.OrderGoodsAdd;
import com.cashregister.controller.commands.orders.goods.OrderGoodsAmount;
import com.cashregister.controller.commands.orders.goods.OrderGoodsDelete;
import com.cashregister.controller.commands.report.XReportView;
import com.cashregister.controller.commands.report.ZReportCreate;
import com.cashregister.controller.commands.report.ZReportView;
import com.cashregister.controller.commands.users.UserCreate;
import com.cashregister.controller.commands.users.UserList;
import com.cashregister.controller.commands.users.UserOpen;
import com.cashregister.controller.commands.users.UserSave;
import com.cashregister.controller.constants.Actions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandFactoryTest {
    CommandFactory commandFactory;

    @BeforeEach
    public void setUp(){

    }
    @Test
    public void execute() {
        commandFactory = new CommandFactory(Actions.CHECKOUT_LIST);
        Assertions.assertInstanceOf(CheckoutList.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.CHECKOUT_CLOSE);
        Assertions.assertInstanceOf(CheckoutClose.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.CHECKOUT_CREATE);
        Assertions.assertInstanceOf(CheckoutCreate.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.CHECKOUT_OPEN);
        Assertions.assertInstanceOf(CheckoutOpen.class, commandFactory.getCommand());

        commandFactory = new CommandFactory(Actions.CHANGE_LANGUAGE);
        Assertions.assertInstanceOf(ChangeLanguage.class, commandFactory.getCommand());

        commandFactory = new CommandFactory(Actions.GOODS_LIST);
        Assertions.assertInstanceOf(GoodsList.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.GOODS_CREATE);
        Assertions.assertInstanceOf(GoodsCreate.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.GOODS_UPDATE_AMOUNT);
        Assertions.assertInstanceOf(GoodsUpdateAmount.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.GOODS_UPDATE_PRICE);
        Assertions.assertInstanceOf(GoodsUpdatePrice.class, commandFactory.getCommand());

        commandFactory = new CommandFactory(Actions.HOME);
        Assertions.assertInstanceOf(Home.class, commandFactory.getCommand());

        commandFactory = new CommandFactory(Actions.LOGIN);
        Assertions.assertInstanceOf(Login.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.LOGOUT);
        Assertions.assertInstanceOf(Logout.class, commandFactory.getCommand());

        commandFactory = new CommandFactory(Actions.ORDER_LIST);
        Assertions.assertInstanceOf(OrderList.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.ORDER_OPEN);
        Assertions.assertInstanceOf(OrderOpen.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.ORDER_CLOSE);
        Assertions.assertInstanceOf(OrderClose.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.ORDER_CREATE);
        Assertions.assertInstanceOf(OrderCreate.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.ORDER_DELETE);
        Assertions.assertInstanceOf(OrderDelete.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.ORDER_GOODS_ADD);
        Assertions.assertInstanceOf(OrderGoodsAdd.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.ORDER_GOODS_AMOUNT);
        Assertions.assertInstanceOf(OrderGoodsAmount.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.ORDER_GOODS_DELETE);
        Assertions.assertInstanceOf(OrderGoodsDelete.class, commandFactory.getCommand());

        commandFactory = new CommandFactory(Actions.USER_LIST);
        Assertions.assertInstanceOf(UserList.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.USER_CREATE);
        Assertions.assertInstanceOf(UserCreate.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.USER_OPEN);
        Assertions.assertInstanceOf(UserOpen.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.USER_SAVE);
        Assertions.assertInstanceOf(UserSave.class, commandFactory.getCommand());

        commandFactory = new CommandFactory(Actions.X_REPORT);
        Assertions.assertInstanceOf(XReportView.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.Z_REPORT_VIEW);
        Assertions.assertInstanceOf(ZReportView.class, commandFactory.getCommand());
        commandFactory = new CommandFactory(Actions.Z_REPORT_CREATE);
        Assertions.assertInstanceOf(ZReportCreate.class, commandFactory.getCommand());
    }
    @AfterEach
    public void tearDown(){
        commandFactory = null;
    }
}
