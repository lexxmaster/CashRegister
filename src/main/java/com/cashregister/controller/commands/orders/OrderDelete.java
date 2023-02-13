package com.cashregister.controller.commands.orders;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.Goods;
import com.cashregister.model.entity.GoodsAmount;
import com.cashregister.model.entity.Order;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OrderDelete implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final long order_id = Long.parseLong(req.getParameter(Parameters.ORDER_ID));
        HttpSession session = req.getSession();
        Warehouse warehouse = (Warehouse) session.getAttribute(Attributes.WAREHOUSE);

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.findById(order_id).orElseGet(null);

        GoodsDAO goodsDAO = new GoodsDAO();

        Map<Goods, GoodsAmount> goodsList = order.getGoodsList();
        double availableAmount = 0;
        for (Goods key: goodsList.keySet()) {
            availableAmount = goodsDAO.getAvailableAmount(warehouse.getId(), key.getId());
            availableAmount += goodsList.get(key).getAmount();
            goodsDAO.setAvailableAmount(warehouse.getId(), key.getId(), availableAmount);
        }
        orderDAO.delete(order);

        return new CommandResult(Paths.CONTROLLER + Actions.ORDER_LIST, true);
    }
}
