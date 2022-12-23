package com.my.servlets;

import com.my.DB.DAO.UserDAO;
import com.my.DB.ShipDB;
import com.my.DB.UserDB;
import com.my.classes.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/user/changeLang","/changeLang","/admin/changeLang"})
public class ChangeLanguageServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ChangeLanguageServlet.class);
    public UserDAO userDAO;

    public void init(ServletConfig config) throws ServletException {
        userDAO = (UserDB) config.getServletContext().getAttribute("UserDB");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("ChangeLang#DoGet");
            req.getServletContext().setAttribute("lang",req.getParameter("lang"));
            User user = (User) req.getSession().getAttribute("user");
            if(user!=null){
                user.setLang(req.getParameter("lang"));
                userDAO.changeLang(user.getId(),req.getParameter("lang"));
            }
            logger.info("Lang changed");
            resp.sendRedirect(req.getParameter("page"));
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
