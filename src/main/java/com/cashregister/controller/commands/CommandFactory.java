package com.cashregister.controller.commands;

import com.cashregister.controller.commands.checkoutshift.*;
import com.cashregister.controller.commands.common.*;
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
import com.cashregister.controller.commands.users.UserOpen;
import com.cashregister.controller.commands.users.UserSave;
import com.cashregister.controller.commands.users.UserList;
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
            case Actions.CHECKOUT_LIST:
                command = new CheckoutList();
                break;
            case Actions.CHECKOUT_OPEN:
                command = new CheckoutOpen();
                break;
            case Actions.CHECKOUT_CREATE:
                command = new CheckoutCreate();
                break;
            case Actions.CHECKOUT_CLOSE:
                command = new CheckoutClose();
                break;
            case Actions.CHECKOUT_EXIT:
                command = new CheckoutExit();
                break;
            case Actions.ORDER_LIST:
                command = new OrderList();
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
            case Actions.GOODS_UPDATE_AMOUNT:
                command = new GoodsUpdateAmount();
                break;
            case Actions.GOODS_UPDATE_PRICE:
                command = new GoodsUpdatePrice();
                break;
            case Actions.X_REPORT:
                command = new XReportView();
                break;
            case Actions.Z_REPORT_CREATE:
                command = new ZReportCreate();
                break;
            case Actions.Z_REPORT_VIEW:
                command = new ZReportView();
                break;
            case Actions.USER_LIST:
                command = new UserList();
                break;
            case Actions.USER_OPEN:
                command = new UserOpen();
                break;
            case Actions.USER_CREATE:
                command = new UserCreate();
                break;
            case Actions.USER_SAVE:
                command = new UserSave();
                break;
            default:
                command = new EmptyAction();
        }
    }

    public ICommand getCommand(){
        return command;
    }
}
