package com.my.servlets;

import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.UserDAO;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.DB.TicketsDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(LoginServlet.class);
    public UserDAO userDAO;

    public void init(ServletConfig config) throws ServletException {
        userDAO = (UserDB) config.getServletContext().getAttribute("UserDB");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("Login#DoPost");
            String email= req.getParameter("email");
            String pass = req.getParameter("pass");
            User user = userDAO.getUser(email, pass);
            if(user==null){
                logger.info("No user found");
                resp.sendError(417,"Such user is not existing. Check your email and password");
            }else if(user.getRole().equals("client")){
                logger.info("Client user");
                req.getServletContext().setAttribute("lang",user.getLang());
                req.getSession().setAttribute("user",user);
                resp.sendRedirect("index");
            }else{
                logger.info("Admin user");
                req.getServletContext().setAttribute("lang",user.getLang());
                req.getSession().setAttribute("user",user);
                resp.sendRedirect("admin/needToAccept");
            }
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
