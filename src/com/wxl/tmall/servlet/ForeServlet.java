package com.wxl.tmall.servlet;

import com.wxl.tmall.bean.Category;
import com.wxl.tmall.bean.User;
import com.wxl.tmall.dao.CategoryDAO;
import com.wxl.tmall.dao.ProductDAO;
import com.wxl.tmall.util.Page;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ForeServlet", urlPatterns = "/foreServlet")
public class ForeServlet extends BaseForeServlet {

    public String home(HttpServletRequest request, HttpServletResponse response, Page page){
        //获取分类
        List<Category> cs = categoryDAO.list();
        //填充产品 Category填充products
        productDAO.fill(cs);
        //分类下填充推荐产品集合 Category填充productsByRow
        productDAO.fillByRow(cs);
        //设置分类集合
        request.setAttribute("cs",cs);
        return "home.jsp";
    }

    public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);
        System.out.println(name);
        boolean exist = userDAO.isExist(name);

        System.out.println(exist);
        if(exist){
            request.setAttribute("msg", "用户名已经被使用,不能使用");
            return "register.jsp";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        userDAO.add(user);

        return "@registerSuccess.jsp";
    }

}
