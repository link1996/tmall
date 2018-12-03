package com.wxl.tmall.servlet;

import com.wxl.tmall.bean.User;
import com.wxl.tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet",urlPatterns = "/userServlet")
public class UserServlet extends BackBaseServlet {
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
        List<User> us = userDAO.list(page.getStart(),page.getCount());
        int total = userDAO.getTotal();
        page.setTotal(total);
        request.setAttribute("us",us);
        request.setAttribute("page",page);
        return "admin/listUser.jsp";
    }
}
