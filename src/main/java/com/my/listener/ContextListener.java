package com.my.listener;

import com.my.DB.DAOFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class  ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        DAOFactory daoFactory = new DAOFactory();
        sce.getServletContext().setAttribute("TicketsDB",daoFactory.getTicketsDAO());
        sce.getServletContext().setAttribute("ShipDB",daoFactory.getShipDAO());
        sce.getServletContext().setAttribute("StationDB",daoFactory.getStationDAO());
        sce.getServletContext().setAttribute("UserDB",daoFactory.getUserDAO());
        sce.getServletContext().setAttribute("lang","en");


    }
}
