package entityModel;

import entityModel.Items.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Apple on 16/04/2017.
 */
public class Game_Map {

    String mapFile;
    int difficulty;
    Terrain[][] gameTerrain;
    int rowIndex;
    int column;
    int index;
    int [] marked;

    BufferedReader br = null;
    FileReader fr = null;

    public Game_Map(String mapFile, int difficulty) {
        this.mapFile = mapFile;
        this.difficulty = difficulty;
        gameTerrain = new Terrain[10][10];
        marked = new int[10];
        mapFileReader();
    }
    public void mapFileReader(){
        try {
            fr = new FileReader(mapFile);
            br = new BufferedReader(fr);
            String row;

            br = new BufferedReader(new FileReader(mapFile));

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
                        gameTerrain[rowIndex][column].addItem("Tank", "Left");
                        marked[index] = rowIndex*10 + column;
                        index++;
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
        if(itemType.equalsIgnoreCase("Bullet"))
            addType = ((Tank) gameTerrain[row][column].retrieveItemInfo("Tank")).getDirection();
        gameTerrain[row][column].addItem(itemType, addType);
        if(itemType.equalsIgnoreCase("Bullet")||itemType.equalsIgnoreCase("Tank")||
                itemType.equalsIgnoreCase("Power Up")){
            marked[index] = row*10 + column;
            index++;
        }
    }
    public void updateItems(){
        for(int i = 0 ; i < index ; i++) {
            gameTerrain[marked[i] / 10][marked[i] % 10].updateItem();
            if(gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Bullet") ==null &&
                    gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Tank") == null &&
                    gameTerrain[marked[i] / 10][marked[i] % 10].retrieveItemInfo("Power Up") == null) {
                unmark(i);
                index--;
            }
        }
    }
    public void unmark(int i){
        for(int j = i ; j < index -1 ; j++)
            marked[i] = marked[i+1];
    }
    public void moveBullet(){

        for(int i = 0 ; i < index ; i++){
            Other_Item item = gameTerrain[marked[i]/10][marked[i]%10].retrieveItemInfo("Bullet");
            gameTerrain[marked[i]/10][marked[i]%10].removeItem("Bullet");
            if(item != null) {
                if (((Bullet) item).getDirection().equalsIgnoreCase("Left")) {
                    gameTerrain[marked[i] / 10][marked[i] % 10 - 1].setItem(item);
                    marked[i] = marked[i] - 1;
                } else if (((Bullet) item).getDirection().equalsIgnoreCase("Right")) {
                    gameTerrain[marked[i] / 10][marked[i] % 10 + 1].setItem(item);
                    marked[i] = marked[i] + 1;
                } else if (((Bullet) item).getDirection().equalsIgnoreCase("Up")) {
                    gameTerrain[marked[i] / 10 - 1][marked[i] % 10].setItem(item);
                    marked[i] = marked[i] - 10;
                } else if (((Bullet) item).getDirection().equalsIgnoreCase("Down")) {
                    gameTerrain[marked[i] / 10 + 1][marked[i] % 10].setItem(item);
                    marked[i] = marked[i] + 10;
                }
            }
        }
    }
}
