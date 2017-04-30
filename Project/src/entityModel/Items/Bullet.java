package entityModel.Items;

/**
 * Created by Apple on 16/04/2017.
 */
public class Bullet extends Harmful_Tool implements Movable {

    private int bulletLevel;
    private String direction;

    public Bullet(boolean isPickable, String itemType, String direction, int damage ) {
        super(isPickable, itemType, damage);
        this.direction = direction;
        this.bulletLevel = 1;
    }

    public int getBulletLevel() {
        return bulletLevel;
    }

    public void setBulletLevel(int bulletLevel) {
        this.bulletLevel = bulletLevel;
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
