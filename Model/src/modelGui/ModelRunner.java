package modelGui;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import javax.swing.table.DefaultTableModel;


import modelLogic.DBManager;
import modelLogic.ModelStats;
import modelLogic.TwoEntityModel;

import oracle.jdbc.OraclePreparedStatement;

public final class ModelRunner extends SwingWorker implements Observer {
    private TwoEntityModel model;
    private DefaultTableModel output;
    private JProgressBar progress;
    private int rowCount;
    private double lifeTime;
    private JButton startButton;
    private Connection dbConnection;
    private PreparedStatement stat;
    private JTextArea finalStats;
    private int recordNumber;

    public ModelRunner(TwoEntityModel model, Model modelFrame) {
        super();
        dbConnection = DBManager.getConnection();
        this.model = model;
        model.addObserver(this);
        this.output = modelFrame.tableModel;
        this.progress = modelFrame.progress;
        this.startButton = modelFrame.jButton1;
        this.lifeTime = model.getLifeTime();
        rowCount = 0;
        this.finalStats = modelFrame.finalStats;
        this.recordNumber = 0;
    }

    @Override
    protected Object doInBackground() throws Exception {
        stat = dbConnection.prepareStatement("alter index log_number unusable");
        stat.execute();
        dbConnection.prepareStatement("alter session set skip_unusable_indexes=true");
        stat.execute();
        stat = dbConnection.prepareStatement("truncate table model_log");

        stat.execute();
        stat.close();

        this.dbConnection.commit();
        stat = dbConnection.prepareStatement("alter index log_number unusable");
        stat.execute();
        stat =
dbConnection.prepareStatement("insert into model_log (model_time,event_type,queue_size,entity_type,next_a,next_b,processing_done,server_free,log_number) values(?,?,?,?,?,?,?,?,?)");
        ((OraclePreparedStatement)stat).setExecuteBatch(100000);
        model.run();
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        ModelStats stats;
        double modelTime;
        double lifeTime = model.getLifeTime();
        if (arg instanceof ModelStats) {
            String type;
            stats = (ModelStats)arg;
            modelTime = stats.getEvent().getTime();
            stats = (ModelStats)arg;
            modelTime = stats.getEvent().getTime();
            if (stats.getEvent().getEntity() != null) {
                type = stats.getEvent().getEntity().getType().toString();
            } else {
                type = "NA";
            }
            try {
                stat.setDouble(1, modelTime);
                stat.setString(2, stats.getEvent().getType().toString());
                stat.setInt(3, stats.getQueueSize());
                stat.setString(4, type);
                stat.setDouble(5, stats.getNextA());
                stat.setDouble(6, stats.getNextB());
                stat.setDouble(7, stats.getProcessingDone());
                stat.setDouble(8, stats.getServerFree());
                stat.setInt(9, ++this.recordNumber);
                stat.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int percent = (int)Math.round(modelTime / lifeTime * 100);

            progress.setValue(percent - 5);

        }
    }

    private void fillFinalStats() throws SQLException {
        ArrayList<Object[]> stats = this.model.gatherStatistics();
        finalStats.setText("");
        for (Object[] stat : stats) {
            this.finalStats.append((String)stat[0] + (String)stat[1] + "\n");
        }
        stat =
this.dbConnection.prepareStatement("select entity_type, count(model_time) as c from model_log where event_type = 'ENTITY_ARRIVES' group by entity_type");
        finalStats.append("Entities spawned \n");
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            finalStats.append(("Type " + rs.getString("entity_type") + " : " + Integer.toString(rs.getInt("c"))) +
                              "\n");
        }
        rs.close();
    }

    protected void done() {
        super.done();
        if (!this.isCancelled()) {
            try {
                this.dbConnection.commit();
                this.fillFinalStats();
                stat = dbConnection.prepareStatement("alter index log_number rebuild");
                stat.execute();
                progress.setValue(100);
                this.startButton.setEnabled(true);
                stat.close();
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            this.model = null;
            this.finalStats.setText("Task canceled");
            this.progress.setValue(0);
            this.startButton.setEnabled(true);
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}