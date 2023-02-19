package com.cashregister.controller;

import com.cashregister.controller.constants.Actions;
import com.cashregister.model.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccessRulesTest {
    @Test
    public void execute() {
        boolean result;
        String action;
        Role role;

        role = Role.CASHIER;

        action = Actions.HOME;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.LOGOUT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.CHANGE_LANGUAGE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.CHECKOUT_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_CLOSE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_OPEN;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_LIST;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_GOODS_ADD;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_GOODS_AMOUNT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_GOODS_DELETE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
        action = Actions.X_REPORT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
        action = Actions.Z_REPORT_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
        action = Actions.Z_REPORT_VIEW;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);

        role = Role.SENIOR_CASHIER;

        action = Actions.HOME;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.LOGOUT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.CHANGE_LANGUAGE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.CHECKOUT_CLOSE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_LIST;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_OPEN;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.LOGOUT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_GOODS_DELETE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.X_REPORT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.Z_REPORT_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.Z_REPORT_VIEW;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.CHECKOUT_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
        action = Actions.ORDER_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);

        role = Role.COMMODITY_EXPERT;

        action = Actions.HOME;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.LOGOUT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.CHANGE_LANGUAGE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.GOODS_LIST;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.GOODS_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.GOODS_UPDATE_PRICE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.GOODS_UPDATE_AMOUNT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(true, result);
        action = Actions.ORDER_GOODS_DELETE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
        action = Actions.X_REPORT;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
        action = Actions.Z_REPORT_CREATE;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
        action = Actions.Z_REPORT_VIEW;
        result = AccessRules.checkAccess(role, action);
        Assertions.assertEquals(false, result);
    }
}
