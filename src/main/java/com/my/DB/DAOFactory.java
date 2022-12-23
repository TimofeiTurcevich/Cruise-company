package com.my.DB;

import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.UserDAO;

public class DAOFactory {
    public ShipDAO getShipDAO(){
        return new ShipDB();
    }

    public StationDAO getStationDAO(){
        return new StationDB();
    }

    public UserDAO getUserDAO(){
        return new UserDB();
    }

    public TicketsDAO getTicketsDAO(){
        return new TicketsDB();
    }
}
