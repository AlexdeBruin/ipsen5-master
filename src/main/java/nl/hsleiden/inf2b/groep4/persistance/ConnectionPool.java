package nl.hsleiden.inf2b.groep4.persistance;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionPool extends nl.hsleiden.inf2b.groep4.persistance.ObjectPool<Connection>{

    private String dsn, user, password;

    @Inject
    public ConnectionPool(String driver, String dsn, String user, String password){
        super();
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.dsn = dsn;
        this.user = user;
        this.password = password;
    }

    @Override
    protected Connection create() {
        try{
           return (DriverManager.getConnection("jdbc:postgresql://localhost:5432/ipose", "postgres", "ipsen12345"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean validate(Connection o) {
        try {
            return (!o.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void expire(Connection o) {
        try{
            o.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
