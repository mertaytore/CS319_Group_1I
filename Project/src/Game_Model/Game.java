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
    MapGenerator generator;
    Game_Screen screen;
    int count;
    String[] powerUp;
    Player player1;
    Player player2;
    NPC npc1;
    NPC npc2;
    Sound sounds;
    boolean terminated;
    boolean played1;
    boolean played2;

    public Game(Game_Screen screen, Player player1, Player player2, String mapPath){

        this.screen = screen;
        sounds = new Sound();
        if(mapPath.equalsIgnoreCase("Project/maps/mapRandom.txt")) {
            generator = new MapGenerator();
            sounds.playSound("Project/soundFiles/oz6.wav");
        }
        else
            sounds.playSound("Project/soundFiles/aaeve.wav");
        map = new Game_Map(screen, mapPath);
        gameLoop = new Timer();
        powerUp = new String[5];
        powerUp[0] = "Time"; powerUp[1] = "Tank"; powerUp[2] = "Bullet"; powerUp[3] = "Destructor"; powerUp[4] = "Mine";
        this.player1 = player1;
        this.player2 = player2;
        task = new TimerTask() {
            @Override
            public void run() {
                if(!screen.isPaused()) {

                    map.updateItems();
                    map.move(0);
                    count++;
                    createPowerUp();
                    updateNPC();
                    updatePlayerScore();
                    screen.displayScore(player1.getScore(), player2.getScore(), player1.getPlayerName(), player2.getPlayerName());

                    if (screen.getMin() == 0 && screen.getSec() == 0) {
                        sounds.playSound("Project/soundFiles/boring.wav");
                        gameLoop.cancel();
                        terminated = true;
                    }
                    if (screen.getMin() == 0 && screen.getSec() == 10)
                        sounds.playSound("Project/soundFiles/time.wav");

                    if (isGameOver()) {
                        sounds.playSound("Project/soundFiles/gameovr.wav");
                        gameLoop.cancel();
                        terminated = true;
                        screen.displayScore(player1.getScore(), player2.getScore(), player1.getPlayerName(), player2.getPlayerName());
                        screen.finishGame(player1, player2);
                    }
                }
            }
        };
        gameLoop.schedule(task,300, 300);
    }

    public void updatePlayerState(int playerNo, String input){
        if(!screen.isPaused()) {
            if (input.equalsIgnoreCase("Fire")) {
                map.fireBullet(playerNo);
            } else if (input.equalsIgnoreCase("Land")) {
                map.landMine(playerNo);
            } else {
                boolean changed = map.changeDirection(playerNo, input);
                if (!changed) {
                    map.updateItems();
                    map.move(playerNo);
                }
            }
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
            int preScore1 = player1.getScore(); int preScore2 = player2.getScore();
            Tank tank = retrieveTank(1);
            if(tank != null) {
                player1.setScore(tank.getLevel() * tank.getHealth() +
                        score + tank.getDestroyedBrick() * 10 + tank.getDestroyedSteel() * 20);
                if(tank.getHealth() > 14 && !played1) {
                    sounds.playSound("Project/soundFiles/forceisstrong.wav");
                    played1 = true;
                }
            }
            tank = retrieveTank(2);
            if(tank != null) {
                player2.setScore(tank.getLevel() * tank.getHealth() +
                        score + tank.getDestroyedBrick() * 10 + tank.getDestroyedSteel() * 20);
                if(tank.getHealth() > 14 && !played2) {
                    sounds.playSound("Project/soundFiles/invincible.wav");
                    played2 = true;
                }
            }
            if(player1.getScore() <= 50 && preScore1 > 50 ||  player2.getScore() <= 50 && preScore2 > 50)
                sounds.playSound("Project/soundFiles/concentr-3.wav");
            else if(player1.getScore() >= 200 && preScore1 < 200 )
                sounds.playSound("Project/soundFiles/to_inf.wav");
            else if(player1.getScore() >= 400 && preScore1 < 400)
                sounds.playSound("Project/soundFiles/you_win.wav");
            if( player2.getScore() >= 200 && preScore2 < 200)
                sounds.playSound("Project/soundFiles/joking.wav");
            else if(player2.getScore() >= 400 && preScore2 < 400)
                sounds.playSound("Project/soundFiles/gotten.wav");
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
            sounds.playSound("Project/soundFiles/hmrcando.wav");
            Tank tank = retrieveTank(playerNo);
            if (tank != null) {
                tank.setNPC(true);
                if (tank.getPlayerNo() == 1)
                    npc1 = new NPC(playerNo, this, map);
                if (tank.getPlayerNo() == 2)
                    npc2 = new NPC(playerNo, this, map);
            }
    }

    public void finishAI(int playerNo){
        sounds.playSound("Project/soundFiles/error.wav");
        Tank tank = retrieveTank(playerNo);
        if(tank != null) {
            tank.setNPC(false);
            if(playerNo == 1)
                npc1 = null;
            if(playerNo == 2)
                npc2 = null;
        }
    }

    public void updateNPC(){
        if(!screen.isPaused()) {
            String input;
            if (npc1 != null) {
                map.updateItems();
                input = npc1.handleNPC();
                if (input != "")
                    updatePlayerState(1, input);
            }
            if (npc2 != null) {
                map.updateItems();
                input = npc2.handleNPC();
                if (input != "")
                    updatePlayerState(2, input);
            }
        }
    }

    public boolean isTerminated(){
        return terminated;
    }
}
