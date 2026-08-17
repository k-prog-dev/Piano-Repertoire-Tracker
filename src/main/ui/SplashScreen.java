package ui;

import javax.swing.*;
import java.awt.*;

// constructs a splash screen to be opened when the repertoire
// GUI is run
public class SplashScreen extends JWindow {

    public SplashScreen() {
        JLabel splashLabel = new JLabel(new ImageIcon("./data/KEYNOTES.png"));
        getContentPane().add(splashLabel, BorderLayout.CENTER);
        setSize(700, 500);
    }

    // EFFECTS: constructs the time frame for the splash screen to be shown
    public void showSplash() {
        setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
        dispose();
    }
}