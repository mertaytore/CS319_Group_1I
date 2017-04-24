package gameView.Menu;

import gameView.Game_Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Created by mertaytore on 24/4/17.
 */
public class Menu {

    private JFrame frame;
    private Game_Screen gameScreen;
    private BufferedImage background;

    public Menu(Game_Screen screen, JFrame f, BufferedImage bg) {
        this.gameScreen = screen;
        this.frame = f;
        this.background = bg;
    }

}
