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
import com.cashregister.model.entity.GoodsAmount;
import com.cashregister.model.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

public class OrderGoodsAmount implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long goods_id = Long.parseLong(req.getParameter(Parameters.GOODS_ID));
        double amount = Double.parseDouble(req.getParameter(Parameters.GOODS_SET_AMOUNT));

        Order order = (Order) session.getAttribute(Attributes.ORDER);
        Map<Goods, GoodsAmount> goodsList =  order.getGoodsList();

        GoodsDAO goodsDAO = new GoodsDAO();
        Goods goods = goodsDAO.findById(goods_id).orElse(null);

        GoodsAmount goodsAmount = goodsList.get(goods);
        goodsAmount.setAmount(amount);

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateOrderGoods(order, goods);

        String path = Paths.CONTROLLER + Actions.ORDER_OPEN + "&" + Parameters.ORDER_ID + "=" + order.getId();
        return new CommandResult(path, true);
    }
}
