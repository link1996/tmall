package com.wxl.tmall.servlet;

import com.wxl.tmall.bean.Order;
import com.wxl.tmall.bean.User;
import com.wxl.tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet",urlPatterns = "/orderServlet")
public class OrderServlet extends BackBaseServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Order> os = orderDAO.list(page.getStart(), page.getCount());
        int total = orderDAO.getTotal();

        page.setTotal(total);


        return "admin/listOrder.jsp";
    }
}
