package com.my.DB;

import com.my.DB.DAO.*;
import com.my.classes.BoughtTickets;
import com.my.classes.Voyage;
import com.my.classes.VoyageStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicketsDB implements TicketsDAO {
    static final Logger logger = LogManager.getLogger(TicketsDB.class);

    public Voyage getVoyage(long id, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM voyage WHERE id = ?")){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Voyage tempt = new Voyage.Builder()
                        .setId(rs.getLong("voyage.id"))
                        .setSale(rs.getBoolean("sale"))
                        .setBoughtLux(rs.getInt("bought_lux"))
                        .setSaleLuxPrice(rs.getInt("sale_lux"))
                        .setSaleStandardPrice(rs.getInt("sale_standard"))
                        .setLuxPrice(rs.getInt("lux_price"))
                        .setBoughtStandard(rs.getInt("bought_standard"))
                        .setStandardPrice(rs.getInt("standard_price"))
                        .setShip(shipDAO.getShip(rs.getLong("ship_id"), path))
                        .setAvailable(rs.getBoolean("available"))
                        .setVoyageStations(stationDAO.getStations(rs.getLong("voyage.id")))
                        .build();
                logger.info("Voyage get");
                return tempt;
            }
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
        return null;
    }

    public List<Voyage> getFavoriteVoyages(long userId, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
        PreparedStatement ps = con.prepareStatement ("SELECT * FROM favorite_ticket " +
                            "WHERE user_id = ?")){
            ps.setLong(1,userId);
            ResultSet rs = ps.executeQuery();
            List<Voyage> voyages = new ArrayList<>();
            while(rs.next()){
                voyages.add(getVoyage(rs.getLong("voyage_id"),shipDAO,stationDAO, path));
            }
            logger.info("Voyages get");
            return voyages;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }
    public List<Voyage> getSaleTickets(ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
                Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM voyage WHERE sale = true")){
            List<Voyage> voyages = new ArrayList<>();
            while(rs.next()){
                Voyage tempt = new Voyage.Builder()
                        .setId(rs.getLong("voyage.id"))
                        .setSale(rs.getBoolean("sale"))
                        .setBoughtLux(rs.getInt("bought_lux"))
                        .setSaleLuxPrice(rs.getInt("sale_lux"))
                        .setSaleStandardPrice(rs.getInt("sale_standard"))
                        .setLuxPrice(rs.getInt("lux_price"))
                        .setBoughtStandard(rs.getInt("bought_standard"))
                        .setStandardPrice(rs.getInt("standard_price"))
                        .setShip(shipDAO.getShip(rs.getLong("ship_id"), path))
                        .setVoyageStations(stationDAO.getStations(rs.getLong("voyage.id")))
                        .build();
                voyages.add(tempt);
            }
            logger.info("Sale voyages get");
            return voyages;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public long insertVoyage(Voyage voyage, StationDAO stationDAO) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("INSERT INTO voyage(sale,bought_lux,lux_price,bought_standard,standard_price,ship_id) " +
                    "SELECT ?,?,?,?,?,? " +
                    "FROM voyage " +
                        "WHERE (EXISTS (SELECT * FROM voyage " +
                        "INNER JOIN voyage_station " +
                        "ON voyage_station.voyage_id = voyage.id " +
                        "AND (voyage_station.position = 1 " +
                        "OR voyage_station.position = (SELECT max(position) FROM voyage_station WHERE voyage_id = voyage.id)) " +
                        "WHERE (( ? < departure_date OR ? > arrival_date) AND ship_id = ? ))) " +
                        "OR (SELECT count(*) FROM voyage WHERE ship_id = ? ) = 0 " +
                    "LIMIT 1 ",PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1,false);
            ps.setInt(2,0);
            ps.setInt(3,voyage.getLuxPrice());
            ps.setInt(4,0);
            ps.setInt(5,voyage.getStandardPrice());
            ps.setLong(6,voyage.getShip().getId());
            ps.setTimestamp(7,voyage.getVoyageStations().get(voyage.getVoyageStations().size()-1).getArrivalDate());
            ps.setTimestamp(8,voyage.getVoyageStations().get(0).getDepartureDate());
            ps.setLong(9,voyage.getShip().getId());
            ps.setLong(10,voyage.getShip().getId());
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            ResultSet rs = ps.getGeneratedKeys();
            long id=0;
            if(rs.next()){
                id =  rs.getLong(1);
                voyage.setId(id);
            }
            if(!stationDAO.insertVoyageStations(voyage.getVoyageStations(),voyage.getId())){
                con.rollback();
                logger.info("Cant create voyage, due to SQL error in stations");
                return 0;
            }
            logger.info("Voyage inserted");
            return id;
        }catch (SQLException e){
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

    public List<Voyage> getAllTickets(String sortBy, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        String query = "SELECT * FROM voyage";
        if(sortBy!=null&&!sortBy.isBlank()){
            query+=" ORDER BY " + sortBy;
        }
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
                Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            List<Voyage> voyages = new ArrayList<>();
            while(rs.next()){
                Voyage tempt = new Voyage.Builder()
                        .setId(rs.getLong("voyage.id"))
                        .setSale(rs.getBoolean("sale"))
                        .setBoughtLux(rs.getInt("bought_lux"))
                        .setSaleLuxPrice(rs.getInt("sale_lux"))
                        .setSaleStandardPrice(rs.getInt("sale_standard"))
                        .setLuxPrice(rs.getInt("lux_price"))
                        .setBoughtStandard(rs.getInt("bought_standard"))
                        .setStandardPrice(rs.getInt("standard_price"))
                        .setShip(shipDAO.getShip(rs.getLong("ship_id"), path))
                        .setVoyageStations(stationDAO.getStations(rs.getLong("voyage.id")))
                        .build();
                voyages.add(tempt);
            }
            return voyages;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public List<Voyage> getLatestVoyages(ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
                Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM voyage ORDER BY id DESC LIMIT 3")){
            List<Voyage> voyages = new ArrayList<>();
            while(rs.next()){
                Voyage tempt = new Voyage.Builder()
                        .setId(rs.getLong("voyage.id"))
                        .setSale(rs.getBoolean("sale"))
                        .setBoughtLux(rs.getInt("bought_lux"))
                        .setSaleLuxPrice(rs.getInt("sale_lux"))
                        .setSaleStandardPrice(rs.getInt("sale_standard"))
                        .setLuxPrice(rs.getInt("lux_price"))
                        .setBoughtStandard(rs.getInt("bought_standard"))
                        .setStandardPrice(rs.getInt("standard_price"))
                        .setShip(shipDAO.getShip(rs.getLong("ship_id"), path))
                        .setVoyageStations(stationDAO.getStations(rs.getLong("voyage.id")))
                        .build();
                voyages.add(tempt);
            }
            logger.info("Latest voyages get");
            return voyages;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public BoughtTickets getBoughtTicket(long id, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT bought_ticket.id,user_id,voyage_id, status.name,total_price, tickets_count, type FROM bought_ticket " +
                    "INNER JOIN status " +
                    "ON status_id = status.id " +
                    "WHERE bought_ticket.id = ? ")){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Voyage voyage = getVoyage(rs.getLong("voyage_id"),shipDAO,stationDAO,path);
                BoughtTickets tempt = new BoughtTickets.Builder()
                        .setId(rs.getLong("bought_ticket.id"))
                        .setVoyage(voyage)
                        .setType(rs.getString("type"))
                        .setTicketsCount(rs.getInt("tickets_count"))
                        .setTotalPrice(rs.getInt("total_price"))
                        .setUserId(rs.getLong("user_id"))
                        .setStatus(rs.getString("name"))
                        .build();
                logger.info("Bought ticket get");
                return tempt;
            }
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
        return null;
    }

    public List<BoughtTickets> getBoughtTickets(long userId, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
            PreparedStatement ps = con.prepareStatement ("SELECT bought_ticket.id,user_id,voyage_id,status.name,total_price, tickets_count, type FROM bought_ticket " +
                    "INNER JOIN status " +
                    "ON status_id = status.id " +
                    "WHERE user_id = ? " +
                    "ORDER BY bought_ticket.id DESC")){
            ps.setLong(1,userId);
            ResultSet rs = ps.executeQuery();
            List<BoughtTickets> tickets = new ArrayList<>();
            while(rs.next()){
                Voyage voyage = getVoyage(rs.getLong("voyage_id"),shipDAO,stationDAO,path);
                BoughtTickets tempt = new BoughtTickets.Builder()
                        .setId(rs.getLong("bought_ticket.id"))
                        .setVoyage(voyage)
                        .setType(rs.getString("type"))
                        .setTicketsCount(rs.getInt("tickets_count"))
                        .setTotalPrice(rs.getInt("total_price"))
                        .setUserId(rs.getLong("user_id"))
                        .setStatus(rs.getString("name"))
                        .build();
                tickets.add(tempt);
            }
            logger.info("Bought tickets get");
            return tickets;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public List<BoughtTickets> getAcceptTickets(ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
                Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT bought_ticket.id,user_id,voyage_id,status.name, tickets_count, total_price, type FROM bought_ticket " +
                "INNER JOIN status " +
                "ON status_id = status.id " +
                "WHERE status_id = 3 " +
                "ORDER BY bought_ticket.id DESC")){
            List<BoughtTickets> boughtTickets = new ArrayList<>();
            while(rs.next()){
                Voyage voyage = getVoyage(rs.getLong("voyage_id"),shipDAO,stationDAO,path);
                BoughtTickets tempt = new BoughtTickets.Builder()
                        .setId(rs.getLong("bought_ticket.id"))
                        .setVoyage(voyage)
                        .setType(rs.getString("type"))
                        .setTicketsCount(rs.getInt("tickets_count"))
                        .setTotalPrice(rs.getInt("total_price"))
                        .setUserId(rs.getLong("user_id"))
                        .setStatus(rs.getString("name"))
                        .build();
                boughtTickets.add(tempt);
            }
            logger.info("Tickets to accept get");
            return boughtTickets;
        }catch (SQLException e){
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public List<Voyage> getSort(String minPrice,String maxPrice,String startStationId,
                                String stationId, String isFinal, String startDate, String endDate,String sortBy,String voyageDuration, StationDAO stationDAO, ShipDAO shipDAO, String path) throws SQLException {
        List<Voyage> voyages = getAllTickets(sortBy.equals("voyage duration")||sortBy.equals("stations count")?"":sortBy,shipDAO,stationDAO, path);
        if(sortBy.equals("voyage duration")) {
            voyages = voyages.stream().sorted((o1, o2) -> {
                if(o1.getVoyageDuration()==o2.getVoyageDuration()){
                    return 0;
                }
                return o1.getVoyageDuration()>o2.getVoyageDuration()?1:-1;
            }).collect(Collectors.toList());
        }else if(sortBy.equals("stations count")){
            voyages = voyages.stream().sorted((o1, o2) -> {
                if(o1.getVoyageStations().size()==o2.getVoyageStations().size()){
                    return 0;
                }
                return o1.getVoyageStations().size()>o2.getVoyageStations().size()?1:-1;
            }).collect(Collectors.toList());
        }
        if(minPrice!=null&& !minPrice.isBlank()&&!minPrice.equals("0")){
            voyages = voyages.stream()
                    .filter(i->i.getLuxPrice()>=Integer.parseInt(minPrice))
                    .collect(Collectors.toList());
        }
        if(maxPrice!=null&& !maxPrice.isBlank()&&!maxPrice.equals("0")){
            voyages = voyages.stream()
                    .filter(i->i.getStandardPrice()<=Integer.parseInt(maxPrice))
                    .collect(Collectors.toList());
        }
        if(maxPrice!=null&&!maxPrice.isBlank()&&minPrice!=null&&!maxPrice.isBlank()&&!minPrice.equals("0")&&!maxPrice.equals("0")){
            voyages=voyages.stream()
                    .filter(i-> i.getStandardPrice() >= Integer.parseInt(minPrice) || i.getLuxPrice() <= Integer.parseInt(maxPrice))
                    .collect(Collectors.toList());
        }
        if(startStationId!=null && !startStationId.isBlank()){
            voyages = voyages.stream()
                    .filter(i->i.getVoyageStations().get(0).getStation().getId()==Long.parseLong(startStationId))
                    .collect(Collectors.toList());
        }
        if(isFinal==null||!isFinal.equals("on")||isFinal.isBlank()){
            if(stationId!= null && !stationId.isBlank()){
            voyages = voyages.stream()
                    .filter(i-> {
                        try {
                            return i.getVoyageStations().contains(new VoyageStation(stationDAO.getStation(Long.parseLong(stationId))));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
            }
        }else{
            if(stationId!= null && !stationId.isBlank()){
                voyages = voyages.stream()
                        .filter(i-> {
                            try {
                                return i.getVoyageStations().get(i.getVoyageStations().size() - 1).equals(new VoyageStation(stationDAO.getStation(Long.parseLong(stationId))));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
            }
        }
        if(startDate!=null&&!startDate.isBlank()){
            voyages = voyages.stream()
                    .filter(i-> new Date(i.getVoyageStations().get(0).getDepartureDate().getTime()).toString().equals( Date.valueOf(startDate).toString()))
                    .collect(Collectors.toList());
        }
        if(endDate!=null&&!endDate.isBlank()){
            voyages = voyages.stream()
                    .filter(i-> new Date(i.getVoyageStations().get(i.getVoyageStations().size() - 1).getArrivalDate().getTime()).toString().equals(Date.valueOf(endDate).toString()))
                    .collect(Collectors.toList());
        }
        if(voyageDuration!=null&&!voyageDuration.isBlank()){
            voyages = voyages.stream()
                    .filter(i->i.getVoyageDuration()==Long.parseLong(voyageDuration))
                    .collect(Collectors.toList());
        }
        logger.info("Sorted tickets get");
        return voyages;
    }

    public void deleteVoyage(long id) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("DELETE FROM voyage WHERE id = ?");
            ps.setLong(1,id);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Voyage deleted");
        }catch (SQLException e){
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

    public void updateVoyageGeneral(Voyage voyage) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE voyage SET ship_id = ?, standard_price = ?, lux_price =? WHERE id = ?");
            ps.setLong(1,voyage.getShip().getId());
            ps.setInt(2,voyage.getStandardPrice());
            ps.setInt(3,voyage.getLuxPrice());
            ps.setLong(4,voyage.getId());
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Voyage general updated");
        }catch (SQLException e){
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

    public void setSaleVoyage(Voyage voyage) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE voyage SET sale = ?, sale_standard = ?, sale_lux = ? WHERE id  =?");
            ps.setBoolean(1,true);
            ps.setInt(2,voyage.getSaleStandardPrice());
            ps.setInt(3,voyage.getSaleLuxPrice());
            ps.setLong(4,voyage.getId());
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Sale price set");
        }catch (SQLException e){
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

    public void insertToFavorite(long voyageId, long userId) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("INSERT INTO favorite_ticket(voyage_id, user_id) VALUES(?,?)");
            ps.setLong(1,voyageId);
            ps.setLong(2,userId);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Added to favorite");
        }catch (SQLException e){
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

    public void deleteFromFavorite(long voyageId, long userId) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("DELETE FROM favorite_ticket WHERE voyage_id = ? AND user_id = ?");
            ps.setLong(1,voyageId);
            ps.setLong(2,userId);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Deleted from favorite");
        }catch (SQLException e){
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

    public void setStandardVoyage(long id) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE voyage SET sale = ?, sale_standard = ?, sale_lux = ? WHERE id  =?");
            ps.setBoolean(1,false);
            ps.setNull(2,Types.NULL);
            ps.setNull(3,Types.NULL);
            ps.setLong(4,id);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Sale removed");
        }catch (SQLException e){
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

    public void setBoughtStatus(long id, long statusId) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE bought_ticket SET status_id = ? WHERE id = ? AND status_id = ? OR (? = 4 AND status_id = 4) OR (? = 9 AND status_id < 5)");
            ps.setLong(1,statusId);
            ps.setLong(2,id);
            ps.setLong(3,statusId-1);
            ps.setLong(4,statusId-2);
            ps.setLong(5,statusId);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Status set");
        }catch (SQLException e){
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

    public void insertBoughtTicket(long voyageId, long userId, String type, int children, int adult, ShipDAO shipDAO, StationDAO stationDAO, String path) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("INSERT INTO bought_ticket(user_id, voyage_id, status_id, total_price, tickets_count,type) VALUES(?,?,?,?,?,?)");
            ps.setLong(1,userId);
            ps.setLong(2,voyageId);
            ps.setLong(3,1);
            int totalPrice;
            Voyage tempt = getVoyage(voyageId, shipDAO, stationDAO, path);
            if(tempt.isSale()){
                totalPrice = type.equals("lux")?tempt.getSaleLuxPrice()*adult+tempt.getSaleLuxPrice()*70/100*children:tempt.getSaleStandardPrice()*adult+tempt.getSaleStandardPrice()*70/100*children;
            }else{
                totalPrice = type.equals("lux")?tempt.getLuxPrice()*adult+tempt.getLuxPrice()*70/100*children:tempt.getStandardPrice()*adult+tempt.getStandardPrice()*70/100*children;
            }
            ps.setInt(4,totalPrice);
            ps.setInt(5,children+adult);
            ps.setString(6,type);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
            logger.info("Ticket bought");
        }catch (SQLException e){
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

    public void updateBoughtVoyage(Voyage voyage) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE voyage SET bought_lux = ?, bought_standard = ? WHERE  id = ?");
            ps.setLong(1,voyage.getBoughtLux());
            ps.setLong(2,voyage.getBoughtStandard());
            ps.setLong(3,voyage.getId());
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
        }catch (SQLException e){
            if(con!=null){
                con.rollback();
                con.close();
            }
            e.printStackTrace();
        }finally {
            if(con!=null){
                con.close();
            }
        }
    }

    public void allLuxBought(long id) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE bought_ticket SET status_id = 5 WHERE voyage_id =? AND type = lux");
            ps.setLong(1,id);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
        }catch (SQLException e){
            if(con!=null){
                con.rollback();
                con.close();
            }
            e.printStackTrace();
        }finally {
            if(con!=null){
                con.close();
            }
        }
    }

    public void allStandardBought(long id) throws SQLException {
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE bought_ticket SET status_id = 5 WHERE voyage_id =? AND type = standard");
            ps.setLong(1,id);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            con.commit();
        }catch (SQLException e){
            if(con!=null){
                con.rollback();
                con.close();
            }
            e.printStackTrace();
        }finally {
            if(con!=null){
                con.close();
            }
        }
    }
}
