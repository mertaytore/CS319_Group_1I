package Game_Controller;

import Game_Model.Game;

import java.util.Timer;
import java.util.TimerTask;

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
    private String[] settings;
    private Screen_Handler handler;

    public Input_Handler(){

        handler = new Screen_Handler();
        settings = handler.readSettings();
        threshold = 10;
        time = new Timer();
        isCounting2 = true;
        isCounting1 = true;
        task = new TimerTask() {
            @Override
            public void run() {
             if (game.isTerminated())
                 time.cancel();
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
        if(key == Integer.parseInt(settings[0])){
            playerNo = 1;
            input = "left";
        }
        if(key == Integer.parseInt(settings[1])){
            playerNo = 1;
            input = "Up";
        }
        if(key == Integer.parseInt(settings[2])){
            playerNo = 1;
            input = "Right";
        }
        if(key == Integer.parseInt(settings[3])){
            playerNo = 1;
            input = "Down";
        }
        if(key == Integer.parseInt(settings[4]) ){
            playerNo = 1;
            input = "Fire";
        }
        if(key == Integer.parseInt(settings[5])){
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
        if(key == Integer.parseInt(settings[6])){
            playerNo = 2;
            input = "left";
        }
        if(key == Integer.parseInt(settings[7])){
            playerNo = 2;
            input = "Up";
        }
        if(key == Integer.parseInt(settings[8])){
            playerNo = 2;
            input = "Right";
        }
        if(key == Integer.parseInt(settings[9])){
            playerNo = 2;
            input = "Down";
        }
        if(key == Integer.parseInt(settings[10])){
            playerNo = 2;
            input = "Fire";
        }
        if(key == Integer.parseInt(settings[11])){
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
