package modelGui;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import modelLogic.DBManager;

public class QueryTableModel extends AbstractTableModel {
    @SuppressWarnings("compatibility:-7501143464020158179")
    private static final long serialVersionUID = 4384481141127190419L;
    private Connection dbConnection;
    private int page;
    private PreparedStatement stat;
    private int pageSize;
    Vector cache; // will hold String[] objects . . .
    int colCount;
    String[] headers;

    public QueryTableModel() {
        super();
        this.dbConnection = DBManager.getConnection();
        this.cache = new Vector();
        try {
            stat =
dbConnection.prepareStatement("select model_time, event_type, queue_size, entity_type, next_a, next_b, processing_done, server_free from model_log where model_time >= ?  " +
                "and log_number between  (? * ?)-? and (? * ?)   order by log_number ");
            this.pageSize = 1000;
            this.page = 1;
            stat.setInt(1, 0);
            stat.setInt(2, pageSize);
            stat.setInt(3, 1);
            stat.setInt(4, pageSize - 1);
            stat.setInt(5, pageSize);
            stat.setInt(6, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return this.cache.size();
    }

    public String getColumnName(int i) {
        return headers[i];
    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((String[])cache.elementAt(rowIndex))[columnIndex];
    }

    public void setPage(int page) {
        this.page = page;
        try {
            stat.setInt(3, page);
            stat.setInt(6, page);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getPage() {
        return page;
    }

    public void setQuery() {
        cache = new Vector();
        try {
            stat.execute();
            ResultSet rs = stat.getResultSet();
            ResultSetMetaData meta = rs.getMetaData();
            colCount = meta.getColumnCount();

            headers = new String[colCount];
            for (int h = 1; h <= colCount; h++) {
                headers[h - 1] = meta.getColumnName(h);
            }

            // and file the cache with the records from our query.  This would not be
            // practical if we were expecting a few million records in response to our
            // query, but we aren't, so we can do this.
            while (rs.next()) {
                String[] record = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                cache.addElement(record);
            }
            fireTableChanged(null); // notify everyone that we have a new table.
        } catch (Exception e) {
            cache = new Vector(); // blank it out and keep going.
            e.printStackTrace();
        }
    }
}
