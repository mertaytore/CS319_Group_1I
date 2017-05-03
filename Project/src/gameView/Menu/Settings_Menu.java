package gameView.Menu;

import gameView.Game_Screen;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by mertaytore on 24/4/17.
 */
public class Settings_Menu extends Menu {

    private  JLabel pl1;
    private  JLabel pl2;
    private JTextField up;
    private JTextField down;
    private JTextField left;
    private JTextField right;
    private JTextField fire;
    private JTextField land;
    private JTextField up2;
    private JTextField down2;
    private JTextField left2;
    private JTextField right2;
    private JTextField fire2;
    private JTextField land2;
    private JButton change;
    private JButton returnB;
    private JPanel settingsPanel;
    private HashMap playerSettingsMap;
    private BasicOptionPaneUI.ButtonActionListener settingsListener;

    public Settings_Menu(JFrame frame) {
        // inherited frame and background image from Menu
        super(frame);
        playerSettingsMap = new HashMap();
        displaySettings();
    }

    public  void displaySettings(){


        change = new JButton("Change Settings");
        returnB = new JButton("Back to Main Menu");

        pl1.setForeground(Color.white);
        pl2.setForeground(Color.white);

        settingsPanel=new JPanel(){
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        settingsPanel.add(change);
        settingsPanel.add(returnB);

        settingsPanel.setPreferredSize(new Dimension(500,500));

        frame.getContentPane().add(settingsPanel);
        frame.pack();
        frame.setVisible(true);

        change.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){


                System.out.println("settings changed");
            }
        });

        returnB.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.getContentPane().remove(settingsPanel);
               displayMainMenu();
            }
        });
    }
}
