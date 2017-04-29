package gameView.Menu;

import gameView.Game_Screen;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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
    private BasicOptionPaneUI.ButtonActionListener settingsListener;

    public Settings_Menu() {
        // inherited frame and background image from Menu
        super();
        displaySettings();
    }

    public  void displaySettings(){

        pl1 = new JLabel("player 1 control");
        pl2 = new JLabel("player 2 control");
        up = new JTextField("up key");
        down = new JTextField("down key");
        right = new JTextField("right key");
        left = new JTextField("left key");
        fire = new JTextField("fire key");
        land = new JTextField("land key");
        up2 = new JTextField("up key");
        down2 = new JTextField("down key");
        right2 = new JTextField("right key");
        left2 = new JTextField("left key");
        fire2 = new JTextField("fire key");
        land2 = new JTextField("land key");
        change = new JButton("Change Settings");
        returnB = new JButton("return");

        pl1.setForeground(Color.white);
        pl2.setForeground(Color.white);

        settingsPanel=new JPanel(){
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        settingsPanel.add(pl1);
        settingsPanel.add(up);
        settingsPanel.add(down);
        settingsPanel.add(right);
        settingsPanel.add(left);
        settingsPanel.add(fire);
        settingsPanel.add(land);
        settingsPanel.add(pl2);
        settingsPanel.add(up2);
        settingsPanel.add(down2);
        settingsPanel.add(right2);
        settingsPanel.add(left2);
        settingsPanel.add(fire2);
        settingsPanel.add(land2);
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

               displayMainMenu();
            }
        });
    }
}
