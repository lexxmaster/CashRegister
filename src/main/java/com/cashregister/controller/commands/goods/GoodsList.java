package com.cashregister.controller.commands.goods;

import com.cashregister.controller.commands.CommandResult;
import com.cashregister.controller.commands.ICommand;
import com.cashregister.controller.constants.Attributes;
import com.cashregister.controller.constants.Parameters;
import com.cashregister.controller.constants.Paths;
import com.cashregister.model.dao.GoodsDAO;
import com.cashregister.model.entity.Goods;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

public class GoodsList implements ICommand {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 10;
        if (nonNull(req.getParameter(Parameters.PAGE))) {
            page = Integer.parseInt(req.getParameter(Parameters.PAGE));
        }
        GoodsDAO goodsDAO = new GoodsDAO();
        int numOfPages = getNumOfPages(goodsDAO, recordsPerPage);
        List<Goods> goodsList = goodsDAO.findAllByPage((page - 1) * recordsPerPage, recordsPerPage);

        req.setAttribute(Attributes.GOODS_LIST, goodsList);
        req.setAttribute(Attributes.CURRENT_PAGE, page);
        req.setAttribute(Attributes.NUMBER_OF_PAGES, numOfPages);

        return new CommandResult(Paths.GOODS_LIST);
    }

    private int getNumOfPages(GoodsDAO goodsDAO, int recordsPerPage) {
        int recordsCount = goodsDAO.getRecordsCount();
        return (int) Math.ceil(recordsCount * 1.0/ recordsPerPage);
    }
}
