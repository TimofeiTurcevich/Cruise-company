package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.classes.Ship;
import com.my.DB.TicketsDB;
import com.my.DB.StationDB;
import com.my.DB.ShipDB;
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

@WebServlet(urlPatterns = {"/sort","/admin/sort"})
public class SortTicketsServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(SortTicketsServlet.class);
    public TicketsDAO ticketsDAO;
    public StationDAO stationDAO;
    public ShipDAO shipDAO;
    public String path;

    public void init(ServletConfig config) throws ServletException {
        ticketsDAO = (TicketsDB) config.getServletContext().getAttribute("TicketsDB");
        stationDAO = (StationDB) config.getServletContext().getAttribute("StationDB");
        shipDAO = (ShipDB) config.getServletContext().getAttribute("ShipDB");
        path  = config.getServletContext().getRealPath("/");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                logger.info("Sort#DoPost");
                req.getSession().setAttribute("tickets",ticketsDAO.getSort(
                        req.getParameter("priceMin"),
                        req.getParameter("priceMax"),
                        req.getParameter("startStationId"),
                        req.getParameter("stationId"),
                        req.getParameter("isFinal"),
                        req.getParameter("startDate"),
                        req.getParameter("endDate"),
                        req.getParameter("sortBy"),
                        req.getParameter("voyageDuration"),
                        stationDAO, shipDAO,
                        path
                ));
                logger.info("Tickets sorted");
                resp.sendRedirect("tickets?page=1");
            }catch (SQLException e){
                resp.sendError(500,"Something went wrong. Try again later");
            }
    }
}
