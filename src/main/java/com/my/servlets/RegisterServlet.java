package com.my.servlets;

import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.*;
import com.my.DB.UserDB;
import com.my.classes.MailSender;
import com.my.classes.User;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(RegisterServlet.class);
    public UserDAO userDAO;
    public Random random;
    public String path;

    public void init(ServletConfig config) throws ServletException {
        userDAO = (UserDB) config.getServletContext().getAttribute("UserDB");
        random = new Random();
        path = config.getServletContext().getContextPath();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                logger.info("Register#DoPost");
                req.getSession().setAttribute("errorRegister",false);
                String email= req.getParameter("email");
                String pass = req.getParameter("pass");
                Date date = Date.valueOf(req.getParameter("date"));
                String firstName = req.getParameter("firstName");
                String last_name = req.getParameter("lastName");
                String phone = req.getParameter("phone");
                String code = random.ints(48,123)
                        .filter(i->(i<=57 || i>=65)&&(i<=90||i>=97))
                        .limit(5)
                        .collect(StringBuilder::new,StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                User user = userDAO.insertUser(email,pass,date,firstName,last_name, phone, code);
                logger.info("User created");
                if(user==null){
                    resp.sendRedirect("register.jsp");
                }else{
                    MailSender.send(email,"Verification code: " + code + ". If you close verification page - follow this link: http://localhost:8080/cruise-company/verify.jsp?id=" + user.getId());
                    resp.sendRedirect("verify.jsp?id="+user.getId());
                }
            }catch (SQLException e){
                req.getSession().setAttribute("errorRegister",true);
                resp.sendRedirect("register.jsp");
            }
    }
}
