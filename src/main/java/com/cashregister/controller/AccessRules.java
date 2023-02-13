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
        commandsCashier.add(Actions.CHECKOUT_CREATE);
        commandsCashier.add(Actions.CHECKOUT_CLOSE);
        commandsCashier.add(Actions.ORDER_CREATE);
        commandsCashier.add(Actions.ORDER_CLOSE);
        commandsCashier.add(Actions.ORDER_CLOSE);

        commandsSeniorCashier.add(Actions.HOME);

        commandsCommodity.add(Actions.HOME);
        commandsCommodity.add(Actions.GOODS_LIST);

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
