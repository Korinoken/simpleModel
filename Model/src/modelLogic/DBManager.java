package modelLogic;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class DBManager {
    private static OracleDataSource ds;

    public static Connection getConnection() {
        if (ds == null) {
            try {
                ds = new OracleDataSource();
                ds.setDatabaseName("XE");
                ds.setURL("jdbc:oracle:thin:@127.0.0.1");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection conn = null;
        try {
            conn = ds.getConnection("nikolajs", "newpass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
