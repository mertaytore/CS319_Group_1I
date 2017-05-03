package entityModel.Items;

/**
 * Created by Apple on 16/04/2017.
 */
public class Obstacle extends Other_Item {

    private int health;
    private String brickType;

    public Obstacle(boolean isPickable, String itemType, String brickType) {
        super(isPickable, itemType);
        this.brickType =brickType;
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

    public String getBrickType() {
        return brickType;
    }

    public void setBrickType(String brickType) {
        this.brickType = brickType;
    }
}
