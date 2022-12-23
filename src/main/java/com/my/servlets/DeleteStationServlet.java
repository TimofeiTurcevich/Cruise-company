package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.classes.Ship;
import com.my.classes.Voyage;
import com.my.DB.TicketsDB;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
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
import java.sql.SQLType;
import java.sql.Types;
import java.util.List;

@WebServlet("/admin/deleteStation")
public class DeleteStationServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(DeleteStationServlet.class);
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
                logger.info("DeleteStation#DoPost");
                if(req.getParameter("id")==null||req.getParameter("id").isBlank()){
                    resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                    return;
                }

                int position  = Integer.parseInt(req.getParameter("stationPosition"));
                Voyage voyage = ticketsDAO.getVoyage(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,path);
                if(voyage==null){
                    throw new SQLException();
                }


                voyage.getVoyageStations().stream().filter(i->i.getPosition()>position).forEach(i->i.setPosition(i.getPosition()-1));

                stationDAO.deleteVoyageStation(voyage.getVoyageStations().get(position-1),voyage.getId());
                logger.info("Station deleted");
                voyage.getVoyageStations().remove(position-1);
                stationDAO.updateVoyageStations(voyage.getVoyageStations(),voyage.getId());
                logger.info("Station updated");
                resp.sendRedirect("cruise?id="+req.getParameter("id"));
            }catch (SQLException e){
                resp.sendError(500,"Something went wrong. Try again later");
            }
    }
}
