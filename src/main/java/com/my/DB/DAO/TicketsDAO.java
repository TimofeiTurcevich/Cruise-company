package com.my.DB.DAO;

import com.my.classes.BoughtTickets;
import com.my.classes.Voyage;

import java.sql.SQLException;
import java.util.List;

public interface TicketsDAO {
    public Voyage getVoyage(long id, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public List<Voyage> getFavoriteVoyages(long userId, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public List<Voyage> getSaleTickets(ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public long insertVoyage(Voyage voyage, StationDAO stationDAO) throws SQLException;
    public List<Voyage> getAllTickets(String sortBy, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public List<Voyage> getLatestVoyages(ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public BoughtTickets getBoughtTicket(long id, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public List<BoughtTickets> getBoughtTickets(long userId, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public List<BoughtTickets> getAcceptTickets(ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public List<Voyage> getSort(String minPrice,String maxPrice,String startStationId, String stationId, String isFinal,
                                String startDate, String endDate,String sortBy,String voyageDuration, StationDAO stationDAO, ShipDAO shipDAO, String path) throws SQLException;
    public void deleteVoyage(long id) throws SQLException;
    public void updateVoyageGeneral(Voyage voyage) throws SQLException;
    public void setSaleVoyage(Voyage voyage) throws SQLException;
    public void insertToFavorite(long voyageId, long userId) throws SQLException;
    public void deleteFromFavorite(long voyageId, long userId) throws SQLException;
    public void setStandardVoyage(long id) throws SQLException;
    public void setBoughtStatus(long id, long statusId) throws SQLException;
    public void insertBoughtTicket(long voyageId, long userId, String type, int children, int adult, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException;
    public void allStandardBought(long id) throws SQLException;
    public void allLuxBought(long id) throws SQLException;
    public void updateBoughtVoyage(Voyage voyage) throws SQLException;
}
