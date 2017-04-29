package gameView;

import entityModel.Items.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by mertaytore on 24/4/17.
 */
public class Game_Screen {

    private JPanel gamePanel;
    Terrain[][] terrains;

    public Game_Screen(JFrame frame) {
        gamePanel = new JPanel(){
            public void paintComponent(Graphics g)
            {
                BufferedImage img = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
                String pathname = "" ;
                Obstacle obs;
                Power_Up pow;
                Tank t;

                for(int i = 0; i < terrains.length; i++){
                    for(int j = 0 ; j < terrains[i].length ; j++){

                        String rot = "";

                        if((t = (Tank) terrains[i][j].retrieveItemInfo("Tank")) != null ) {
                            pathname = "Project/imageFiles/green_level_1.png";
                            if(t.getDirection().equalsIgnoreCase("Left"))
                                rot = "Left";
                            else if(t.getDirection().equalsIgnoreCase("Right"))
                                rot = "Right";
                            else if(t.getDirection().equalsIgnoreCase("Down"))
                                rot = "Down";
                        }
                        else if(( obs = (Obstacle) terrains[i][j].retrieveItemInfo("Obstacle")) != null ) {
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
                                pathname = "Project/imageFiles/bric_rider.png";
                        }
                        else if(terrains[i][j] instanceof Ground){
                            pathname = "Project/imageFiles/player_base.png";
                        }
                        else if(terrains[i][j] instanceof Bush){
                            pathname = "Project/imageFiles/bush.png";
                        }
                        if(terrains[i][j].isInTerrain("Bullet") > -1 )
                            ;
                        if(terrains[i][j].isInTerrain("Mine") > -1 )
                            ;
                        try{
                            img = ImageIO.read( new File(pathname) );

                        }catch(IOException e){ System.out.print("no man");}

                         AffineTransformOp op = rotate(img, rot);

                        g.drawImage(op.filter(img,null), j*getWidth()/10, i*getHeight()/10, getWidth()/10, getHeight()/10, this);
                    }
                }
            }
        };

        gamePanel.setPreferredSize(new Dimension(500, 500));

        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setVisible(true);
    }
    public void displayGame(Terrain[][] terrains) {

        this.terrains = terrains;
        gamePanel.repaint();
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
        System.out.print("no por");
        return  op;
    }

}
