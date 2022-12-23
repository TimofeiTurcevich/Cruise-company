package com.my.servlets;

import com.my.DB.DAO.TicketsDAO;
import com.my.classes.User;
import com.my.DB.TicketsDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;

@MultipartConfig
@WebServlet("/user/document")
public class DocumentServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(DocumentServlet.class);
    public TicketsDAO ticketsDAO;

    public void init(ServletConfig config) throws ServletException {
        ticketsDAO = (TicketsDB) config.getServletContext().getAttribute("TicketsDB");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                logger.info("Document#DoGet");
                if(req.getParameter("id")==null||req.getParameter("id").isBlank()){
                    resp.sendError(404,"Such link is not existing. Check if you enter all parameters right");
                    return;
                }
                ticketsDAO.setBoughtStatus(Long.parseLong(req.getParameter("id")),2);
                File dir = new File(req.getServletContext().getRealPath("/") +"/Documents/bought_ticket_"+req.getParameter("id"));
                if(!dir.exists()){
                    dir.mkdirs();
                    logger.info("Dir created");
                }
                for(int i = 1;i<Integer.parseInt(req.getParameter("count"))+1;i++){
                    Part part = req.getPart("document"+i);
                    Files.copy(
                            part.getInputStream(),
                            Paths.get(dir.getAbsolutePath()+"/"+part.getSubmittedFileName()),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                }
                logger.info("Files copied");
                resp.sendRedirect("boughtTickets");
            }catch (SQLException e){
                resp.sendError(500,"Something went wrong. Try again later");
            }
    }
}
