package entityModel.Items;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Apple on 16/04/2017.
 */
public class Mine extends Harmful_Tool {

    Timer mineTime;
    TimerTask task;
    public Mine(boolean isPickable, String itemType, int damage) {
        super(isPickable, itemType, damage);
        mineTime = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                setVisible(false);
                mineTime.cancel();
            }
        };
        mineTime.schedule(task, 3000);
    }
}
