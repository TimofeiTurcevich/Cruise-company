package com.my.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/createGeneral")
public class NewCruiseGeneralServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(NewCruiseGeneralServlet.class);

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("CreateGeneral#DoPost");
        req.getRequestDispatcher("newCruiseStations.jsp").forward(req,resp);
    }
}
