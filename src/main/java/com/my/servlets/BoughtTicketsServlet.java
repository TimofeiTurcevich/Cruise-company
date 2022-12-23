package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.classes.BoughtTickets;
import com.my.classes.Ship;
import com.my.classes.User;
import com.my.DB.TicketsDB;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
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
import java.util.List;

@WebServlet("/user/boughtTickets")
public class BoughtTicketsServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(BoughtTicketsServlet.class);
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
            logger.info("BoughtTickets#DoGet");
            User tempt = (User) req.getSession().getAttribute("user");
            List<BoughtTickets> boughtTickets = ticketsDAO.getBoughtTickets(tempt.getId(),shipDAO,stationDAO,path);
            if(req.getParameter("page")==null){
                req.getSession().setAttribute("boughtTickets",boughtTickets);
                logger.info("Bought tickets get");
                req.getRequestDispatcher("boughtTickets.jsp?page=1").forward(req,resp);
            }else if(Integer.parseInt(req.getParameter("page"))>(int) (boughtTickets.size()/3.0+0.9)) {
                logger.info("Invalid page");
                resp.sendError(404, "This page(" + req.getParameter("page") + ") is not existing");
            }else{
                logger.info("Bought tickets page = " + req.getParameter("page"));
                req.getRequestDispatcher("boughtTickets.jsp").forward(req,resp);
            }
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
