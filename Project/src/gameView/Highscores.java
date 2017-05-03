package gameView;

import java.io.*;
import java.util.HashMap;

/**
 * Created by mertaytore on 30/4/17.
 */
public class Highscores {

    // variables
    private File scoreFile;
    private String[] allScoresNames;
    private int[]    allScores;
    private HashMap playerScoreMap;
    FileReader fileReader;
    BufferedReader br = null;


    public Highscores(){
        playerScoreMap = new HashMap();
        scoreFile = new File("Project/src/gameView/scores.txt");
        String[] tempSplit;

        allScoresNames = new String[10];
        allScores = new int[10];
        try
        {
            fileReader = new FileReader(scoreFile);
            br = new BufferedReader(fileReader);
            for (int i = 0; i < 10 ;i++)
            {
                String row;
                if ( (row = br.readLine()) != null)
                {
                    tempSplit = row.split(" ");
                    playerScoreMap.put(tempSplit[0], Integer.parseInt(tempSplit[1]));
                    allScoresNames[i] = tempSplit[0];
                    allScores[i] = Integer.parseInt(tempSplit[1]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fileReader != null)
                    fileReader.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
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

    public void compareScores(String player, int pScore) {

        for (int i = 0; i < 10; ++i) {
            int currentScore = allScores[i];
            if (currentScore < pScore) {
                for (int k = 9; k > 0 && k + 1 != i; --k) {
                    allScores[k] = allScores[k - 1];
                    allScoresNames[k] = allScoresNames[k - 1];
                }
                allScoresNames[i] = player;
                allScores[i] = pScore;
                this.updateScores();
                break;
            }
        }
    }

}
