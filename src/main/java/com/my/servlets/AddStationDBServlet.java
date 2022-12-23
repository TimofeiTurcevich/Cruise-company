package com.my.servlets;

import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.classes.Voyage;
import com.my.classes.VoyageStation;
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
import java.sql.Timestamp;

@WebServlet("/admin/addStationDB")
public class AddStationDBServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(AddStationDBServlet.class);
    public TicketsDAO ticketsDAO;
    public StationDAO stationDAO;
    public ShipDAO shipDAO;
    public String path;

    public void init(ServletConfig config) throws ServletException {
            ticketsDAO = (TicketsDB) config.getServletContext().getAttribute("TicketsDB");
            stationDAO = (StationDB) config.getServletContext().getAttribute("StationDB");
            shipDAO = (ShipDB) config.getServletContext().getAttribute("ShipDB");
            path = config.getServletContext().getRealPath("/");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                logger.info("AddStationDB#DoPost");
                int position  = Integer.parseInt(req.getParameter("position"));
                VoyageStation tempt=new VoyageStation.Builder()
                        .setArrivalDate(Timestamp.valueOf(req.getParameter("arrivalDate").replace('T',' ')+":00"))
                        .setDepartureDate(Timestamp.valueOf(req.getParameter("departureDate").replace('T',' ')+":00"))
                        .setStation(stationDAO.getStation(Long.parseLong(req.getParameter("stationId"))))
                        .setPosition(position)
                        .build();

                Voyage voyage = ticketsDAO.getVoyage(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,path);
                if(voyage==null){
                    throw new SQLException();
                }

                logger.info("Voyage get");

                voyage.getVoyageStations().stream().filter(i->i.getPosition()>=position).forEach(i->i.setPosition(i.getPosition()+1));

                logger.info("Voyage get");
                stationDAO.insertVoyageStation(tempt,voyage.getId());

                logger.info("Station added");
                voyage.getVoyageStations().add(position-1,tempt);
                stationDAO.updateVoyageStations(voyage.getVoyageStations(),voyage.getId());
                logger.info("Station updated");
                resp.sendRedirect("cruise?id="+req.getParameter("id"));
            }catch (SQLException e){
                resp.sendError(500,"Something went wrong. Try again later");
            }
    }
}
