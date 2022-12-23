package com.my.filters;

import com.my.classes.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/login.jsp","/register.jsp"})
public class LoginFilter implements Filter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else if(user.getRole().equals("client")){
            response.sendRedirect("index");
        }else{
            response.sendRedirect("admin/needToAccept");
        }
    }
}
