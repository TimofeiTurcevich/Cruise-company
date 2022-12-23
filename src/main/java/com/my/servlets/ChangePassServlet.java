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
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/admin/changePass", "/user/changePass"})
public class ChangePassServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ChangePassServlet.class);
    public UserDAO userDAO;

    public void init(ServletConfig config) throws ServletException {
        userDAO = (UserDB) config.getServletContext().getAttribute("UserDB");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("ChangePass#DoPost");
            User tempt = (User) req.getSession().getAttribute("user");
            userDAO.changePass(req.getParameter("oldPass"),req.getParameter("newPass"),tempt.getId());
            logger.info("Pass changed");
            resp.sendRedirect("../profile");
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
