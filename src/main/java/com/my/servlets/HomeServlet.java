package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.classes.Ship;
import com.my.classes.User;
import com.my.DB.TicketsDB;
import com.my.DB.StationDB;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;

import com.my.DB.ShipDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

@WebServlet("/index")
public class HomeServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(HomeServlet.class);
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
            logger.info("HomeServlet#DoGet");
            User user = (User) req.getSession().getAttribute("user");
            if(user!=null&&user.getRole().equals("admin")){
                logger.info("User - admin");
                resp.sendRedirect("admin/needToAccept");
            }else {
                req.getSession().setAttribute("latestTickets", ticketsDAO.getLatestVoyages(shipDAO, stationDAO, path));
                logger.info("Latest tickets get");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
