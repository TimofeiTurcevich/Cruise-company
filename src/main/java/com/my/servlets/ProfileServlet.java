package com.my.servlets;

import com.my.classes.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ProfileServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO
        logger.info("Profile#DoGet");
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            logger.info("No user");
            resp.sendRedirect("login.jsp");
        }else if(user.getRole().equals("client")){
            logger.info("Client user");
            resp.sendRedirect("user/profile.jsp");
        }else{
            logger.info("Admin user");
            resp.sendRedirect("admin/profile.jsp");
        }
    }
}
