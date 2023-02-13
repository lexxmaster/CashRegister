package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.entity.Goods;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class GoodsUpdatePrice implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long goods_id = Long.parseLong(req.getParameter(Parameters.GOODS_ID));
        double price = Double.parseDouble(req.getParameter(Parameters.GOODS_PRICE));

        GoodsDAO goodsDAO = new GoodsDAO();
        Goods goods = goodsDAO.findById(goods_id).orElse(null);
        if (nonNull(goods)) {
            goods.setPrice(price);
            goodsDAO.update(goods);
        }

        return new CommandResult(req.getHeader("referer"), true);
    }
}
