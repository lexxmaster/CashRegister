package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class GoodsUpdateAmount implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Warehouse warehouse = (Warehouse) session.getAttribute(Attributes.WAREHOUSE);

        long goods_id = Long.parseLong(req.getParameter(Parameters.GOODS_ID));
        double amount = Double.parseDouble(req.getParameter(Parameters.GOODS_AMOUNT));

        GoodsDAO goodsDAO = new GoodsDAO();
        goodsDAO.setAvailableAmount(warehouse.getId(), goods_id, amount);

        return new CommandResult(req.getHeader("referer"), true);
    }
}
