package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Actions;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.entity.Goods;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoodsCreate implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(Parameters.GOODS_NAME);
        String scancode = req.getParameter(Parameters.GOODS_SCANCODE);
        double price = Double.parseDouble(req.getParameter(Parameters.GOODS_PRICE));
        boolean weight = req.getParameter(Parameters.GOODS_WEIGHT).equals("on");

        GoodsDAO goodsDAO = new GoodsDAO();
        Goods goods = goodsDAO.findByScancode(scancode).orElse(null);
        if (goods == null) {
            goods = goodsDAO.findByName(name).orElse(null);
        }
        if (goods == null) {
            goods = new Goods(name, scancode);
            goods.setPrice(price);
            goods.setWeight(weight);

            goodsDAO.create(goods);
        }

        return new CommandResult(Paths.CONTROLLER + Actions.GOODS_LIST, true);
    }
}
