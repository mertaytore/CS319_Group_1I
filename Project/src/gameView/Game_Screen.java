package gameView;

import Game_Controller.Input_Handler;
import entityModel.Items.*;
import gameView.Menu.Main_Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
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
    private JLabel label1;
    private  JLabel label2;
    private JLabel label3;
    private  JLabel label4;

    public Game_Screen(Input_Handler input_handler) {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label1 = new JLabel("Player1 Score : 0");
        label2 = new JLabel("Player2 Score : 0");
        label3 = new JLabel("");
        label4 = new JLabel("");
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

                        if(( obs = (Obstacle) terrains[i][j].retrieveItemInfo("Obstacle")) != null ) {
                            if(obs.getBrickType().equalsIgnoreCase("Steel"))
                                pathname = "Project/imageFiles/steel_brick.png";
                            else if(obs.getBrickType().equalsIgnoreCase("Brick"))
                                pathname = "Project/imageFiles/ordinary_brick.png";
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
                            pathname = "Project/imageFiles/player_base.png";
                        }
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
                        if((m = (Mine) terrains[i][j].retrieveItemInfo("Mine")) != null ) {
                            if(m.isVisible()) {
                                pathname2 = "Project/imageFiles/mine.png";
                                width = getWidth() / 40;
                                height = getHeight() / 40;
                            }
                        }
                        if((t = (Tank) terrains[i][j].retrieveItemInfo("Tank")) != null ) {
                            if(t.getLevel() == 1)
                                pathname = "Project/imageFiles/green_level_1.png";
                            else if(t.getLevel() == 2)
                                pathname = "Project/imageFiles/green_level_2.png";
                            else if(t.getLevel() == 3)
                                pathname = "Project/imageFiles/green_level_3.png";
                            else if(t.getLevel() == 4)
                                pathname = "Project/imageFiles/green_level_4.png";
                            pathname2 = "";
                            if(t.getDirection().equalsIgnoreCase("Left"))
                                rot = "Left";
                            else if(t.getDirection().equalsIgnoreCase("Right"))
                                rot = "Right";
                            else if(t.getDirection().equalsIgnoreCase("Down"))
                                rot = "Down";
                            if(t.getPlayerNo() == 1)
                                label3.setText("Player1 Health : " + t.getHealth() + "  ");
                            else if(t.getPlayerNo() == 2)
                                label4.setText("Player2 Health : " + t.getHealth() + "  ");
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

                        g.drawImage(op.filter(img,null),  j*getWidth()/10, 20 + i*(getHeight()-20)/10,
                                getWidth()/10, (getHeight() - 20)/10, this);
                        if(pathname2 != "") {
                            op = rotate(img2, rot);
                            g.drawImage(op.filter(img2, null), j * getWidth() / 10 + width, 20 + i * (getHeight()-20) / 10 + height,
                                    getWidth() / 10 - 2 * width, (getHeight() - 20) / 10 - 2 * height, this);
                        }
                    }
                }
            }
        };

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.setPreferredSize(new Dimension(500, 500));
        gamePanel.add(label1);
        gamePanel.add(label2);
        gamePanel.add(label3);
        gamePanel.add(label4);
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

    public void displayScore(int score1, int score2){
        label1.setText("Player1 Score : " + score1 + "  ");
        label2.setText("Player2 Score : " + score2 + "  ");
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
    public void finishGame(){
        frame.getContentPane().remove(gamePanel);
        Main_Menu main_menu = new Main_Menu(null);
    }

}
