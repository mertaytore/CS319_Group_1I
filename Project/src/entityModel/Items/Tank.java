package entityModel.Items;
import java.util.*;
/**
 * Created by Apple on 16/04/2017.
 */
public class Tank extends Other_Item implements Movable {

    private int playerNo;
    private int health;
    private int mineNumber;
    private int bulletLevel;

    private boolean isDestructor;
    private Timer destruct;
    private TimerTask task;

    private int level;
    private boolean isNPC;
    String direction;

    public Tank( boolean isPickable, String itemType, String direction) {
        super(isPickable, itemType);
        this.health = 5;
        this.mineNumber = 5;
        this.bulletLevel = 1;
        this.isDestructor = false;
        this.level = 1;
        this.isNPC = false;
        this.direction = direction;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMineNumber(int mineNumber) {
        this.mineNumber = mineNumber;
    }

    public void setBulletLevel(int bulletLevel) {
        if (this.bulletLevel < 2)
            this.bulletLevel = bulletLevel;
    }

    public void setDestructor(boolean destructor) {
        isDestructor = destructor;
        if (isDestructor){
            destruct = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    setDestructor(false);
                    destruct.cancel();
                }
            };
            destruct.schedule(task,10000);
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setNPC(boolean NPC) {
        isNPC = NPC;
    }

    public int getHealth() {
        return health;
    }

    public int getMineNumber() {
        return mineNumber;
    }

    public int getBulletLevel() {
        return bulletLevel;
    }

    public boolean isDestructor() {
        return isDestructor;
    }

    public int getLevel() {
        return level;
    }

    public boolean isNPC() {
        return isNPC;
    }

    public void upgradeTank(){
        if(this.level < 4){
            level++;
            health = health*2;
        }
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    @Override
    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String getDirection() {
        return direction;
    }
}
