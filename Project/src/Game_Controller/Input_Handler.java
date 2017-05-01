package Game_Controller;

import Game_Model.Game;

import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * Created by Apple on 29/04/2017.
 */
public class Input_Handler {

    private int playerNo;
    private String input;
    private Game game;
    private int countPlayer1;
    private int countPlayer2;
    private int threshold;
    private Timer time;
    private TimerTask task;
    private boolean isCounting1;
    private boolean isCounting2;

    public Input_Handler(){
        threshold = 10;
        time = new Timer();
        isCounting2 = true;
        isCounting1 = true;
        task = new TimerTask() {
            @Override
            public void run() {
             if(isCounting1)
                countPlayer1++;
             if(isCounting2)
                countPlayer2++;
             if(countPlayer1 > threshold)
                 initializeAI(1);
             if(countPlayer2 > threshold)
                 initializeAI(2);
            }
        };
        time.schedule(task,300, 500);
    }

    public void checkInput(int key){

        input="";
        if(key == 37){
            playerNo = 1;
            input = "left";
        }
        if(key == 38){
            playerNo = 1;
            input = "Up";
        }
        if(key == 39){
            playerNo = 1;
            input = "Right";
        }
        if(key == 40){
            playerNo = 1;
            input = "Down";
        }
        if(key == VK_ENTER ){
            playerNo = 1;
            input = "Fire";
        }
        if(key == 76){
            playerNo = 1;
            input = "Land";
        }
        if(input != "" ){
            if(!isCounting1)
                terminateAI(1);
            countPlayer1 = 0;
            sendInput();
        }
        input = "";
        if(key == 65){
            playerNo = 2;
            input = "left";
        }
        if(key == 87){
            playerNo = 2;
            input = "Up";
        }
        if(key == 68){
            playerNo = 2;
            input = "Right";
        }
        if(key == 83){
            playerNo = 2;
            input = "Down";
        }
        if(key == 32 ){
            playerNo = 2;
            input = "Fire";
        }
        if(key == 67){
            playerNo = 2;
            input = "Land";
        }
        if(input != "" ){
            if(!isCounting2)
                terminateAI(2);
            countPlayer2 = 0;
            sendInput();
        }
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void sendInput(){

        game.updatePlayerState(playerNo, input);
    }

    public int getCountPlayer1() {
        return countPlayer1;
    }

    public void setCountPlayer1(int countPlayer1) {
        this.countPlayer1 = countPlayer1;
    }

    public int getCountPlayer2() {
        return countPlayer2;
    }

    public void setCountPlayer2(int countPlayer2) {
        this.countPlayer2 = countPlayer2;
    }

    public void initializeAI(int playerNo){
        if(playerNo == 1) {
            isCounting1 = false;
            countPlayer1 = 0;
            game.startAI(1);
        }
        else if(playerNo == 2) {
            isCounting2 = false;
            countPlayer2 = 0;
            game.startAI(2);
        }
    }

    public void terminateAI(int playerNo){
        if(playerNo == 1) {
            isCounting1 = true;
            game.finishAI(1);
        }
        else if(playerNo == 2) {
            isCounting2 = true;
            game.finishAI(2);
        }
    }
}
