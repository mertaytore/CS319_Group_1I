package Game_Model;

import entityModel.Game_Map;
import gameView.Game_Screen;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Apple on 29/04/2017.
 */
public class Game {

    Game_Map map;
    private Timer gameLoop;
    private TimerTask task;
    public Game(Game_Screen screen){

        map = new Game_Map(screen, "Project/maps/map.txt" ,1);
        gameLoop = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                map.updateItems();
                map.move(0);
            }
        };
        gameLoop.schedule(task,1000, 1000);
    }

    public void updatePlayerState(int playerNo, String input){
        if(input.equalsIgnoreCase("Fire")){
            map.fireBullet(playerNo);
        }
        else if (input.equalsIgnoreCase("Land")){
            map.landMine(playerNo);
        }
        else{
            map.changeDirection(playerNo, input);
            map.move(playerNo);
        }
    }
}
