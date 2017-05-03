package gameView;

import Game_Controller.Input_Handler;
import Game_Model.Player;
import entityModel.Items.*;
import gameView.Menu.Main_Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private Highscores scores;
    int delay = 1000;

    int min = 1; int sec;
    int score1; int score2;

    public Game_Screen(Input_Handler input_handler, Player player1, Player player2) {

        frame = new JFrame();
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setMinimumSize( new Dimension(500,500));

        scores = new Highscores();
        p1score = new JLabel("");
        p2score = new JLabel("");
        p1health = new JLabel("");
        p2health = new JLabel("");
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
                                p1health.setText("Player1 Health : " + t.getHealth() + "  ");
                            else if(t.getPlayerNo() == 2)
                                p2health.setText("Player2 Health : " + t.getHealth() + "  ");
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

                        g.drawImage(op.filter(img,null),  j*getWidth()/10, 40 + i*(getHeight()-40)/10,
                                getWidth()/10, (getHeight() - 40)/10, this);
                        if(pathname2 != "") {
                            op = rotate(img2, rot);
                            g.drawImage(op.filter(img2, null), j * getWidth() / 10 + width, 40 + i * (getHeight()-40) / 10 + height,
                                    getWidth() / 10 - 2 * width, (getHeight() - 40) / 10 - 2 * height, this);
                        }
                    }
                }
            }
        };

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.setPreferredSize(new Dimension(500, 500));
        gamePanel.add(p1score);
        gamePanel.add(p2score);
        gamePanel.add(p1health);
        gamePanel.add(p2health);
        gamePanel.add(time);
        gamePanel.addKeyListener( new KeyListener() {
            public void keyPressed(KeyEvent e){
                input_handler.checkInput(e.getKeyCode());
            }
            public void keyReleased(KeyEvent e){}
            public void keyTyped(KeyEvent e){}
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
        frame.getContentPane().remove(gamePanel);
        JButton finishB = new JButton("Finish Game");
        JPanel finish = new JPanel();
        scores.compareScores(player1.getPlayerName(), score1);
        scores.compareScores(player2.getPlayerName(), score2);
        finish.add(p1score);
        finish.add(p2score);
        finish.add(finishB);
        finishB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main_Menu main_menu = new Main_Menu(null);
            }
        });
        frame.getContentPane().add(finish);
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
}