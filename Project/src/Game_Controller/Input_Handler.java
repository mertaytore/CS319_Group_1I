package Game_Controller;

import Game_Model.Game;

import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * Created by Apple on 29/04/2017.
 */
public class Input_Handler {

    private int playerNo;
    private String input;
    Game game;

    public Input_Handler(){

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
        if(key == 190){
            playerNo = 1;
            input = "Land";
        }
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
        sendInput();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void sendInput(){

        game.updatePlayerState(playerNo, input);
    }
}
