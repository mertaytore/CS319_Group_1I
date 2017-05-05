package gameView.Menu;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mertaytore on 24/4/17.
 */
public class Settings_Menu extends Menu {


    private JButton change;
    private JButton returnB;
    private JPanel settingsPanel;
    private File settingsFile;
    private HashMap playerSettingsMap;
    private FileReader fileReader;
    private BufferedReader br = null;
    private BasicOptionPaneUI.ButtonActionListener settingsListener;
    private JComboBox[] combos;
    private JLabel[] labelsForCombo;
    private JLabel error;
    private JButton playGameButton;
    private String[] names = {"Green Tank Left Move : ", "Green Tank Up Move : ", "Green Tank Right Move : ", "Green Tank Down Move : ",
            "Green Tank Fire : ","Green Tank Land : ","Yellow Tank Left Move : ","Yellow Tank Up Move : ",
            "Yellow Tank Right Move : ","Yellow Tank Down Move : ","Yellow Tank Fire : ","Yellow Tank Land : "};
    private JToggleButton sound;
    private static boolean selected;
    private static float vol = -2;
    private final int vol_min = 0;
    private final int vol_max = 16;

    public Settings_Menu(JFrame frame) {
        // inherited frame and background image from Menu
        super(frame);
        playerSettingsMap = new HashMap();
        settingsFile = new File("Project/src/gameView/keyBoardConf.txt");
        displaySettings();
    }

    public  void displaySettings(){

        try
        {
            fileReader = new FileReader(settingsFile);
            br = new BufferedReader(fileReader);
            String row;
            while ( (row = br.readLine()) != null)
            {
                String[] tempSplit = row.split(" ");
                playerSettingsMap.put(tempSplit[0], Integer.parseInt(tempSplit[1]));
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


        settingsPanel=new JPanel(){
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        error = new JLabel("");
        combos = new JComboBox[12];
        labelsForCombo = new JLabel[12];
        error.setForeground(Color.cyan);
        playGameButton = new JButton("Play");


        JSlider volume = new JSlider(JSlider.HORIZONTAL,
                vol_min, vol_max, (int) vol + 10);
        JLabel volName = new JLabel("  Volume Level Adjustment :  ");
        volName.setForeground(Color.white);

        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                vol = volume.getValue() - 10;
            }
        });

        volume.setMajorTickSpacing(4);
        volume.setMinorTickSpacing(1);
        volume.setPaintTicks(true);
        volume.setPaintLabels(true);
        volume.setForeground(Color.white);

        String[] array = null;
        try {
            fileReader = new FileReader("Project/src/gameView/settings.txt");
            br = new BufferedReader(fileReader);
            String row;

            br = new BufferedReader(new FileReader("Project/src/gameView/settings.txt"));
            if ((row = br.readLine()) != null) {
                array = row.split(" ");
            }
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

        for( int i = 0; i < 12; i++){
            Iterator iter = playerSettingsMap.entrySet().iterator();
            combos[i] = new JComboBox();
            labelsForCombo[i] = new JLabel(names[i]);
            if(i < 6)
                labelsForCombo[i].setForeground(Color.green);
            else
                labelsForCombo[i].setForeground(Color.yellow);
            while (iter.hasNext()){
                Map.Entry pair = (Map.Entry) iter.next();
                combos[i].addItem(pair.getKey());
                if(array[i].equalsIgnoreCase( (String.valueOf(pair.getValue()))))
                    combos[i].setSelectedItem(pair.getKey());
            }
            settingsPanel.add(labelsForCombo[i]);
            settingsPanel.add(combos[i]);
        }

        change = new JButton("Change Settings");
        returnB = new JButton("Back to Main Menu");
        sound = new JToggleButton("Enable Sound");
        sound.setSelected(selected);
        sound.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if(ev.getStateChange()==ItemEvent.SELECTED){
                    selected = true;
                } else if(ev.getStateChange()==ItemEvent.DESELECTED){
                    selected = false;
                }
            }
        });

        settingsPanel.add(change);
        settingsPanel.add(returnB);
        settingsPanel.add(sound);
        settingsPanel.add(volName);
        settingsPanel.add(volume);
        settingsPanel.add(playGameButton);
        settingsPanel.add(error);


        settingsPanel.setPreferredSize(new Dimension(500,500));

        frame.getContentPane().add(settingsPanel);
        frame.pack();
        frame.setVisible(true);

        change.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String result = "";
                error.setText("");

                for(int i = 0; i < 12; i++){
                    String temp = String.valueOf(playerSettingsMap.get(combos[i].getSelectedItem()));
                    result += temp + " ";
                }
                result = result.substring(0, result.length() - 1);
                String[] part = result.split(" ");
                for( int i = 0; i < part.length; i++)
                    for( int j = i + 1; j < part.length ; j++)
                        if(part[i].equalsIgnoreCase(part[j]))
                            error.setText(" All keys must be unique. ");
                if(error.getText().equalsIgnoreCase("")){
                    try {
                        FileWriter w = new FileWriter("Project/src/gameView/settings.txt", false);
                        w.write(result.toCharArray());
                        w.close();
                    } catch (IOException ex)
                    {
                        System.out.println("there is a problem generating settings");
                    }
                }
            }
        });

        returnB.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.getContentPane().remove(settingsPanel);
               displayMainMenu();
            }
        });

        playGameButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){

                frame.getContentPane().remove(settingsPanel);
                displayGameOptions();
            }
        });
    }

    public static boolean isSelected(){
        return selected;
    }

    public static void setSelected(boolean select){
         selected = select;
    }

    public static float getVol() {
        return vol;
    }

    public static void setVol(float vol) {
        Settings_Menu.vol = vol;
    }
}
