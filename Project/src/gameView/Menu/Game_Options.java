package gameView.Menu;

import Game_Controller.Input_Handler;
import Game_Model.Game;
import Game_Model.Player;
import gameView.Game_Screen;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.*;

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

    String mapPath = "Project/maps/mapEasy.txt";

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

        p1name = new JTextField("NPC1");
        p2name = new JTextField("NPC2");

        playButton = new JButton("PLAY!");
        returnB = new JButton("Back to Main Menu");

        final JRadioButton easyMap = new JRadioButton("Easy Map");
        final JRadioButton mediumMap = new JRadioButton("Medium Map");
        final JRadioButton hardMap = new JRadioButton("Hard Map");
        final JRadioButton randomMap = new JRadioButton("Random Map");

        easyMap.setForeground(Color.white);
        mediumMap.setForeground(Color.white);
        hardMap.setForeground(Color.white);
        randomMap.setForeground(Color.white);

        easyMap.setMnemonic(KeyEvent.VK_C);
        mediumMap.setMnemonic(KeyEvent.VK_M);
        hardMap.setMnemonic(KeyEvent.VK_P);
        randomMap.setMnemonic(KeyEvent.VK_P);

        easyMap.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                mapPath = "Project/maps/mapEasy.txt";
            }
        });
        mediumMap.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                mapPath = "Project/maps/mapMedium.txt";
            }
        });
        hardMap.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                mapPath = "Project/maps/mapHard.txt";
            }
        });
        randomMap.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                mapPath = "Project/maps/mapRandom.txt";
            }
        });

        ButtonGroup group = new ButtonGroup();

        group.add(easyMap);
        group.add(mediumMap);
        group.add(hardMap);
        group.add(randomMap);

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
        buttonPanel.add(returnB);

        frame.getContentPane().add(buttonPanel);
        frame.pack();
        frame.setVisible(true);

        playButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Player player1 = new Player(1);
                Player player2 = new Player(2);
                player1.setPlayerName(p1name.getText());
                player2.setPlayerName(p2name.getText());
                frame.dispose();
                Input_Handler input_handler = new Input_Handler();
                Game_Screen game_screen = new Game_Screen(input_handler, player1, player2);
                Game game = new Game(game_screen, player1, player2, mapPath);
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
