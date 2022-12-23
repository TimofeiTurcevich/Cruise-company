package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.classes.Ship;
import com.my.classes.Voyage;
import com.my.classes.VoyageStation;
import com.my.DB.TicketsDB;
import com.my.DB.StationDB;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.my.DB.ShipDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/admin/createCruise")
public class NewCruiseCreateServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(NewCruiseCreateServlet.class);
    public TicketsDAO ticketsDAO;
    public StationDAO stationDAO;
    public ShipDAO shipDAO;
    public String path;
    public Voyage voyage = new Voyage();

    public void init(ServletConfig config) throws ServletException {
        ticketsDAO = (TicketsDB) config.getServletContext().getAttribute("TicketsDB");
        stationDAO = (StationDB) config.getServletContext().getAttribute("StationDB");
        shipDAO = (ShipDB) config.getServletContext().getAttribute("ShipDB");
        path  = config.getServletContext().getRealPath("/");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                logger.info("CreateCruise#DoPost");
                List<VoyageStation> stations = new ArrayList<>();
                VoyageStation tempt=new VoyageStation.Builder()
                        .setArrivalDate(null)
                        .setDepartureDate(Timestamp.valueOf(req.getParameter("departureDate1").replace('T',' ')+":00"))
                        .setStation(stationDAO.getStation(Long.parseLong(req.getParameter("stationId1"))))
                        .setPosition(1)
                        .build();
                stations.add(tempt);
                for(int i =2;i<Integer.parseInt(req.getParameter("stationNumbers"));i++){
                    tempt = new VoyageStation.Builder()
                            .setArrivalDate(Timestamp.valueOf(req.getParameter("arrivalDate"+i).replace('T',' ')+":00"))
                            .setDepartureDate(Timestamp.valueOf(req.getParameter("departureDate"+i).replace('T',' ')+":00"))
                            .setStation(stationDAO.getStation(Long.parseLong(req.getParameter("stationId"+i))))
                            .setPosition(i)
                            .build();
                    stations.add(tempt);
                }
                tempt = new VoyageStation.Builder()
                        .setDepartureDate(null)
                        .setArrivalDate(Timestamp.valueOf(req.getParameter("arrivalDate"+req.getParameter("stationNumbers")).replace('T',' ')+":00"))
                        .setStation(stationDAO.getStation(Long.parseLong(req.getParameter("stationId"+req.getParameter("stationNumbers")))))
                        .setPosition(Integer.parseInt(req.getParameter("stationNumbers")))
                        .build();
                stations.add(tempt);

                voyage.setShip(shipDAO.getShip(Long.parseLong(req.getParameter("shipId")),path));
                voyage.setStandardPrice(Integer.parseInt(req.getParameter("standardPrice")));
                voyage.setVoyageStations(stations);
                voyage.setLuxPrice(Integer.parseInt(req.getParameter("luxPrice")));

                long id = ticketsDAO.insertVoyage(voyage,stationDAO);
                logger.info("Voyage created");
                resp.sendRedirect("cruise?id="+id);
            }catch (SQLException e){
                resp.sendError(500,"Something went wrong. Check if ship dont have voyage on this time or try again later");
            }
    }
}
