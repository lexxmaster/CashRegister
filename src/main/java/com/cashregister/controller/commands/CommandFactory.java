package com.cashregister.controller.commands;

import com.cashregister.controller.commands.checkoutshift.CheckoutClose;
import com.cashregister.controller.commands.checkoutshift.CheckoutCreate;
import com.cashregister.controller.commands.common.*;
import com.cashregister.controller.commands.goods.GoodsCreate;
import com.cashregister.controller.commands.goods.GoodsList;
import com.cashregister.controller.commands.orders.*;
import com.cashregister.controller.commands.orders.goods.OrderGoodsAdd;
import com.cashregister.controller.commands.orders.goods.OrderGoodsAmount;
import com.cashregister.controller.commands.orders.goods.OrderGoodsDelete;
import com.cashregister.controller.constants.Actions;

public class CommandFactory {
    private ICommand command;

    public CommandFactory(String action) {
        switch (action) {
            case Actions.LOGIN:
                command = new Login();
                break;
            case Actions.LOGOUT:
                command = new Logout();
                break;
            case Actions.CHANGE_LANGUAGE:
                command = new ChangeLanguage();
                break;
            case Actions.HOME:
                command = new Home();
                break;
            case Actions.CHECKOUT_CREATE:
                command = new CheckoutCreate();
                break;
            case Actions.CHECKOUT_CLOSE:
                command = new CheckoutClose();
                break;
            case Actions.ORDER_LIST:
                command = new OrdersList();
                break;
            case Actions.ORDER_OPEN:
                command = new OrderOpen();
                break;
            case Actions.ORDER_CREATE:
                command = new OrderCreate();
                break;
            case Actions.ORDER_CLOSE:
                command = new OrderClose();
                break;
            case Actions.ORDER_DELETE:
                command = new OrderDelete();
                break;
            case Actions.ORDER_GOODS_ADD:
                command = new OrderGoodsAdd();
                break;
            case Actions.ORDER_GOODS_AMOUNT:
                command = new OrderGoodsAmount();
                break;
            case Actions.ORDER_GOODS_DELETE:
                command = new OrderGoodsDelete();
                break;
            case Actions.GOODS_LIST:
                command = new GoodsList();
                break;
            case Actions.GOODS_CREATE:
                command = new GoodsCreate();
                break;
            default:
                command = new EmptyAction();
        }
    }

    public ICommand getCommand(){
        return command;
    }
}
