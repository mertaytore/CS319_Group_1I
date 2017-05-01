package Game_Model;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.net.URL;

/**
 * Created by mertaytore on 30/4/17.
 */
public class Highscores {

    // variables
    private static Highscores score_manager = new Highscores();
    private URL path;
    private File scoreFile;
    private String[] allScoresNames;
    private int[]    allScores;
    private int highScore;
    private int scoreList;
    private int name;
    private HashMap playerScoreMap;
    Scanner fileReader;


    public Highscores(){
        playerScoreMap = new HashMap();
        path = getClass().getResource("scores.txt");
        scoreFile = new File(path.getPath());
        String readerTemp;
        String[] tempSplit;

        allScoresNames = new String[10];
        allScores = new int[10];
        tempSplit = new String[2];
        FileWriter write = null;
        try
        {
            fileReader = new Scanner(scoreFile);
            for (int i = 0; i < 10 ;i++)
            {
                if (fileReader.hasNext())
                {
                    readerTemp = fileReader.nextLine();
                    tempSplit = readerTemp.split(" ");
                    playerScoreMap.put(tempSplit[0], Integer.parseInt(tempSplit[1]));
                    allScoresNames[i] = tempSplit[0];
                    allScores[i] = Integer.parseInt(tempSplit[1]);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("there is a problem with the scanner reading");
        }
    }

    public String[] getHighScoresNames() {
        return allScoresNames;
    }

    public int[] getHighScores() {
        return allScores;
    }

    public void updateScores() {
        String fileWrite;
        try {
            FileWriter w = new FileWriter(scoreFile, false);
            for (int i = 0; i < 10 ;i++){
                fileWrite = allScoresNames[i] + " " + String.valueOf(allScores[i]) + "\n";
                w.write(fileWrite.toCharArray());
            }
            w.close();
        } catch (IOException e)
        {
            System.out.println("there is a problem with updating scores");
        }
    }

    public void compareScores(String player, int pScore){
        String read;
        String[] temp;
        try
        {
            Scanner fileReader = new Scanner(scoreFile);
            for (int i = 0; i < 10; ++i)
            {
                read = fileReader.nextLine();
                temp = read.split(" ");
                int currentScore = Integer.parseInt(temp[1]);
                if (currentScore < pScore)
                {
                    for (int k = 9;k > 0 && k + 1 != i; --k)
                    {
                        allScores[k] = allScores[k-1];
                        allScoresNames[k] = allScoresNames[k-1];
                    }
                    allScoresNames[i] = player;
                    allScores[i] = pScore;
                    this.updateScores();
                    break;
                }
            }

        } catch (FileNotFoundException e)
        {
            System.out.println("problem has occured in comparing results");
        }

    }

}
