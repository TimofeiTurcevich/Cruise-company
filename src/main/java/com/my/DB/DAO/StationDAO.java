package com.my.DB.DAO;

import com.my.classes.Station;
import com.my.classes.VoyageStation;

import java.sql.SQLException;
import java.util.List;

public interface StationDAO {
    public List<Station> getStations() throws SQLException;
    public List<VoyageStation> getStations(long voyageId) throws SQLException;
    public Station getStation(long id) throws SQLException;
    public boolean insertVoyageStations(List<VoyageStation> stations,long voyageId) throws SQLException;
    public boolean deleteVoyageStation(VoyageStation voyageStation, long voyageId) throws SQLException;
    public boolean updateVoyageStations(List<VoyageStation> stations, long voyageId) throws SQLException;
    public boolean insertVoyageStation(VoyageStation station, long voyageId) throws SQLException;
    public boolean updateStation(VoyageStation station, long voyageId) throws SQLException;
}
