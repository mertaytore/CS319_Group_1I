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

    protected JFrame frame;
    protected BufferedImage background;

    private Main_Menu main_menu;
    private Settings_Menu settings_menu;
    private Score_Menu score_menu;
    private Credits_Screen credits_screen;

    public Menu(JFrame frame) {

        if(frame == null) {
            this.frame = new JFrame("Battle City");
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        else
            this.frame = frame;

        this.frame.setMinimumSize( new Dimension(500,500));

        background = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);

        try{
            background = ImageIO.read( new File("Project/imageFiles/background_image.png") );

        }catch(IOException e){ System.out.print("no man");}
    }

    public void displaySettingsMenu() {

        settings_menu = new Settings_Menu(frame);
    }

    public void displayMainMenu() {

        main_menu = new Main_Menu(frame);
    }

    public void displayHighscoresMenu() {
        score_menu = new Score_Menu(frame);
    }

    public void displayCredits() {
        credits_screen = new Credits_Screen(frame);
    }
}
