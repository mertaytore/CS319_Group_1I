package gameView.Menu;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mertaytore on 30/4/17.
 */
public class Credits_Screen extends Menu {
    private JButton returnB;
    private JPanel creditsPanel;
    private BasicOptionPaneUI.ButtonActionListener scoresListener;

    public Credits_Screen(JFrame frame) {
        // inherited frame and background image from Menu
        super(frame);
        displayCredits();
    }

    public void displayCredits(){

        returnB = new JButton("return");

        creditsPanel=new JPanel(){
            public void paintComponent(Graphics g){
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        creditsPanel.add(returnB);

        creditsPanel.setPreferredSize(new Dimension(500,500));

        frame.getContentPane().add(creditsPanel);
        frame.pack();
        frame.setVisible(true);

        returnB.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.getContentPane().remove(creditsPanel);
                displayMainMenu();
            }
        });

    }

}
