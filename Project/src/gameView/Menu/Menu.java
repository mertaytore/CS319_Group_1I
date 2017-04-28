package gameView.Menu;

import gameView.Game_Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Created by mertaytore on 24/4/17.
 */
public class Menu {

    private JFrame frame;
    private Game_Screen gameScreen;
    protected BufferedImage background;

    public Menu(Game_Screen screen, JFrame f) {


        this.gameScreen = screen;
        this.frame = f;

        background = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);

        try{
            background = ImageIO.read( new File("Project/imageFiles/background_image.png") );

        }catch(IOException e){ System.out.print("no man");}
    }

}
