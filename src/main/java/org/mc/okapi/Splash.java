package org.mc.okapi;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Splash extends JFrame {
	 
    private JLabel imglabel;
    private ImageIcon img;
    private static JProgressBar pbar;
    Thread t = null;
 
    public Splash() {
        super("Splash");
        setSize(404, 310);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        img = new ImageIcon("src/images/ico/extra/micro-human-mice.gif");
        imglabel = new JLabel(img);
        add(imglabel);
        setLayout(null);
        pbar = new JProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(100);
        pbar.setStringPainted(true);
        pbar.setForeground(Color.LIGHT_GRAY);
        imglabel.setBounds(0, 0, 404, 310);
        add(pbar);
        pbar.setPreferredSize(new Dimension(310, 30));
        pbar.setBounds(0, 290, 404, 20);
 
        Thread t = new Thread() {
 
            public void run() {
                int i = 0;
                while (i <= 100) {
                    pbar.setValue(i);
                    try {
                        sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            }
        };
        t.start();
    }
}
