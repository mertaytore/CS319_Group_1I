package gameView.Menu;

import Game_Model.Game;
import gameView.Game_Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

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

    public Main_Menu() {
        // inherited frame and background image from Menu
        super();
        displayMain();
    }

    public  void displayMain(){

        playGameButton = new JButton("Play Game");
        settingsButton = new JButton("Settings");
        highscoresButton = new JButton("Highscores");
        creditsButton = new JButton("Info and Credits");

        buttonPanel=new JPanel(){
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        buttonPanel.add(playGameButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(highscoresButton);
        buttonPanel.add(creditsButton);
        buttonPanel.setPreferredSize(new Dimension(500,500));

        frame.getContentPane().add(buttonPanel);
        frame.pack();
        frame.setVisible(true);

        playGameButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){

                frame.getContentPane().remove(buttonPanel);
                Game_Screen game_screen = new Game_Screen(frame);
                Game game = new Game(game_screen);
                System.out.println("game screen play");
            }
        });

        settingsButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                displaySettingsMenu();
            }
        });

        highscoresButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                displayHighscoresMenu();
            }
        });

        creditsButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                displayCredits();
                System.out.println("game screen credits display");
            }
        });
    }



}
