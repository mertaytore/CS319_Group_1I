package Game_Model;

import entityModel.Game_Map;
import gameView.Game_Screen;

/**
 * Created by Apple on 29/04/2017.
 */
public class Game {

    Game_Map map;
    public Game(Game_Screen screen){

        map = new Game_Map(screen, "Project/maps/map.txt" ,1);
    }
}
