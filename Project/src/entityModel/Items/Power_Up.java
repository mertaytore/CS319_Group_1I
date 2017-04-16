package entityModel.Items;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Apple on 16/04/2017.
 */
public class Power_Up extends Other_Item {

    private Timer visibility;
    private TimerTask task;
    private String powerUpType;

    public void setPowerUpType(String powerUpType) {
        this.powerUpType = powerUpType;
    }

    public Power_Up(boolean isPickable, String itemType, String powerUp) {
        super(isPickable, itemType);
        this.powerUpType = powerUp;
        this.visibility = new Timer();
        this.task = new TimerTask() {
            @Override
            public void run() {
                setPickable(false);
                visibility.cancel();
            }
        };
        visibility.schedule(task,5000);
    }

    public String getPowerUpType() {
        return powerUpType;
    }
}
