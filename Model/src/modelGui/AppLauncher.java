package modelGui;

import java.awt.Dimension;

import javax.swing.JFrame;


import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class AppLauncher {
    public AppLauncher() {
        JFrame frame = new Model();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        // if (frameSize.height > screenSize.height) {
        frameSize.height = screenSize.height;
        // }
        //if (frameSize.width > screenSize.width) {
        frameSize.width = screenSize.width;
        // }
        frame.setSize(screenSize.width-40,screenSize.height-100);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AppLauncher();
    }

}

