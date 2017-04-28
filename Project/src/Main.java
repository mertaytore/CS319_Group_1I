import gameView.Game_Screen;
import gameView.Menu.Main_Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Apple on 15/04/2017.
 */

public class Main {
    public static void main(String[] args){
        Game_Screen game = new Game_Screen();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Main_Menu menu = new Main_Menu(game, frame);

        System.out.println("hello");
    }
}
