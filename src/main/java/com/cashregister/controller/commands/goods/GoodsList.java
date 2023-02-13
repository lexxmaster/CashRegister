package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.entity.Goods;
import com.cashregister.model.entity.Warehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cashregister.controller.constants.Common.RECORDS_PER_PAGE;
import static java.util.Objects.nonNull;

public class GoodsList implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Warehouse warehouse = (Warehouse) session.getAttribute(Attributes.WAREHOUSE);
        int page = 1;
        int recordsPerPage = RECORDS_PER_PAGE;
        if (nonNull(req.getParameter(Parameters.PAGE))) {
            page = Integer.parseInt(req.getParameter(Parameters.PAGE));
        }
        GoodsDAO goodsDAO = new GoodsDAO();
        int numOfPages = getNumOfPages(goodsDAO, recordsPerPage);
        List<Goods> goodsList = goodsDAO.findAllByPage((page - 1) * recordsPerPage, recordsPerPage);

        Map<Goods,Double> mapGoods = new HashMap<>();
        for (Goods goods:goodsList) {
            mapGoods.put(goods, Double.valueOf(goodsDAO.getAvailableAmount(warehouse.getId(), goods.getId())));
        }

        req.setAttribute(Attributes.GOODS_LIST, mapGoods);
        req.setAttribute(Attributes.CURRENT_PAGE, page);
        req.setAttribute(Attributes.NUMBER_OF_PAGES, numOfPages);

        return new CommandResult(Paths.GOODS_LIST);
    }

    private int getNumOfPages(GoodsDAO goodsDAO, int recordsPerPage) {
        int recordsCount = goodsDAO.getRecordsCount();
        return (int) Math.ceil(recordsCount * 1.0/ recordsPerPage);
    }
}
