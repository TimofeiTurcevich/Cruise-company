package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.classes.Ship;
import com.my.DB.TicketsDB;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.classes.Voyage;
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
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = {"/user/sales","/admin/sales"})
public class SalesServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(SalesServlet.class);
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

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("Sales#DoGet");
            List<Voyage> voyages = ticketsDAO.getSaleTickets(shipDAO,stationDAO,path);
            if(req.getParameter("page")==null){
                req.getSession().setAttribute("salesTickets",voyages);
                logger.info("Sales get");
                req.getRequestDispatcher("sales.jsp?page=1").forward(req,resp);
            }else if(Integer.parseInt(req.getParameter("page"))>(int) (voyages.size()/3.0+0.9)) {
                logger.info("Invalid page");
                resp.sendError(404, "This page(" + req.getParameter("page") + ") is not existing");
            }else{
                logger.info("Sales page = " + req.getParameter("page"));
                req.getRequestDispatcher("sales.jsp").forward(req,resp);
            }
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
