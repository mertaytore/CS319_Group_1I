package entityModel.Items;

/**
 * Created by Apple on 16/04/2017.
 */
public class Bullet extends Harmful_Tool implements Movable {

    private String direction;

    public Bullet(boolean isPickable, String itemType, String direction, int damage ) {
        super(isPickable, itemType, damage);
        this.direction = direction;
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
