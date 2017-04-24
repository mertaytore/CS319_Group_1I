package gameView.Menu;

import gameView.Game_Screen;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.image.BufferedImage;
import java.awt.event.*;

/**
 * Created by mertaytore on 24/4/17.
 */
public class Main_Menu extends Menu{

    private JButton settingsButton;
    private JButton creditsButton;
    private JButton playGameButton;
    private JButton highscoresButton;
    private JPanel buttonPanel;
    private BasicOptionPaneUI.ButtonActionListener mainListener;

    public Main_Menu(Game_Screen screen, JFrame frame, BufferedImage background){
        // inherited frame and background image from Menu
        super(screen, frame, background);

        playGameButton = new JButton("Play Game");
        settingsButton = new JButton("Settings");
        highscoresButton = new JButton("Highscores");
        creditsButton = new JButton("Info and Credits");

        buttonPanel.add(playGameButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(highscoresButton);
        buttonPanel.add(creditsButton);

        frame.add(buttonPanel);

        playGameButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                screen.displayGame();
                System.out.println("game screen play");
            }
        });

        settingsButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                screen.displaySettings();
                System.out.println("game screen settings display");
            }
        });

        highscoresButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                screen.displayHighscores();
                System.out.println("game screen highscores display");
            }
        });

        creditsButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                screen.displayCredits();
                System.out.println("game screen credits display");
            }
        });
    }



}
