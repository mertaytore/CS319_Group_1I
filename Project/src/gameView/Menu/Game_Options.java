package gameView.Menu;

import Game_Controller.Input_Handler;
import Game_Model.Game;
import gameView.Game_Screen;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mertaytore on 1/5/17.
 */
public class Game_Options extends Menu {

    private JButton easyMap;
    private JButton mediumMap;
    private JButton hardMap;
    private JButton randomMap;
    private JButton playButton;
    private JButton returnB;
    private JPanel buttonPanel;

    private JLabel player1;
    private JLabel player2;

    private JTextField p1name;
    private JTextField p2name;

    private BasicOptionPaneUI.ButtonActionListener mainListener;

    public Game_Options(JFrame frame) {
        // inherited frame and background image from Menu
        super(frame);
        displayGameOptions();
    }

    public void displayGameOptions(){

        player1 = new JLabel("Player1 name:");
        player1.setForeground(Color.white);
        player2 = new JLabel("Player2 name:");
        player2.setForeground(Color.white);

        p1name = new JTextField("ucankedineydi");
        p2name = new JTextField("adiniunuttumyav");

        easyMap = new JButton("Easy Map");
        mediumMap = new JButton("Medium Map");
        hardMap = new JButton("Hard Map");
        randomMap = new JButton("Random Map");
        playButton = new JButton("PLAY!");
        returnB = new JButton("Back to Main Menu");

        easyMap.setPreferredSize(new Dimension(200, 40));
        mediumMap.setPreferredSize(new Dimension(200, 40));
        hardMap.setPreferredSize(new Dimension(200, 40));
        randomMap.setPreferredSize(new Dimension(200, 40));
        playButton.setPreferredSize(new Dimension(200, 40));


        buttonPanel = new JPanel()
        {
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

        buttonPanel.add(easyMap, gbc);
        buttonPanel.add(mediumMap, gbc);
        buttonPanel.add(hardMap, gbc);
        buttonPanel.add(randomMap, gbc);
        buttonPanel.add(playButton, gbc);
        buttonPanel.add(player1, gbc);
        buttonPanel.add(p1name, gbc);
        buttonPanel.add(player2, gbc);
        buttonPanel.add(p2name, gbc);

        frame.getContentPane().add(buttonPanel);
        frame.pack();
        frame.setVisible(true);

        playButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){

                frame.dispose();
                Input_Handler input_handler = new Input_Handler();
                Game_Screen game_screen = new Game_Screen(input_handler);
                Game game = new Game(game_screen);
                input_handler.setGame(game);
            }
        });

        returnB.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.getContentPane().remove(buttonPanel);
                displayMainMenu();
            }
        });
    }
}
