package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.TicketsDB;
import com.my.classes.Ship;
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

@WebServlet("/admin/newCruiseGeneral")
public class NewCruiseServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(NewCruiseServlet.class);
    public StationDAO stationDAO;
    public ShipDAO shipDAO;
    public String path;

    public void init(ServletConfig config) throws ServletException {
        stationDAO = (StationDB) config.getServletContext().getAttribute("StationDB");
        shipDAO = (ShipDB) config.getServletContext().getAttribute("ShipDB");
        path  = config.getServletContext().getRealPath("/");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("NewCruiseGeneral#DoGet");
            req.getSession().setAttribute("ships",shipDAO.getShips(path));
            req.getSession().setAttribute("stations",stationDAO.getStations());
            req.getRequestDispatcher("addNewCruiseGeneral.jsp").forward(req,resp);
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
