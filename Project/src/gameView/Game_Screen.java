package gameView;

import Game_Controller.Input_Handler;
import Game_Model.Player;
import entityModel.Items.*;
import gameView.Menu.Main_Menu;
import gameView.Menu.Settings_Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by mertaytore on 24/4/17.
 */
public class Game_Screen {

    private JFrame frame;
    private JPanel gamePanel;
    private Terrain[][] terrains;
    private JLabel p1score;
    private  JLabel p2score;
    private JLabel p1health;
    private  JLabel p2health;
    private  JLabel time;
    private Timer timer;
    private JButton pauseGame;
    private Highscores scores;
    int delay = 1000;
    boolean isPaused;
    Input_Handler input_handler;
    Player player1;
    Player player2;

    int min = 1; int sec;
    int score1; int score2;
    private final int vol_min = 0;
    private final int vol_max = 16;

    public Game_Screen(Input_Handler input_handler, Player player1, Player player2) {

        frame = new JFrame();
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setMinimumSize( new Dimension(500,500));

        this.input_handler = input_handler;
        this.player1 = player1;
        this.player2 = player2;

        scores = new Highscores();
        p1score = new JLabel("");
        p2score = new JLabel("");
        p1health = new JLabel("");
        p2health = new JLabel("");
        pauseGame = new JButton("Pause");
        time = new JLabel("Remaining Time : " + min + " : " + sec);
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sec == 0) {
                    min--;
                    sec = 59;
                }
                else
                    sec--;
                if(sec == 0 && min == 0) {
                    timer.stop();
                    finishGame( player1, player2);
                }
                time.setText("Remaining Time : " + min + " : " + sec);
            }
        });
        timer.start();
        gamePanel = new JPanel(){
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                BufferedImage img = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
                BufferedImage img2 = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
                String pathname = "" ;
                Obstacle obs;
                Power_Up pow;
                Bullet b;
                Mine m;
                Tank t;

                for(int i = 0; i < terrains.length; i++){
                    for(int j = 0 ; j < terrains[i].length ; j++){
                        String pathname2 ="";
                        int width=0; int height=0;
                        String rot = "";

                        if((b = (Bullet) terrains[i][j].retrieveItemInfo("Bullet")) != null ) {
                            pathname2 = "Project/imageFiles/bullet.png";
                            width = getWidth()/40;
                            height = getHeight()/40;
                            if(b.getDirection().equalsIgnoreCase("Left"))
                                rot = "Left";
                            else if(b.getDirection().equalsIgnoreCase("Right"))
                                rot = "Right";
                            else if(b.getDirection().equalsIgnoreCase("Down"))
                                rot = "Down";
                        }
                        if(( obs = (Obstacle) terrains[i][j].retrieveItemInfo("Obstacle")) != null ) {
                            if(obs.getBrickType().equalsIgnoreCase("Steel"))
                                pathname = "Project/imageFiles/steel_brick.png";
                            else if(obs.getBrickType().equalsIgnoreCase("Brick"))
                                pathname = "Project/imageFiles/ordinary_brick.png";
                            pathname2 = "";
                        }
                        else if( (pow = (Power_Up) terrains[i][j].retrieveItemInfo("Power Up")) != null ){
                            if(pow.getPowerUpType().equalsIgnoreCase("Tank"))
                                pathname = "Project/imageFiles/tank_upgrade.png";
                            if(pow.getPowerUpType().equalsIgnoreCase("Bullet"))
                                pathname = "Project/imageFiles/bullet_upgrade.png";
                            if(pow.getPowerUpType().equalsIgnoreCase("Mine"))
                                pathname = "Project/imageFiles/mine_pickup.png";
                            if(pow.getPowerUpType().equalsIgnoreCase("Time"))
                                pathname = "Project/imageFiles/extra_time.png";
                            if(pow.getPowerUpType().equalsIgnoreCase("Destructor"))
                                pathname = "Project/imageFiles/brick_rider.png";
                        }
                        else if(terrains[i][j] instanceof Ground){
                            pathname = "Project/imageFiles/blank_terrain.png";
                        }
                        if((m = (Mine) terrains[i][j].retrieveItemInfo("Mine")) != null ) {
                            if(m.isVisible()) {
                                pathname2 = "Project/imageFiles/mine.png";
                                width = getWidth() / 40;
                                height = getHeight() / 40;
                            }
                        }
                        if((t = (Tank) terrains[i][j].retrieveItemInfo("Tank")) != null ) {
                            if(t.getLevel() == 1 && t.getPlayerNo() == 1)
                                pathname = "Project/imageFiles/green_level_1.png";
                            else if(t.getLevel() == 2 && t.getPlayerNo() == 1)
                                pathname = "Project/imageFiles/green_level_2.png";
                            else if(t.getLevel() == 3 && t.getPlayerNo() == 1)
                                pathname = "Project/imageFiles/green_level_3.png";
                            else if(t.getLevel() == 4 && t.getPlayerNo() == 1)
                                pathname = "Project/imageFiles/green_level_4.png";
                            else if(t.getLevel() == 1 && t.getPlayerNo() == 2)
                                pathname = "Project/imageFiles/yellow_level_1.png";
                            else if(t.getLevel() == 2 && t.getPlayerNo() == 2)
                                pathname = "Project/imageFiles/yellow_level_2.png";
                            else if(t.getLevel() == 3 && t.getPlayerNo() == 2)
                                pathname = "Project/imageFiles/yellow_level_3.png";
                            else if(t.getLevel() == 4 && t.getPlayerNo() == 2)
                                pathname = "Project/imageFiles/yellow_level_4.png";
                            if(t.getLevel() == 1 && t.isNPC())
                                pathname = "Project/imageFiles/npc_level_1.png";
                            else if(t.getLevel() == 2 && t.isNPC())
                                pathname = "Project/imageFiles/npc_level_2.png";
                            else if(t.getLevel() == 3 && t.isNPC())
                                pathname = "Project/imageFiles/npc_level_3.png";
                            else if(t.getLevel() == 4 && t.isNPC())
                                pathname = "Project/imageFiles/npc_level_4.png";
                            pathname2 = "";
                            if(t.getDirection().equalsIgnoreCase("Left"))
                                rot = "Left";
                            else if(t.getDirection().equalsIgnoreCase("Right"))
                                rot = "Right";
                            else if(t.getDirection().equalsIgnoreCase("Down"))
                                rot = "Down";
                            if(t.getPlayerNo() == 1)
                                p1health.setText(player1.getPlayerName() + " Health : " + t.getHealth() + "  ");
                            else if(t.getPlayerNo() == 2)
                                p2health.setText(player2.getPlayerName() + " Health : " + t.getHealth() + "  ");
                        }
                        if(terrains[i][j] instanceof Bush){
                            pathname = "Project/imageFiles/bush.png";
                            pathname2 = "";
                        }
                        try{
                            img = ImageIO.read( new File(pathname) );
                            if(pathname2 != "")
                                img2 = ImageIO.read( new File(pathname2) );

                        }catch(IOException e){ System.out.print("no man");}

                        AffineTransformOp op = rotate(img, rot);

                        g.drawImage(op.filter(img,null),  j*getWidth()/10, 50 + i*(getHeight()-50)/10,
                                getWidth()/10, (getHeight() - 50)/10, this);
                        if(pathname2 != "") {
                            op = rotate(img2, rot);
                            g.drawImage(op.filter(img2, null), j * getWidth() / 10 + width, 50 + i * (getHeight()-50) / 10 + height,
                                    getWidth() / 10 - 2 * width, (getHeight() - 50) / 10 - 2 * height, this);
                        }
                    }
                }
            }
        };

        gamePanel.setBackground(Color.white);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.setPreferredSize(new Dimension(500, 500));
        gamePanel.add(p1score);
        gamePanel.add(p2score);
        gamePanel.add(p1health);
        gamePanel.add(p2health);
        gamePanel.add(time);
        gamePanel.add(pauseGame);
        gamePanel.addKeyListener( new KeyListener() {
            public void keyPressed(KeyEvent e){
                input_handler.checkInput(e.getKeyCode());
            }
            public void keyReleased(KeyEvent e){}
            public void keyTyped(KeyEvent e){}
        });
        pauseGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });

        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setVisible(true);
    }
    public void displayGame(Terrain[][] terrains) {

        this.terrains = terrains;
        gamePanel.repaint();
    }

    public void displayScore(int score1, int score2, String playerName1, String playerName2){
        if(min !=0 || sec != 0) {
            p1score.setText(playerName1 + " Score : " + score1 + "  ");
            p2score.setText(playerName2 + " Score : " + score2 + "  ");
            this.score1 = score1;
            this.score2 = score2;
        }
    }

    public AffineTransformOp rotate(BufferedImage img, String rot){

        int rotate = 0;
        if(rot.equalsIgnoreCase("Left"))
            rotate = 270;
        else if(rot.equalsIgnoreCase("Down"))
            rotate = 180;
        else if(rot.equalsIgnoreCase("Right"))
            rotate = 90;

        double rotationRequired = Math.toRadians (rotate);
        double locationX = img.getWidth() / 2;
        double locationY = img.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return  op;
    }

    public void finishGame( Player player1, Player player2){
        timer.stop();
        setSec(0);
        frame.getContentPane().remove(gamePanel);
        JButton finishB = new JButton("Finish Game");
        JPanel finish = new JPanel();
        p1score.setText(player1.getPlayerName() + " : " + score1);
        p2score.setText(player2.getPlayerName() + " : " + score2);
        scores.compareScores(player1.getPlayerName(), score1);
        scores.compareScores(player2.getPlayerName(), score2);
        finish.add(p1score);
        finish.add(p2score);
        finish.add(finishB);
        finishB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSec(0);
                frame.dispose();
                Main_Menu main_menu = new Main_Menu(null);
            }
        });
        frame.getContentPane().add(finish);
        frame.pack();
        frame.setVisible(true);
    }

    public void pauseGame(){

        isPaused = true;
        input_handler.setPaused(true);
        timer.stop();
        JButton resume = new JButton("Resume");
        JButton quit = new JButton("Quit");
        JLabel scr1 = new JLabel(p1score.getText());
        JLabel scr2 = new JLabel(p2score.getText());
        JLabel remainTime = new JLabel(time.getText());
        JPanel resumeGame = new JPanel();

        resumeGame.setBackground(Color.white);
        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.restart();
                frame.getContentPane().add(gamePanel);
                frame.getContentPane().remove(resumeGame);
                frame.pack();
                frame.setVisible(true);
                gamePanel.setFocusable(true);
                gamePanel.requestFocusInWindow();
                input_handler.setPaused(false);
                isPaused = false;
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                score1 = player1.getScore() - getMin()*120 - getSec()*2;
                score2 = player2.getScore() - getMin()*120 - getSec()*2;
                setMin(0);
                setSec(0);
                frame.getContentPane().remove(resumeGame);
                timer.stop();
                finishGame( player1, player2);
            }
        });

        JToggleButton sound = new JToggleButton("Enable Sound");
        sound.setSelected(Settings_Menu.isSelected());
        sound.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if(ev.getStateChange()==ItemEvent.SELECTED){
                    Settings_Menu.setSelected(true);
                } else if(ev.getStateChange()==ItemEvent.DESELECTED){
                    Settings_Menu.setSelected(false);
                }
            }
        });

        JSlider volume = new JSlider(JSlider.HORIZONTAL,
                vol_min, vol_max, (int) Settings_Menu.getVol() + 10);
        JLabel volName = new JLabel("  Volume Level Adjustment :  ");
        volName.setForeground(Color.darkGray);

        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Settings_Menu.setVol(volume.getValue() - 10);
            }
        });

        volume.setMajorTickSpacing(4);
        volume.setMinorTickSpacing(1);
        volume.setPaintTicks(true);
        volume.setPaintLabels(true);
        volume.setForeground(Color.darkGray);

        resumeGame.add(scr1);
        resumeGame.add(scr2);
        resumeGame.add(remainTime);
        resumeGame.add(sound);
        resumeGame.add(volName);
        resumeGame.add(volume);
        resumeGame.add(resume);
        resumeGame.add(quit);
        resumeGame.setPreferredSize(new Dimension(500, 500));

        frame.getContentPane().remove(gamePanel);
        frame.getContentPane().add(resumeGame);
        frame.pack();
        frame.setVisible(true);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        if(sec >= 60){
            this.sec = sec % 60;
            setMin(getMin() + 1);
        }
        else
            this.sec = sec;
    }

    public boolean isPaused(){
        return isPaused;
    }
}