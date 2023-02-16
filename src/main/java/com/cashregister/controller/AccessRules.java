package com.cashregister.controller;

import com.cashregister.controller.constants.Actions;
import com.cashregister.model.entity.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessRules {
    private static Map<Role, List<String>> availableCommands;
    static {
        availableCommands = new HashMap<>();

        List<String> commandsCashier = new ArrayList<>();
        List<String> commandsSeniorCashier = new ArrayList<>();
        List<String> commandsCommodity = new ArrayList<>();
        List<String> commandsAdmin = new ArrayList<>();

        commandsCashier.add(Actions.HOME);
        commandsCashier.add(Actions.LOGOUT);
        commandsCashier.add(Actions.CHANGE_LANGUAGE);
        commandsCashier.add(Actions.CHECKOUT_CREATE);
        commandsCashier.add(Actions.ORDER_CREATE);
        commandsCashier.add(Actions.ORDER_CLOSE);
        commandsCashier.add(Actions.ORDER_OPEN);
        commandsCashier.add(Actions.ORDER_LIST);
        commandsCashier.add(Actions.ORDER_GOODS_ADD);
        commandsCashier.add(Actions.ORDER_GOODS_AMOUNT);

        commandsSeniorCashier.add(Actions.HOME);
        commandsSeniorCashier.add(Actions.LOGOUT);
        commandsSeniorCashier.add(Actions.CHANGE_LANGUAGE);
        commandsSeniorCashier.add(Actions.CHECKOUT_CLOSE);
        commandsSeniorCashier.add(Actions.ORDER_LIST);
        commandsSeniorCashier.add(Actions.ORDER_OPEN);
        commandsSeniorCashier.add(Actions.LOGOUT);
        commandsSeniorCashier.add(Actions.ORDER_GOODS_DELETE);
        commandsSeniorCashier.add(Actions.X_REPORT);
        commandsSeniorCashier.add(Actions.Z_REPORT_CREATE);
        commandsSeniorCashier.add(Actions.Z_REPORT_VIEW);

        commandsCommodity.add(Actions.HOME);
        commandsCommodity.add(Actions.LOGOUT);
        commandsCommodity.add(Actions.CHANGE_LANGUAGE);
        commandsCommodity.add(Actions.GOODS_LIST);
        commandsCommodity.add(Actions.GOODS_CREATE);
        commandsCommodity.add(Actions.GOODS_UPDATE_PRICE);
        commandsCommodity.add(Actions.GOODS_UPDATE_AMOUNT);

        commandsAdmin.add(Actions.HOME);
        commandsAdmin.add(Actions.LOGOUT);
        commandsAdmin.add(Actions.CHANGE_LANGUAGE);
        commandsAdmin.add(Actions.USER_LIST);
        commandsAdmin.add(Actions.USER_CREATE);
        commandsAdmin.add(Actions.USER_SAVE);
        commandsAdmin.add(Actions.USER_OPEN);

        availableCommands.put(Role.CASHIER, commandsCashier);
        availableCommands.put(Role.SENIOR_CASHIER, commandsSeniorCashier);
        availableCommands.put(Role.COMMODITY_EXPERT, commandsCommodity);
        availableCommands.put(Role.ADMIN, commandsAdmin);
    }

    public static boolean checkAccess(Role role, String command){
        if (command.equals("login") || command.equals("logout")) {
            return true;
        }
        return availableCommands.get(role).stream().anyMatch(cmd -> cmd.equals(command));
    }
}
