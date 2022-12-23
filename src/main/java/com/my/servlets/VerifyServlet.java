package com.my.servlets;

import com.my.DB.DAO.UserDAO;
import com.my.DB.UserDB;
import com.my.classes.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(VerifyServlet.class);
    public UserDAO userDAO;

    public void init(ServletConfig config) throws ServletException{
        userDAO = (UserDB) config.getServletContext().getAttribute("UserDB");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("Verify#DoPost");
            if(req.getParameter("id")==null||req.getParameter("id").isBlank()){
                resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                return;
            }
            User user = userDAO.getUserToVerify(Long.parseLong(req.getParameter("id")));
            logger.info("User get");
            if(user.getCode().equals(req.getParameter("code"))){
                logger.info("Code right");
                userDAO.updateRole(user.getId());
                logger.info("Role updated");
                user.setRole("client");
                req.getSession().setAttribute("user",user);
                resp.sendRedirect("index");
            }else{
                resp.sendRedirect("verify.jsp?id="+req.getParameter("id"));
            }
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
