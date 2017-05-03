package entityModel.Items;


/**
 * Created by Apple on 16/04/2017.
 */
public class Harmful_Tool extends Other_Item {

    private int damage;
    private boolean isVisible;
    private int tank;

    public Harmful_Tool(boolean isPickable, String itemType, int damage ) {
        super(isPickable, itemType);
        isVisible = true;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getTank() {
        return tank;
    }

    public void setTank(int tank) {
        this.tank = tank;
    }
}
