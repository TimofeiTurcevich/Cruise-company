package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.classes.BoughtTickets;
import com.my.classes.User;
import com.my.DB.TicketsDB;
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

@WebServlet("/user/cancel")
public class CancelServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(CancelServlet.class);
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
            logger.info("Cancel#DoGet");
            if(req.getParameter("id")==null||req.getParameter("id").isBlank()){
                resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                return;
            }
            BoughtTickets tempt = ticketsDAO.getBoughtTicket(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,path);
            if(tempt==null){
                throw new SQLException();
            }
            ticketsDAO.setBoughtStatus(Long.parseLong(req.getParameter("id")),9);
            if(tempt.getStatus().equals("Accepted")){
                switch (tempt.getType()){
                    case "lux":
                        tempt.getVoyage().setBoughtLux(tempt.getVoyage().getBoughtLux()-1);
                        break;
                    case "standard":
                        tempt.getVoyage().setBoughtStandard(tempt.getVoyage().getBoughtStandard()-1);
                        break;
                }
                ticketsDAO.updateBoughtVoyage(tempt.getVoyage());
            }
            logger.info("Canceled");
            resp.sendRedirect("boughtTickets");
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
