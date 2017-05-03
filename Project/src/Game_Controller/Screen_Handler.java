package Game_Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Apple on 29/04/2017.
 */
public class Screen_Handler {

    String file;

    BufferedReader br = null;
    FileReader fr = null;

    public Screen_Handler(){
        file = "Project/src/gameView/settings.txt";
        readSettings();
    }

    public String[] readSettings(){
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String row;

            br = new BufferedReader(new FileReader(file));
            if ((row = br.readLine()) != null) {
                String[] array = row.split(" ");
                return array;
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
        return null;
    }
}
