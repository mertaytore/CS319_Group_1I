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
    Game_Screen screen;
    int count;
    String[] powerUp;
    Player player1;
    Player player2;
    public Game(Game_Screen screen){

        this.screen = screen;
        map = new Game_Map(screen, "Project/maps/mapMedium.txt" ,1);
        gameLoop = new Timer();
        powerUp = new String[5];
        powerUp[0] = "Time"; powerUp[1] = "Tank"; powerUp[2] = "Bullet"; powerUp[3] = "Destructor"; powerUp[4] = "Mine";
        player1 = new Player(1);
        player2 = new Player(2);
        task = new TimerTask() {
            @Override
            public void run() {
                map.updateItems();
                map.move(0);
                count ++;
                createPowerUp();
                updatePlayerScore();
                screen.displayScore(player1.getScore(), player2.getScore());
                if(isGameOver()){
                    gameLoop.cancel();
                    screen.displayScore(player1.getScore(), player2.getScore());
                    screen.finishGame();
                }
            }
        };
        gameLoop.schedule(task,300, 300);
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
            if(map.retrieveTerrainInfo(randomX, randomY).retrieveItemInfo("Obstacle") == null)
                map.addItem(randomX, randomY, "Power Up", powerUp[random], 0);
        }
    }
    public boolean isGameOver(){
        Tank tank1 = retrieveTank(1);
        Tank tank2 = retrieveTank(2);
        if(tank1 != null && tank1.getHealth() <= 0)
            player2.setScore(player2.getScore() + 40);
        else if(tank2 != null && tank2.getHealth() <= 0)
            player1.setScore(player1.getScore() + 40);
        else
            return false;
        return true;
    }

    public void updatePlayerScore(){
            int score = screen.getSec()*2 + screen.getMin()*120;
            Tank tank = retrieveTank(1);
            if(tank != null)
                player1.setScore(tank.getLevel() * tank.getHealth() +
                        score + tank.getDestroyedBrick()*10 + tank.getDestroyedSteel()*20);
            tank = retrieveTank(2);
            if(tank != null)
                player2.setScore(tank.getLevel() * tank.getHealth() +
                        score + tank.getDestroyedBrick()*10 + tank.getDestroyedSteel()*20);
    }

    public Tank retrieveTank(int playerNo){
        int[] marked = map.retrieveMarked();
        for(int i = 0; i < marked.length; i++) {
            Tank tank = (Tank) map.retrieveTerrainInfo(marked[i] / 10, marked[i] % 10).retrieveItemInfo("Tank");
            if (tank != null && tank.getPlayerNo() == playerNo) {
                return tank;
            }
        }
        return null;
    }

    public void startAI(int playerNo){
        Tank tank = retrieveTank(playerNo);
        if(tank != null)
            tank.setNPC(true);
    }

    public void finishAI(int playerNo){
        Tank tank = retrieveTank(playerNo);
        if(tank != null)
            tank.setNPC(false);
    }
}
