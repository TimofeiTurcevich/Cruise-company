package com.my.filters;

import com.my.classes.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            response.sendRedirect(req.getContextPath()+"/login.jsp");
        } else if(user.getRole().equals("admin")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            response.sendRedirect("../index");
        }
    }
}
