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

        returnB = new JButton("Back to Main Menu");
        final JTextArea credits = new JTextArea("Developers:\n Muhammed Yusuf Satıcı\n Mert Aytore\n Mehmet Orçun Yalçın\n" +
                "\n CS319-01 -- Group I \n Course Instructor: Bora Güngören\n\n " +
                "Battle City is a Java-based desktop application created for the course\n CS319 in Bilkent University, Spring 2017",40,60);

        credits.setEditable(false);

        creditsPanel=new JPanel(){
            public void paintComponent(Graphics g){
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        creditsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        creditsPanel.add(credits, gbc);
        creditsPanel.add(returnB, gbc);

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
