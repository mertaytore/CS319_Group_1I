package Game_Model;

import entityModel.Game_Map;
import entityModel.Items.Tank;
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
    Player player1;
    Player player2;
    public Game(Game_Screen screen){

        map = new Game_Map(screen, "Project/maps/map.txt" ,1);
        gameLoop = new Timer();
        powerUp = new String[5];
        powerUp[0] = "Time"; powerUp[1] = "Tank"; powerUp[2] = "Bullet"; powerUp[3] = "Destructor"; powerUp[4] = "Mine";
        player1 = new Player();
        player2 = new Player();
        task = new TimerTask() {
            @Override
            public void run() {
                map.updateItems();
                map.move(0);
                count ++;
                createPowerUp();
                if(isGameOver()){
                    screen.finishGame();
                    gameLoop.cancel();
                }
            }
        };
        gameLoop.schedule(task,3000, 500);
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
    public boolean isGameOver(){
        int[] marked = map.retrieveMarked();
        for(int i = 0; i < marked.length; i++){
           Tank tank = (Tank) map.retrieveTerrainInfo(marked[i]/10, marked[i]%10).retrieveItemInfo("Tank");
           if(tank != null && tank.getHealth() <= 0)
               return true;
        }
        return false;
    }
}
