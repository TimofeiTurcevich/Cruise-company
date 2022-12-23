package com.my.DB.DAO;

import com.my.classes.Ship;
import com.my.classes.Staff;

import java.sql.SQLException;
import java.util.List;

public interface ShipDAO {
    public Ship getShip(long id, String path) throws SQLException;
    public List<Staff> getShipStaff(long shipId) throws SQLException;
    public List<Ship> getShips(String path) throws SQLException;
}
