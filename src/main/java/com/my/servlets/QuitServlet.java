package com.my.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/user/quit","/admin/quit"})
public class QuitServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(QuitServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Quit#DoGet");
        req.getSession().removeAttribute("user");
        logger.info("Quited");
        resp.sendRedirect("../index");
    }
}
