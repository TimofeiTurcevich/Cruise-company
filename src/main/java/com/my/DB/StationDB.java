package com.my.DB;

import com.my.DB.DAO.StationDAO;
import com.my.classes.Station;
import com.my.classes.VoyageStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StationDB implements StationDAO {
    static final Logger logger = LogManager.getLogger(StationDB.class);

    public List<Station> getStations() throws SQLException {
        try (Connection con = ConnectionManager.getInstance().getDs().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM station")) {
            List<Station> stations = new ArrayList<>();
            while (rs.next()) {
                Station tempt = new Station.Builder()
                        .setId(rs.getLong("id"))
                        .setCity(rs.getString("city"))
                        .setCountry(rs.getString("country"))
                        .build();
                stations.add(tempt);
            }
            logger.info("Stations get");
            return stations;
        } catch (SQLException e) {
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public List<VoyageStation> getStations(long voyageId) throws SQLException {
        try (Connection con = ConnectionManager.getInstance().getDs().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM voyage_station " +
                     "WHERE voyage_id = ?")) {
            ps.setLong(1, voyageId);
            ResultSet rs = ps.executeQuery();
            List<VoyageStation> voyageStations = new ArrayList<>();
            while (rs.next()) {
                VoyageStation tempt = new VoyageStation.Builder()
                        .setStation(getStation(rs.getLong("station_id")))
                        .setPosition(rs.getInt("position"))
                        .setArrivalDate(rs.getTimestamp("arrival_date"))
                        .setDepartureDate(rs.getTimestamp("departure_date"))
                        .build();
                voyageStations.add(tempt);
            }
            voyageStations.sort((o1, o2) -> {
                if (o1.getPosition() == o2.getPosition()) {
                    return 0;
                }
                return o1.getPosition() < o2.getPosition() ? -1 : 1;
            });
            logger.info("Voyage stations get");
            return voyageStations;
        } catch (SQLException e) {
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public Station getStation(long id) throws SQLException {
        try (Connection con = ConnectionManager.getInstance().getDs().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM station WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Station tempt = new Station.Builder()
                        .setId(rs.getLong("id"))
                        .setCity(rs.getString("city"))
                        .setCountry(rs.getString("country"))
                        .build();
                logger.info("Station get");
                return tempt;
            }
        } catch (SQLException e) {
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
        return null;
    }

    public boolean insertVoyageStations(List<VoyageStation> stations, long voyageId) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("INSERT INTO voyage_station(voyage_id,station_id,position,arrival_date,departure_date) VALUES(?,?,?,?,?)");
            for (VoyageStation tempt : stations) {
                ps.setLong(1, voyageId);
                ps.setLong(2, tempt.getStation().getId());
                ps.setInt(3, tempt.getPosition());
                if (tempt.getArrivalDate() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setTimestamp(4, tempt.getArrivalDate());
                }
                if (tempt.getDepartureDate() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setTimestamp(5, tempt.getDepartureDate());
                }
                ps.addBatch();
            }
            int[] i = ps.executeBatch();
            for (int tempt : i) {
                if (tempt < 1) {
                    throw new SQLException();
                }
            }
            con.commit();
            logger.info("Voyage stations inserted");
            return true;
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
                con.close();
            }
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        } finally {
            if(con!=null){
                con.close();
            }
        }
    }

    public boolean deleteVoyageStation(VoyageStation voyageStation, long voyageId) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("DELETE FROM voyage_station WHERE voyage_id = ? AND station_id = ?");
            ps.setLong(1, voyageId);
            ps.setLong(2, voyageStation.getStation().getId());
            if (ps.executeUpdate() < 1) {
                throw new SQLException();
            }
            con.commit();
            logger.info("Voyage station deleted");
            return true;
        } catch (SQLException e) {
            if(con!=null){
                con.rollback();
                con.close();
            }
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }finally {
            if(con!=null){
                con.close();
            }
        }
    }

    public boolean updateVoyageStations(List<VoyageStation> stations, long voyageId) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE voyage_station SET position = ? WHERE voyage_id = ? AND station_id =?");
            for (VoyageStation tempt : stations) {
                ps.setInt(1, tempt.getPosition());
                ps.setLong(2, voyageId);
                ps.setLong(3, tempt.getStation().getId());
                ps.addBatch();
            }
            int[] i = ps.executeBatch();
            for (int tempt : i) {
                if (tempt < 0) {
                    throw new SQLException();
                }
            }
            con.commit();
            logger.info("Voyage station updated");
            return true;
        } catch (SQLException e) {
            if(con!=null){
                con.rollback();
                con.close();
            }
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }finally {
            if(con!=null){
                con.close();
            }
        }
    }

    public boolean insertVoyageStation(VoyageStation station, long voyageId) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("INSERT INTO voyage_station(voyage_id,station_id,position,arrival_date,departure_date) VALUES(?,?,?,?,?)");
            ps.setLong(1, voyageId);
            ps.setLong(2, station.getStation().getId());
            ps.setInt(3, station.getPosition());
            ps.setTimestamp(4, station.getArrivalDate());
            ps.setTimestamp(5, station.getDepartureDate());
            if (ps.executeUpdate() < 1) {
                throw new SQLException();
            }
            con.commit();
            logger.info("Voyage station inserted");
            return true;
        } catch (SQLException e) {
            if(con!=null){
                con.rollback();
                con.close();
            }
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }finally {
            if(con!=null){
                con.close();
            }
        }
    }

    public boolean updateStation(VoyageStation station, long voyageId) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE voyage_station SET arrival_date= ?,departure_date = ? WHERE voyage_id = ? AND station_id =?");
            ps.setTimestamp(1, station.getArrivalDate());
            ps.setTimestamp(2, station.getDepartureDate());
            ps.setLong(3, voyageId);
            ps.setLong(4, station.getStation().getId());
            if (ps.executeUpdate() < 1) {
                throw new SQLException();
            }
            con.commit();
            logger.info("Voyage station updated");
            return true;
        } catch (SQLException e) {
            if(con!=null){
                con.rollback();
                con.close();
            }
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }finally {
            if(con!=null){
                con.close();
            }
        }
    }
}
