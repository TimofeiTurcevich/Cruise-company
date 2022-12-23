package com.my.filters;

import com.my.classes.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class UnverifiedFilter implements Filter{
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        User tempt = (User) req.getSession().getAttribute("user");
        if(tempt != null && tempt.getRole().equals("unverified")){
            resp.sendRedirect("verify");
        }else{
            filterChain.doFilter(req,resp);
        }
    }
}
