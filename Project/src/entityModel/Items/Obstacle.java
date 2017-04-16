package entityModel.Items;

/**
 * Created by Apple on 16/04/2017.
 */
public class Obstacle extends Other_Item {

    private int health;
    private boolean isBreakable;
    private String brickType;

    public Obstacle(boolean isPickable, String itemType, boolean isBreakable, String brickType) {
        super(isPickable, itemType);
        this.brickType =brickType;
        this.isBreakable = isBreakable;
        if (brickType.equalsIgnoreCase("Steel"))
            health = 5;
        else
            health = 3;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isBreakable() {
        return isBreakable;
    }

    public void setBreakable(boolean breakable) {
        isBreakable = breakable;
    }

    public String getBrickType() {
        return brickType;
    }

    public void setBrickType(String brickType) {
        this.brickType = brickType;
    }
}