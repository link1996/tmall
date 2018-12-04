package com.wxl.tmall.servlet;

import com.wxl.tmall.bean.Category;
import com.wxl.tmall.bean.Property;
import com.wxl.tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PropertyServlet",urlPatterns = "/propertyServlet")
public class PropertyServlet extends BackBaseServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);

        String name = request.getParameter("name");
        Property property = new Property();
        property.setCategory(category);
        property.setName(name);
        propertyDAO.add(property);
        return "@admin_property_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property property = propertyDAO.get(id);
        propertyDAO.delete(id);

        return "@admin_property_list?cid="+property.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property p = propertyDAO.get(id);
        request.setAttribute("p",p);
        return "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        String name = request.getParameter("name");

        Category c = categoryDAO.get(cid);
        Property p = new Property();
        p.setName(name);
        p.setCategory(c);
        p.setId(id);

        return "@admin_property_list?cid="+p.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.get(cid);
        System.out.println(cid);

        List<Property> ps = propertyDAO.list(cid,page.getStart(),page.getCount());
        System.out.println("test=====>"+ps.size());
        int total = propertyDAO.getTotal(cid);
        page.setTotal(total);
        //page传递cid
        page.setParam("&cid="+cid);
        request.setAttribute("ps",ps);
        request.setAttribute("c",c);
        request.setAttribute("page",page);
        return "admin/listProperty.jsp";
    }
}
