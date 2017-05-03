package Game_Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Apple on 03/05/2017.
 */
public class MapGenerator {

    private File mapFile;
    String[] mapItem;
    private Random randomGenerator = new Random();
    int random;
    int row1;
    int row2;
    int column1;
    int column2;

    public MapGenerator(){

        mapFile = new File("Project/maps/mapRandom.txt");
        mapItem = new String[5]; mapItem[0]="S"; mapItem[1]="O"; mapItem[2]="G"; mapItem[3]="B";
        row1 = randomGenerator.nextInt(10);
        row2 = randomGenerator.nextInt(10);
        column1 = randomGenerator.nextInt(10);
        column2 = randomGenerator.nextInt(10);
        generateMap();
    }

    public void generateMap() {
        String fileWrite;
        try {
            FileWriter w = new FileWriter(mapFile, false);
            for (int i = 0; i < 10 ;i++){
                for( int j = 0; j < 10 ; j++) {
                    random = randomGenerator.nextInt(4);
                    if((i == row1 && j == column1) || (i == row2 && j == column2))
                        fileWrite = "T";
                    else
                        fileWrite = mapItem[random];
                    if(j == 9)
                        fileWrite += "\n";
                    else
                        fileWrite += " ";
                    w.write(fileWrite.toCharArray());
                }
            }
            w.close();
        } catch (IOException e)
        {
            System.out.println("there is a problem with generating map");
        }
    }
}
