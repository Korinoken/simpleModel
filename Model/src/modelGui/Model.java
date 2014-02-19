package modelGui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import modelLogic.TwoEntityModel;

import qrandom.QRBG;
import qrandom.ServiceDeniedException;


public class Model extends JFrame {
    @SuppressWarnings("compatibility:639846845986360491")
    private static final long serialVersionUID = 1L;
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private ImageIcon imageOpen = new ImageIcon(Model.class.getResource("openfile.gif"));
    private ImageIcon imageClose = new ImageIcon(Model.class.getResource("closefile.gif"));
    private ImageIcon imageHelp = new ImageIcon(Model.class.getResource("help.gif"));
    protected DefaultTableModel tableModel = new DefaultTableModel();
    private JTable events = new JTable(tableModel);
    protected long startTime;
    protected JButton jButton1 = new JButton();
    protected JTextArea jTextArea1 = new JTextArea();
    protected JProgressBar progress = new JProgressBar(0, 100);
    protected JEditorPane jEditorPane1 = new JEditorPane();
    protected JLabel jLabel1 = new JLabel();
    protected JTextArea finalStats = new JTextArea();
    protected JEditorPane jEditorPane2 = new JEditorPane();
    protected JLabel jLabel2 = new JLabel();
    protected JButton jButton2 = new JButton();
    private ModelRunner worker;
    protected JCheckBox jCheckBox1 = new JCheckBox();
    private QueryTableModel queryModel;
    private JButton jButtonRefresh = new JButton();
    private JButton jButtonNext = new JButton();
    private JButton jButtonPrevious = new JButton();
    private JLabel jLabel3 = new JLabel();
    private Thread queryTableThread;
    private  QRBG random = new QRBG("Korinoken", "Agetn@Paranoia");
    private boolean useQrandom = true;
    public Model() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.queryModel = new QueryTableModel();

        this.events.setModel(this.queryModel);
        this.jEditorPane1.setText("500");
        progress.setStringPainted(true);
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(layoutMain);
        panelCenter.setLayout(null);
        this.setSize(new Dimension(911, 538));
        this.setBounds(new Rectangle(10, 10, 1200, 800));
        panelCenter.setSize(100, 50);
        menuFile.setText("File");
        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileExit_ActionPerformed(ae);
            }
        });
        menuHelp.setText("Help");
        menuHelpAbout.setText("About");
        menuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                helpAbout_ActionPerformed(ae);
            }
        });
        statusBar.setText("");

        events.setFillsViewportHeight(true);

        jButton1.setText("Run");
        jButton1.setBounds(new Rectangle(85, 85, 81, 22));
        jButton1.setActionCommand("Run");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        jTextArea1.setBounds(new Rectangle(335, 10, 395, 315));
        jTextArea1.setDragEnabled(true);
        jTextArea1.setAutoscrolls(false);
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        panelCenter.add(jLabel3, null);
        panelCenter.add(jButtonPrevious, null);
        panelCenter.add(jButtonNext, null);
        panelCenter.add(jButtonRefresh, null);
        panelCenter.add(jCheckBox1, null);
        panelCenter.add(jButton2, null);
        panelCenter.add(jLabel2, null);
        panelCenter.add(jEditorPane2, null);
        panelCenter.add(finalStats, null);
        panelCenter.add(jLabel1, null);
        panelCenter.add(jEditorPane1, null);
        panelCenter.add(jButton1, null);
        JScrollPane scroll = new JScrollPane(events);
        scroll.setPreferredSize(new Dimension(100, 200));
        jLabel3.setText("jLabel3");
        jLabel3.setBounds(new Rectangle(375, 85, 41, 16));
        jButtonPrevious.setText("<<");
        jButtonPrevious.setBounds(new Rectangle(370, 130, 65, 20));
        jButtonPrevious.setActionCommand("Previous");
        jButtonPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton5_actionPerformed(e);
            }
        });
        jButtonNext.setText(">>");
        jButtonNext.setBounds(new Rectangle(435, 130, 65, 20));
        jButtonNext.setActionCommand("Next");
        jButtonNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton4_actionPerformed(e);
            }
        });
        jButtonRefresh.setText("Refresh");
        jButtonRefresh.setBounds(new Rectangle(370, 110, 130, 20));
        jButtonRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton3_actionPerformed(e);
            }
        });
        jCheckBox1.setText("Random Seed");
        jCheckBox1.setBounds(new Rectangle(245, 45, 105, 20));
        jCheckBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jCheckBox1_actionPerformed(e);
            }
        });
        jButton2.setText("Cancel");
        jButton2.setBounds(new Rectangle(165, 85, 81, 22));
        jButton2.setActionCommand("Cancel");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton2_actionPerformed(e);
            }
        });
        jLabel2.setText("Seed");
        jLabel2.setBounds(new Rectangle(25, 50, 95, 15));
        jEditorPane2.setText("1000");
        jEditorPane2.setBounds(new Rectangle(130, 47, 105, 20));
        finalStats.setBounds(new Rectangle(820, 5, 330, 175));
        finalStats.setEditable(false);
        progress.setBounds(new Rectangle(5, 165, 800, 30));
        jEditorPane1.setBounds(new Rectangle(130, 12, 103, 20));
        jLabel1.setText("System Life time");
        jLabel1.setBounds(new Rectangle(25, 15, 110, 15));
        panelCenter.setPreferredSize(new Dimension(100, 200));
        this.add(this.progress);
        this.add(scroll, BorderLayout.CENTER);
        this.getContentPane().add(panelCenter, BorderLayout.NORTH);
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new Model_AboutBoxPanel1(), "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        startTime = System.currentTimeMillis();
        this.progress.setValue(0);
        this.jButton1.setEnabled(false);
        long r;
        if (this.jCheckBox1.isSelected()) {
           
            r = Long.valueOf(jEditorPane2.getText());
            try {
                if (this.useQrandom) {
                r = random.getLong();}
                else {
                    r = Math.round((Math.random() * 1000000));
                }
            } catch (IOException f) {
                f.printStackTrace();
                this.useQrandom = false;
                r = Math.round((Math.random() * 1000000));
            } catch (ServiceDeniedException f) {
                f.printStackTrace();
                this.useQrandom = false;
                r = Math.round((Math.random() * 1000000));
            }
            this.jEditorPane2.setText(Long.toString(r));
        }
        TwoEntityModel model = new TwoEntityModel(Long.valueOf(jEditorPane1.getText()));
        model.setSeed(new Long(this.jEditorPane2.getText()));
        worker = new ModelRunner(model, this);
        try {
            worker.execute();
        } catch (Exception f) {
            f.printStackTrace();
        }
        //  this.queryModel.setQuery();
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        if (worker != null) {
            worker.cancel(true);
        }
    }

    private void jCheckBox1_actionPerformed(ActionEvent e) {
        this.jEditorPane2.setEnabled(!this.jEditorPane2.isEnabled());
    }

    private void jButton3_actionPerformed(ActionEvent e) {
        this.queryModel.setPage(1);
        this.refreshTable();
    }

    private void jButton4_actionPerformed(ActionEvent e) {
        this.queryModel.setPage(this.queryModel.getPage() + 1);
        refreshTable();
    }

    private void jButton5_actionPerformed(ActionEvent e) {
        this.queryModel.setPage(this.queryModel.getPage() - 1);
        refreshTable();
    }

    public void refreshTable() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                queryModel.setQuery();
            }
        };
        if (queryTableThread == null || !queryTableThread.isAlive()) {
            this.queryTableThread = new Thread(task);
            queryTableThread.start();
        }
//        new Thread().isAlive();
    }
}
