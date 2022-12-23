package com.my.DB;

import com.my.DB.DAO.ShipDAO;
import com.my.classes.Ship;
import com.my.classes.Staff;
import com.my.servlets.TicketsServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipDB implements ShipDAO {
    static final Logger logger = LogManager.getLogger(ShipDB.class);

     public Ship getShip(long id, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ship WHERE id = ?")){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                File dir = new File(path+"/Ship_Pictures/ship_"+rs.getLong("id"));
                Ship tempt = new Ship.Builder()
                        .setId(rs.getLong("id"))
                        .setLuxRooms(rs.getInt("lux_rooms"))
                        .setStandardRooms(rs.getInt("standard_rooms"))
                        .setName(rs.getString("name"))
                        .setStaffs(getShipStaff(rs.getLong("id")))
                        .setPicture(dir.listFiles()[0])
                        .build();
                logger.info("Ship get");
                return tempt;
            }
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
        return null;
     }

     public List<Staff> getShipStaff(long shipId) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cruise_company.ship_staff " +
                    "INNER JOIN cruise_company.staff " +
                    "ON staff_id = staff.id " +
                    "WHERE ship_id = ?")){
            ps.setLong(1,shipId);
            ResultSet rs = ps.executeQuery();
            List<Staff> staff = new ArrayList<>();
            while(rs.next()){
                Staff tempt = new Staff.Builder()
                        .setId(rs.getLong("staff.id"))
                        .setName(rs.getString("staff.name"))
                        .setPosition(rs.getString("staff.position"))
                        .build();
                staff.add(tempt);
            }
            logger.info("Staff get");
            return staff;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
     }

     public List<Ship> getShips(String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
                Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id,name FROM ship")){
            List<Ship> ships = new ArrayList<>();
            while(rs.next()){
                File dir = new File(path+"/Ship_Pictures/ship_"+rs.getLong("id"));
                Ship tempt = new Ship.Builder()
                        .setId(rs.getLong("id"))
                        .setName(rs.getString("name"))
                        .setPicture(dir.listFiles()[0])
                        .build();
                ships.add(tempt);
            }
            logger.info("Ships get");
            return ships;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
     }
}
