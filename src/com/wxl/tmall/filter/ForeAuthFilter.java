package com.wxl.tmall.filter;

import com.wxl.tmall.bean.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@WebFilter(filterName = "ForeAuthFilter",urlPatterns = "/*")
public class ForeAuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpServletRequest request= (HttpServletRequest) req;
        String contextPath = request.getServletContext().getContextPath();
        String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search",
        };
        String uri= request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")) {
            String method = StringUtils.substringAfterLast(uri, "/fore");
            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                User user = (User) request.getSession().getAttribute("user");
                if (null == user) {
                    response.sendRedirect("login.jsp");
                    return;
                }
            }
        }
        chain.doFilter(request,response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
