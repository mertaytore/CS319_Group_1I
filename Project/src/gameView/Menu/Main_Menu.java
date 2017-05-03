package gameView.Menu;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
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

    public Main_Menu(JFrame frame) {
        // inherited frame and background image from Menu
        super(frame);
        displayMain();
    }

    public  void displayMain(){

        playGameButton = new JButton("Play Game");
        settingsButton = new JButton("Settings");
        highscoresButton = new JButton("Highscores");
        creditsButton = new JButton("Info and Credits");

        playGameButton.setPreferredSize(new Dimension(200, 40));
        settingsButton.setPreferredSize(new Dimension(200, 40));
        highscoresButton.setPreferredSize(new Dimension(200, 40));
        creditsButton.setPreferredSize(new Dimension(200, 40));

        buttonPanel=new JPanel(){
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        buttonPanel.setPreferredSize(new Dimension(500,500));


        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(20, 100, 20, 100);

        buttonPanel.add(playGameButton, gbc);
        buttonPanel.add(settingsButton, gbc);
        buttonPanel.add(highscoresButton, gbc);
        buttonPanel.add(creditsButton, gbc);

        frame.getContentPane().add(buttonPanel);
        frame.pack();
        frame.setVisible(true);

        playGameButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){

                frame.getContentPane().remove(buttonPanel);
                displayGameOptions();
            }
        });

        settingsButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){

                frame.getContentPane().remove(buttonPanel);
                displaySettingsMenu();
            }
        });

        highscoresButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){

                frame.getContentPane().remove(buttonPanel);
                displayHighscoresMenu();
            }
        });

        creditsButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){

                frame.getContentPane().remove(buttonPanel);
                displayCredits();
            }
        });
    }
}
