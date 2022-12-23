package com.my.filters;

import com.my.classes.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/user/*")
public class UserFilter implements Filter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) req.getSession().getAttribute("user");
        if(user!=null&&user.getRole().equals("client")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else if(user!=null&&user.getRole().equals("admin")){
            response.sendRedirect(req.getContextPath()+"/admin/needToAccept");
        }else{
            response.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }
}
