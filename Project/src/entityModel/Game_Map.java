package entityModel;

import entityModel.Items.*;
import gameView.Game_Screen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Apple on 16/04/2017.
 */
public class Game_Map {

    private String mapFile;
    private int difficulty;
    private Terrain[][] gameTerrain;
    private int rowIndex;
    private int column;
    private int index;
    private int [] marked;
    private Game_Screen screen;

    BufferedReader br = null;
    FileReader fr = null;

    public Game_Map(Game_Screen screen, String mapFile, int difficulty) {
        this.mapFile = mapFile;
        this.difficulty = difficulty;
        gameTerrain = new Terrain[10][10];
        marked = new int[10];
        this.screen = screen;
        mapFileReader();
    }
    public void mapFileReader(){
        try {
            fr = new FileReader(mapFile);
            br = new BufferedReader(fr);
            String row;

            br = new BufferedReader(new FileReader(mapFile));
            int playerNo=1;
            while ((row = br.readLine()) != null) {
                String[] array = row.split(" ");
                for( column=0 ; column < array.length ; column++){
                    if(array[column].equalsIgnoreCase("S")){
                        gameTerrain[rowIndex][column] = new Ground(rowIndex, column);
                        gameTerrain[rowIndex][column].addItem("Obstacle", "Steel");
                    }
                    else if(array[column].equalsIgnoreCase("O")){
                        gameTerrain[rowIndex][column] = new Ground(rowIndex, column);
                        gameTerrain[rowIndex][column].addItem("Obstacle", "Brick");
                    }
                    else if(array[column].equalsIgnoreCase("G")){
                        gameTerrain[rowIndex][column] = new Ground(rowIndex, column);
                    }
                    else if(array[column].equalsIgnoreCase("B")){
                        gameTerrain[rowIndex][column] = new Bush(rowIndex, column);
                    }
                    else if(array[column].equalsIgnoreCase("T")){
                        gameTerrain[rowIndex][column] = new Ground(rowIndex, column);
                        addItem(rowIndex, column, "Tank", "Left");
                        ((Tank) gameTerrain[rowIndex][column].retrieveItemInfo("Tank")).setPlayerNo(playerNo);
                        playerNo++;
                    }
                }
                column = 0;
                rowIndex++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }
    public void addItem(int row, int column, String itemType, String addType){
        gameTerrain[row][column].addItem(itemType, addType);
        if(itemType.equalsIgnoreCase("Bullet")||itemType.equalsIgnoreCase("Tank")||
                itemType.equalsIgnoreCase("Power Up")){
            marked[index] = row*10 + column;
            index++;
        }
        screen.displayGame(gameTerrain);
    }
    public void fireBullet(int playerNo){
        int indexPre = index;
        for(int i = 0 ; i < indexPre ; i++) {
            Movable item = ((Movable) gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Tank"));
            if(playerNo == ((Tank) item).getPlayerNo()) {
                if (item.getDirection().equalsIgnoreCase("left")) {
                    addItem(marked[i] / 10, marked[i] % 10 - 1, "Bullet", item.getDirection());
                }
                if (item.getDirection().equalsIgnoreCase("right")) {
                    addItem(marked[i] / 10, marked[i] % 10 + 1, "Bullet", item.getDirection());
                }
                if (item.getDirection().equalsIgnoreCase("down")) {
                    addItem(marked[i] / 10 + 1, marked[i] % 10, "Bullet", item.getDirection());
                }
                if (item.getDirection().equalsIgnoreCase("up")) {
                    addItem(marked[i] / 10 - 1, marked[i] % 10, "Bullet", item.getDirection());
                }
            }
        }
    }
    public void landMine(int playerNo){
        for(int i = 0 ; i < index ; i++) {
            Movable item = ((Movable) gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Tank"));
            if(playerNo == ((Tank) item).getPlayerNo())
                addItem(marked[i] / 10, marked[i] % 10, "Mine", "");
        }
    }
    public void updateItems(){
        for(int i = 0 ; i < index ; i++) {
            gameTerrain[marked[i] / 10][marked[i] % 10].updateItem();
            if(gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Bullet") == null &&
                    gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Tank") == null &&
                    gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Power Up") == null) {
                unmark(i);
                index--;
            }
        }
        screen.displayGame(gameTerrain);
    }
    public void unmark(int i){
        for(int j = i ; j < index -1 ; j++)
            marked[i] = marked[i+1];
    }
    public void move(int playerNo){
        String itemType;
        if(playerNo == 0){
            itemType = "Bullet";
        }
        else{
            itemType = "Tank";
        }
        for(int i = 0 ; i < index ; i++){
            Movable item = ((Movable) gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo(itemType));
            if(item != null && (itemType == "Bullet" || playerNo == ((Tank) item).getPlayerNo())) {
                gameTerrain[marked[i]/10][marked[i]%10].removeItem(itemType);
                if ( item.getDirection().equalsIgnoreCase("Left")) {
                    gameTerrain[marked[i] / 10][marked[i] % 10 - 1].setItem((Other_Item) item);
                    marked[i] = marked[i] - 1;
                } else if (item.getDirection().equalsIgnoreCase("Right")) {
                    gameTerrain[marked[i] / 10][marked[i] % 10 + 1].setItem((Other_Item) item);
                    marked[i] = marked[i] + 1;
                } else if (item.getDirection().equalsIgnoreCase("Up")) {
                    gameTerrain[marked[i] / 10 - 1][marked[i] % 10].setItem((Other_Item) item);
                    marked[i] = marked[i] - 10;
                } else if (item.getDirection().equalsIgnoreCase("Down")) {
                    gameTerrain[marked[i] / 10 + 1][marked[i] % 10].setItem((Other_Item) item);
                    marked[i] = marked[i] + 10;
                }
            }
        }
        screen.displayGame(gameTerrain);
    }
    public void changeDirection(int playerNo, String direction){
        for(int i = 0 ; i < index ; i++) {
            Other_Item item = gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Tank");
            if(item != null && ((Tank) item).getPlayerNo() == playerNo){
                ((Tank) item).setDirection(direction);
            }
        }
        screen.displayGame(gameTerrain);
    }
    public Terrain retrieveTerrainInfo(int row, int column){
        return gameTerrain[row][column];
    }
}
