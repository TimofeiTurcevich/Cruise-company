package com.my.servlets;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.BoughtTickets;
import com.my.classes.User;
import com.my.classes.Voyage;
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

@WebServlet("/user/buyForm")
public class DocumentUploadServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(DocumentServlet.class);
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
            logger.info("BuyForm#DoGet");
            if(req.getParameter("id")==null||req.getParameter("id").isBlank()){
                resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                return;
            }
            BoughtTickets boughtTickets = ticketsDAO.getBoughtTicket(Long.parseLong(req.getParameter("id")), shipDAO,stationDAO,path);
            if(boughtTickets == null){
                throw new SQLException();
            }
            req.getRequestDispatcher("buyForm.jsp?count="+boughtTickets.getTicketsCount()).forward(req,resp);
        }catch (SQLException e){
            resp.sendError(500,"Something went wrong. Try again later");
        }
    }
}
