package com.wxl.tmall.servlet;

import com.wxl.tmall.bean.*;
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

    public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
        //获取分类
        List<Category> cs = categoryDAO.list();
        //填充产品 Category填充products
        productDAO.fill(cs);
        //分类下填充推荐产品集合 Category填充productsByRow
        productDAO.fillByRow(cs);
        //设置分类集合
        request.setAttribute("cs", cs);
        return "home.jsp";
    }

    public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);
        System.out.println(name);
        boolean exist = userDAO.isExist(name);

        System.out.println(exist);
        if (exist) {
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

    public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        name = HtmlUtils.htmlEscape(name);
        String password = request.getParameter("password");
        User user = userDAO.get(name, password);
        if (null == user) {
            request.setAttribute("msg", "账号密码错误");
            return "login.jsp";
        }
        System.out.println("user" + user.getName() + user.getPassword());
        request.getSession().setAttribute("user", user);
        return "@forehome";
    }

    public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
        request.getSession().removeAttribute("user");
        return "@forehome";
    }

    public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productDAO.get(pid);

        List<ProductImage> productSingleImages = productImageDAO.list(product, productImageDAO.type_single);
        List<ProductImage> productDetailImages = productImageDAO.list(product, productImageDAO.type_detail);
        product.setProductDetailImages(productDetailImages);
        product.setProductSingleImages(productSingleImages);
        List<PropertyValue> pvs = propertyValueDAO.list(product.getId());
        List<Review> reviews = reviewDAO.list(product.getId());
        productDAO.setSaleAndReviewNumber(product);

        request.setAttribute("reviews", reviews);
        request.setAttribute("p", product);
        request.setAttribute("pvs", pvs);
        return "product.jsp";
    }

    public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));
        Product p = productDAO.get(pid);
        int oiid = 0;
        User user = (User) request.getSession().getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == p.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemDAO.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(p);
            orderItemDAO.add(oi);
            oiid = oi.getId();
        }
        return "@forebuy?oiid=" + oiid;
    }

    public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {

        return "buy.jsp";
    }

    public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user = (User) request.getSession().getAttribute("user");
        if (null != user)
            return "%success";
        return "%fail";
    }

    public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = userDAO.get(name, password);

        if (null == user)
            return "%fail";
        request.getSession().setAttribute("user", user);
        return "%success";
    }

    public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productDAO.get(pid);
        int num = Integer.parseInt(request.getParameter("num"));
        User user = (User) request.getSession().getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == p.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemDAO.update(oi);
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setNumber(num);
            oi.setProduct(p);
            oi.setUser(user);
            orderItemDAO.add(oi);
        }
        return "%success";
    }

    public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user = (User) request.getSession().getAttribute("user");
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        request.setAttribute("ois", ois);
        return "cart.jsp";
    }
}