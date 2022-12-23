package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
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

@WebServlet("/admin/accept")
public class AcceptDocumentServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(AcceptDocumentServlet.class);
    public TicketsDAO ticketsDAO;
    public ShipDAO shipDAO;
    public StationDAO stationDAO;
    public String path;

    public void init(ServletConfig config) throws ServletException {
        ticketsDAO = (TicketsDB) config.getServletContext().getAttribute("TicketsDB");
        shipDAO = (ShipDB) config.getServletContext().getAttribute("ShipDB");
        stationDAO = (StationDB) config.getServletContext().getAttribute("StationDB");
        path = config.getServletContext().getRealPath("/");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                logger.info("Accept#DoGet");
                if(req.getParameter("id")==null||req.getParameter("id").isBlank()){
                    resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                    return;
                }
                ticketsDAO.setBoughtStatus(Long.parseLong(req.getParameter("id")),4);
                BoughtTickets boughtTickets = ticketsDAO.getBoughtTicket(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,path);
                if(boughtTickets.getType().equals("standard")){
                    boughtTickets.getVoyage().setBoughtStandard(boughtTickets.getVoyage().getBoughtStandard()+1);
                }else{
                    boughtTickets.getVoyage().setBoughtLux(boughtTickets.getVoyage().getBoughtLux()+1);
                }
                ticketsDAO.updateBoughtVoyage(boughtTickets.getVoyage());
                if(boughtTickets.getVoyage().getBoughtStandard()== boughtTickets.getVoyage().getShip().getStandardRooms()){
                    ticketsDAO.allStandardBought(Long.parseLong(req.getParameter("id")));
                }
                if(boughtTickets.getVoyage().getBoughtLux()== boughtTickets.getVoyage().getShip().getLuxRooms()){
                    ticketsDAO.allLuxBought(Long.parseLong(req.getParameter("id")));
                }
                logger.info("Accepted");
                resp.sendRedirect("needToAccept");
            }catch (SQLException e){
                resp.sendError(500,"Something went wrong. Try again later");
            }
    }
}
