package gameView.Menu;

import gameView.Highscores;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        returnB = new JButton("Back to Main Menu");

        Highscores scores = new Highscores();
        String[] playerNames = scores.getHighScoresNames();
        int[] playerScores = scores.getHighScores();
        String scorebuilder = "";
        for (int i = 0; i < 10; ++i){
            scorebuilder = scorebuilder + playerNames[i] + " " + String.valueOf(playerScores[i]) + "\n";
        }

        final JTextArea scoreTable = new JTextArea(scorebuilder,40,100);

        scoreTable.setEditable(false);

        scoresPanel=new JPanel(){
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        scoresPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        scoresPanel.add(scoreTable, gbc);
        scoresPanel.add(returnB, gbc);

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
