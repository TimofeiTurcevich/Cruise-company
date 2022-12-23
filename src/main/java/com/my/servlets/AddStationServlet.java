package com.my.servlets;

import com.my.DB.DAO.StationDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
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

@WebServlet("/admin/addStation")
public class AddStationServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(AddStationServlet.class);

    public StationDAO stationDAO;

    public void init(ServletConfig config) throws ServletException {
        stationDAO = (StationDB) config.getServletContext().getAttribute("StationDB");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("AddStation#DoGet");
            req.getSession().setAttribute("stations",stationDAO.getStations());
            req.getRequestDispatcher("addStation.jsp").forward(req,resp);
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
