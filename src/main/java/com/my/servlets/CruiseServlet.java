package com.my.servlets;

import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.classes.Ship;
import com.my.classes.User;
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
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/admin/cruise","/user/cruise"})
public class CruiseServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(CruiseServlet.class);
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
                logger.info("Cruise#DoGet");
                if(req.getParameter("id")==null || req.getParameter("id").isBlank()){
                    logger.info("No cruise chosen");
                    resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                }else{
                    User tempt = (User) req.getSession().getAttribute("user");
                    Voyage temptVoyage = ticketsDAO.getVoyage(Long.parseLong(req.getParameter("id")),shipDAO, stationDAO,path);
                    if(temptVoyage==null){
                        throw new SQLException();
                    }
                    logger.info("Voyage get");
                    req.getSession().setAttribute("voyage",temptVoyage);
                    if(tempt.getRole().equals("client")){
                        req.getSession().setAttribute("favorite", ticketsDAO.getFavoriteVoyages(tempt.getId(),shipDAO,stationDAO,path)
                                .stream().anyMatch(i -> i.getId() == temptVoyage.getId()));
                        logger.info("In favorite check");
                    }
                    req.getRequestDispatcher("cruise.jsp").forward(req,resp);
                }
            }catch (SQLException e){
                resp.sendError(500,"Something went wrong. Try again later");
            }
    }
}
