package com.cashregister.controller.commands.orders.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.dao.OrderDAO;
import com.cashregister.model.entity.Goods;
import com.cashregister.model.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class OrderGoodsAdd implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchValue = req.getParameter(Parameters.SEARCH_GOODS);
        double amount = 0.0;

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute(Attributes.ORDER);

        String path = Paths.CONTROLLER + Actions.ORDER_OPEN + "&" + Parameters.ORDER_ID + "=" + order.getId();

        if (nonNull(searchValue)) {
            GoodsDAO goodsDAO = new GoodsDAO();
            Goods goods = null;
            if (searchValue.matches("\\d+")){
                goods = goodsDAO.findByScancode(searchValue).orElse(null);
            } else{
                goods = goodsDAO.findByName(searchValue).orElse(null);
            }

            if (nonNull(goods)) {
                boolean newRow = order.addGoods(goods, amount);
                OrderDAO orderDAO = new OrderDAO();
                if (newRow) {
                    orderDAO.createOrderGoods(order, goods);
                } else {
                    orderDAO.updateOrderGoods(order, goods);
                }
            }
        }

        return new CommandResult(path, true);
    }
}
