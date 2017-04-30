package Game_Model;

import entityModel.Game_Map;
import gameView.Game_Screen;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Apple on 29/04/2017.
 */
public class Game {

    Game_Map map;
    private Timer gameLoop;
    private TimerTask task;
    private Random randomGenerator = new Random();
    int count;
    String[] powerUp;
    public Game(Game_Screen screen){

        map = new Game_Map(screen, "Project/maps/map.txt" ,1);
        gameLoop = new Timer();
        powerUp = new String[5];
        powerUp[0] = "Time"; powerUp[1] = "Tank"; powerUp[2] = "Bullet"; powerUp[3] = "Destructor"; powerUp[4] = "Mine";
        task = new TimerTask() {
            @Override
            public void run() {
                map.updateItems();
                map.move(0);
                count ++;
                createPowerUp();
            }
        };
        gameLoop.schedule(task,1000, 500);
    }

    public void updatePlayerState(int playerNo, String input){
        if(input.equalsIgnoreCase("Fire")){
            map.fireBullet(playerNo);
        }
        else if (input.equalsIgnoreCase("Land")){
            map.landMine(playerNo);
        }
        else{
            boolean changed = map.changeDirection(playerNo, input);
            if(!changed)
                map.move(playerNo);
        }
    }
    public void createPowerUp(){
        if(count % 14 == 0){
            int randomX = randomGenerator.nextInt(10);
            int randomY = randomGenerator.nextInt(10);
            int random = randomGenerator.nextInt(5);
            map.addItem(randomX, randomY, "Power Up", powerUp[random]);
        }
    }
}
