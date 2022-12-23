package com.my.servlets;

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
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/documentCheck")
public class DocumentCheckServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(DocumentCheckServlet.class);
    public String path;

    public void init(ServletConfig config) throws ServletException {
        path  = config.getServletContext().getRealPath("/");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            logger.info("DocumentCheck#DoGet");
            if(req.getParameter("id")==null || req.getParameter("id").isBlank()){
                logger.info("No document to check");
                resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
            }else{
                File dir = new File(path +"/Documents/bought_ticket_"+req.getParameter("id"));
                logger.info("Documents get");
                req.getSession().setAttribute("documents",dir.listFiles());
                req.getRequestDispatcher("documentCheck.jsp").forward(req,resp);
            }
    }
}
