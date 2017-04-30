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
public class Score_Menu extends Menu {

    private JButton returnB;
    private JPanel scoresPanel;
    private BasicOptionPaneUI.ButtonActionListener scoresListener;

    public Score_Menu(JFrame frame) {
        // inherited frame and background image from Menu
        super(frame);
        displayScores();
    }

    public void displayScores(){

        returnB = new JButton("return");

        scoresPanel=new JPanel(){
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        scoresPanel.add(returnB);

        scoresPanel.setPreferredSize(new Dimension(500,500));

        frame.getContentPane().add(scoresPanel);
        frame.pack();
        frame.setVisible(true);

        returnB.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.getContentPane().remove(scoresPanel);
                displayMainMenu();
            }
        });

    }
}
