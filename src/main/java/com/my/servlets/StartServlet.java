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

@WebServlet("/start")
public class StartServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(StartServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Start#DoGet");
        User user = (User) req.getSession().getAttribute("user");
        if(user!= null){
            logger.info("User found");
            resp.sendRedirect("tickets");
        }else{
            logger.info("User not found");
            resp.sendRedirect("login.jsp");
        }
    }
}
