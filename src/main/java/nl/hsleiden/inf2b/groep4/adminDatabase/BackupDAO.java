package nl.hsleiden.inf2b.groep4.adminDatabase;

import com.google.inject.Singleton;
import nl.hsleiden.inf2b.groep4.persistance.ConnectionPool;

import com.google.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Singleton
public class BackupDAO {

    private ConnectionPool pool;

    @Inject
    public BackupDAO(ConnectionPool pool) {
        this.pool = pool;
        this.createFunction();
    }

    void wipeAll(String user) {
        Connection conn = pool.checkout();
        try{
            PreparedStatement wipeDatabase = conn.prepareStatement("SELECT truncate_tables(?)");
            wipeDatabase.setString(1, user);
            wipeDatabase.execute();
        } catch (SQLException SQLE) {
            SQLE.printStackTrace();
        } finally {
            pool.checkIn(conn);
        }

    }

    private void createFunction() {
        Connection conn = pool.checkout();
        try {
            PreparedStatement createFuntion = conn.prepareStatement("CREATE OR REPLACE FUNCTION truncate_tables(username IN VARCHAR) RETURNS void AS $$" +
                    "DECLARE" +
                    "    statements CURSOR FOR" +
                    "        SELECT tablename FROM pg_tables" +
                    "        WHERE tableowner = username AND schemaname = 'public';" +
                    "BEGIN" +
                    "    FOR stmt IN statements LOOP" +
                    "        EXECUTE 'TRUNCATE TABLE ' || quote_ident(stmt.tablename) || ' CASCADE;';" +
                    "    END LOOP;" +
                    "END;" +
                    "$$ LANGUAGE plpgsql;");
            createFuntion.execute();
        } catch (SQLException SQLE) {
            SQLE.printStackTrace();
        } finally {
            pool.checkIn(conn);
        }
    }
}
