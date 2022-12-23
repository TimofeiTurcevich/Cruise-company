package com.my.DB.DAO;

import com.my.classes.User;

import java.sql.Date;
import java.sql.SQLException;

public interface UserDAO {
    public User getUser(String email, String pass) throws SQLException;
    public User insertUser(String email, String pass, Date date, String firstName, String lastName, String phone, String code) throws SQLException;
    public User getUserToVerify(long id) throws SQLException;
    public void changeLang(long id, String lang) throws SQLException;
    public void updateRole(long id) throws SQLException;
    public void changePass(String oldPass,String newPass, long id) throws SQLException;
}
