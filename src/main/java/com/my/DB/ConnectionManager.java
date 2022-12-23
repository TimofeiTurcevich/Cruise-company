package com.my.DB;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
    private static ConnectionManager instance;
    private DataSource ds;

    private ConnectionManager(){
        try{
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/TestDB");
        }catch (NamingException e){
            e.printStackTrace();
        }
    }
    public static ConnectionManager getInstance() {
        if(instance==null){
            instance = new ConnectionManager();
        }
        return instance;
    }

    public DataSource getDs(){
        return ds;
    }
}
