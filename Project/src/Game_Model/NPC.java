package Game_Model;

import entityModel.Game_Map;
import entityModel.Items.*;

import java.util.Random;

/**
 * Created by Apple on 29/04/2017.
 */
public class NPC {

    int playerNo;
    Game game;
    Game_Map map;
    private Random randomGenerator = new Random();
    int count = 3;
    String rand;
    int mineUse;
    int[] mined = new int[10];
    int mined2;

    public NPC(int playerNo, Game game, Game_Map map) {

        this.game = game;
        this.playerNo = playerNo;
        this.map = map;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public String handleNPC(){

        Tank t = null;
        String input;
        int posX = 0; int posY = 0;
        int[] array = map.retrieveMarked();
        for(int i = 0 ; i < array.length ; i++) {
            t = (Tank) map.retrieveTerrainInfo(array[i] / 10, array[i] % 10).
                    retrieveItemInfo("Tank");
            if (t != null && playerNo == t.getPlayerNo()) {
                posY = array[i] / 10;
                posX = array[i] % 10;
                break;
            }
        }
        if(mineUse == 50) {
            mineUse = 0;
            mined[mined2%10] = posY*10 + posX; mined2++;
            return "Land";
        }
        mineUse++;
        input = isMove(posX, posY, "Bullet", t);
        if(input != "")
            return input;
        input = isMove(posX, posY, "Tank", t);
        if(input != "")
            return input;
        input = isMove(posX, posY, "Power Up", t);
        if(input != "")
            return input;
        input = isMove(posX, posY, "Obstacle", t);
        if(input != "")
            return input;
        input = randomMove(posX, posY);
        if(input != "")
            return input;

        return "";
    }

    public String isMove(int posX, int posY, String itemType, Tank t){

        for(int i = posX - 1 ; i >= 0 ; i--){
            Other_Item item = map.retrieveTerrainInfo(posY, i).retrieveItemInfo(itemType);
            if(map.retrieveTerrainInfo(posY, i) instanceof Bush)
                continue;
            else if(item !=null) {
                if (itemType.equalsIgnoreCase("Tank") && t.getDirection().equalsIgnoreCase("Left"))
                    return "Fire";
                else if(itemType.equalsIgnoreCase("Bullet") && ((Bullet) item).getTank() != playerNo) {
                    if(map.isTerrainValid(posY + 1, posX, t, "Tank") && !isMined(posY + 1, posX))
                        return "Down";
                    else if(map.isTerrainValid(posY - 1, posX, t, "Tank") && !isMined(posY - 1, posX))
                        return "Up";
                }
                else if(itemType.equalsIgnoreCase("Obstacle") && t.getDirection().equalsIgnoreCase("Left")) {
                    if (t.isDestructor())
                        return "Left";
                    else if(!(((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                        return "Fire";
                }
                else if(!itemType.equalsIgnoreCase("Bullet") && !isMined(posY, posX - 1) &&
                        !(itemType.equalsIgnoreCase("Obstacle") &&
                                ((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                    return "Left";
            }
            if(map.retrieveTerrainInfo(posY, i).retrieveItemInfo("Obstacle")!=null)
                break;
        }
        for(int i = posX + 1 ; i <= 9 ; i++){
            Other_Item item = map.retrieveTerrainInfo(posY, i).retrieveItemInfo(itemType);
            if(map.retrieveTerrainInfo(posY, i) instanceof Bush)
                continue;
            else if(item!=null) {
                if (itemType.equalsIgnoreCase("Tank") && t.getDirection().equalsIgnoreCase("Right"))
                    return "Fire";
                else if(itemType.equalsIgnoreCase("Bullet") && ((Bullet) item).getTank() != playerNo) {
                    if(map.isTerrainValid(posY + 1, posX, t, "Tank") && !isMined(posY + 1, posX))
                        return "Down";
                    else if(map.isTerrainValid(posY - 1, posX, t, "Tank") && !isMined(posY - 1, posX))
                        return "Up";
                }
                else if(itemType.equalsIgnoreCase("Obstacle") && t.getDirection().equalsIgnoreCase("Right")){
                    if (t.isDestructor())
                        return "Right";
                    else if(!(((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                        return "Fire";
               }
                else if(!itemType.equalsIgnoreCase("Bullet") && !isMined(posY, posX + 1) &&
                        !(itemType.equalsIgnoreCase("Obstacle") &&
                                ((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                    return "Right";
            }
            if(map.retrieveTerrainInfo(posY, i).retrieveItemInfo("Obstacle")!=null)
                break;
        }
        for(int i = posY - 1 ; i >= 0 ; i--){
            Other_Item item = map.retrieveTerrainInfo(i, posX).retrieveItemInfo(itemType);
            if(map.retrieveTerrainInfo(i, posX) instanceof Bush)
                continue;
            else if(item != null) {
                if (itemType.equalsIgnoreCase("Tank") && t.getDirection().equalsIgnoreCase("Up"))
                    return "Fire";
                else if(itemType.equalsIgnoreCase("Bullet") && ((Bullet) item).getTank() != playerNo) {
                    if(map.isTerrainValid(posY, posX - 1, t, "Tank") && !isMined(posY, posX - 1))
                        return "Left";
                    else if(map.isTerrainValid(posY, posX + 1, t, "Tank") && !isMined(posY, posX + 1))
                        return "Right";
                }
                else if(itemType.equalsIgnoreCase("Obstacle") && t.getDirection().equalsIgnoreCase("Up")){
                    if (t.isDestructor())
                        return "Up";
                    else if(!(((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                        return "Fire";
                }
                else if(!itemType.equalsIgnoreCase("Bullet") && !isMined(posY - 1, posX) &&
                        !(itemType.equalsIgnoreCase("Obstacle") &&
                                ((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                    return "Up";
            }
            if(map.retrieveTerrainInfo(i, posX).retrieveItemInfo("Obstacle")!=null)
                break;
        }
        for(int i = posY + 1 ; i <= 9 ; i++){
            Other_Item item = map.retrieveTerrainInfo(i, posX).retrieveItemInfo(itemType);
            if(map.retrieveTerrainInfo(i, posX) instanceof Bush)
                continue;
            else if(item !=null) {
                if (itemType.equalsIgnoreCase("Tank") && t.getDirection().equalsIgnoreCase("Down"))
                    return "Fire";
                else if(itemType.equalsIgnoreCase("Bullet") && ((Bullet) item).getTank() != playerNo) {
                    if(map.isTerrainValid(posY, posX - 1, t, "Tank") && !isMined(posY, posX - 1))
                        return "Left";
                    else if(map.isTerrainValid(posY, posX + 1, t, "Tank") && !isMined(posY, posX + 1))
                        return "Right";
                }
                else if(itemType.equalsIgnoreCase("Obstacle") && t.getDirection().equalsIgnoreCase("Down")){
                    if (t.isDestructor())
                        return "Down";
                    else if(!(((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                        return "Fire";
                }
                else if(!itemType.equalsIgnoreCase("Bullet") && !isMined(posY + 1, posX) &&
                        !(itemType.equalsIgnoreCase("Obstacle") &&
                                ((Obstacle) item).getBrickType().equalsIgnoreCase("Steel") && t.getBulletLevel() != 2))
                    return "Down";
            }
            if(map.retrieveTerrainInfo(i, posX).retrieveItemInfo("Obstacle")!=null)
                break;
        }
        return "";
    }

    public String randomMove(int posX, int posY){
        String [] arr = new String[4]; arr[0] = "Left"; arr[1] = "Right"; arr[2] = "Up"; arr[3] = "Down";
        int random;
        if(count == 3){
            random = randomGenerator.nextInt(4);
            count=0;
            rand = arr[random];
        }
        else{
            count++;
        }
        if(rand.equalsIgnoreCase("Left") && !isMined(posY, posX - 1))
            return rand;
        else if(rand.equalsIgnoreCase("Right") && !isMined(posY, posX + 1))
            return rand;
        else if(rand.equalsIgnoreCase("Up") && !isMined(posY - 1, posX))
            return rand;
        else if(rand.equalsIgnoreCase("Down") && !isMined(posY + 1, posX))
            return rand;
        else
            return "";
    }

    public boolean isMined(int posY, int posX){
        for(int i = 0; i < 10 ; i++)
            if(mined[i] == posY*10 + posX)
                return true;
        return false;
    }
}
