package com.my.DB;

import com.my.DB.DAO.UserDAO;
import com.my.classes.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserDB implements UserDAO {
    static final Logger logger = LogManager.getLogger(UserDB.class);

    public User getUser(String email, String pass) throws SQLException {
        try (Connection con = ConnectionManager.getInstance().getDs().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT user.*, role.name FROM user  INNER JOIN role ON user.role = role.id WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User tempt = new User.Builder()
                        .setEmail(rs.getString("email"))
                        .setBirthDate(rs.getDate("birth_date"))
                        .setId(rs.getLong("id"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setRole(rs.getString("name"))
                        .setPhone(rs.getString("phone"))
                        .setLang(rs.getString("lang"))
                        .build();
                logger.info("User get");
                return tempt;
            }
        } catch (SQLException e) {
            logger.info("SQL error");
            e.printStackTrace();
            throw new SQLException();
        }
        return null;
    }

    public User insertUser(String email, String pass, Date date, String firstName, String lastName, String phone, String code) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("INSERT INTO user(email,password,last_name, first_name, birth_date,role, phone, code) VALUES(?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, pass);
            ps.setString(3, lastName);
            ps.setString(4, firstName);
            ps.setDate(5, date);
            ps.setLong(6, 1);
            ps.setString(7, phone);
            ps.setString(8, code);
            ps.executeUpdate();
            con.commit();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                User tempt = new User.Builder()
                        .setEmail(email)
                        .setBirthDate(date)
                        .setId(rs.getLong(1))
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setRole("client")
                        .setPhone(phone)
                        .build();
                logger.info("User inserted");
                return tempt;
            }
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
        return null;
    }

    public void updateRole(long id) throws SQLException{
        Connection con = null;
        try{
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE user SET role = ? WHERE id = ? AND role = 1");
            ps.setLong(1,2);
            ps.setLong(2,id);
            if(ps.executeUpdate()<1){
                throw new SQLException();
            }
            logger.info("Role updated");
            con.commit();
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

    public User getUserToVerify(long id) throws SQLException {
        try(Connection con = ConnectionManager.getInstance().getDs().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE id = ? AND role = 1")){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                User tempt = new User.Builder()
                        .setEmail(rs.getString("email"))
                        .setBirthDate(rs.getDate("birth_date"))
                        .setId(rs.getLong("id"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setRole("unverified")
                        .setPhone(rs.getString("phone"))
                        .setLang(rs.getString("lang"))
                        .setCode(rs.getString("code"))
                        .build();
                return tempt;
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
        return null;
    }

    public void changePass(String oldPass, String newPass, long id) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE user SET password = ? WHERE id = ? AND password = ?");
            ps.setString(1, newPass);
            ps.setLong(2, id);
            ps.setString(3, oldPass);
            if (ps.executeUpdate() < 1) {
                throw new SQLException();
            }
            con.commit();
            logger.info("Pass changed");
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

    public void changeLang(long id, String lang) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getDs().getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE user SET lang = ? WHERE id = ?");
            ps.setString(1, lang);
            ps.setLong(2, id);
            if (ps.executeUpdate() < 1) {
                throw new SQLException();
            }
            con.commit();
            logger.info("Pass changed");
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
