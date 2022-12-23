package com.my.servlets;

import com.my.DB.DAO.TicketsDAO;
import com.my.classes.BoughtTickets;
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

@WebServlet("/admin/decline")
public class DeclineDocumentServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(DeclineDocumentServlet.class);
    public TicketsDAO ticketsDAO;

    public void init(ServletConfig config) throws ServletException {
        ticketsDAO = (TicketsDB) config.getServletContext().getAttribute("TicketsDB");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("Decline#DoPost");
            if(req.getParameter("id")==null||req.getParameter("id").isBlank()){
                resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                return;
            }
            ticketsDAO.setBoughtStatus(Long.parseLong(req.getParameter("id")),5);
            logger.info("Declined");
            resp.sendRedirect("needToAccept");
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
