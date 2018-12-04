package com.wxl.tmall.filter;

import com.wxl.tmall.bean.Category;
import com.wxl.tmall.bean.OrderItem;
import com.wxl.tmall.bean.User;
import com.wxl.tmall.dao.CategoryDAO;
import com.wxl.tmall.dao.OrderItemDAO;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "ForeServletFilter",urlPatterns = "/*")
public class ForeServletFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        String contextPath = request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("contextPath",contextPath);

        //根据用户获取购物车订单项
        User user = (User) request.getSession().getAttribute("user");
        int cartTotalItemNumber = 0;
        if(null!=user){
            List<OrderItem> ois = new OrderItemDAO().listByUser(user.getId());
            for (OrderItem oi : ois) {
                cartTotalItemNumber+=oi.getNumber();
            }
        }
        request.setAttribute("cartTotalItemNumber",cartTotalItemNumber);

        //简单搜索栏下显示超链用 simpleSearch.jsp是他就是他
        List<Category> cs = (List<Category>) request.getAttribute("cs");
        if(null==cs){
            cs = new CategoryDAO().list();
            request.setAttribute("cs",cs);
        }

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/fore") && !uri.startsWith("/foreServlet")){
            String method = StringUtils.substringAfterLast(uri,"/fore");
            request.setAttribute("method",method);
            req.getRequestDispatcher("/foreServlet").forward(request,response);
            return;
        }
        chain.doFilter(request,response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
