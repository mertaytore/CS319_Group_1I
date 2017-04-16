package entityModel.Items;


/**
 * Created by Apple on 16/04/2017.
 */
public class Harmful_Tool extends Other_Item {

    private int damage;
    private boolean isVisible;

    public Harmful_Tool(boolean isPickable, String itemType ) {
        super(isPickable, itemType);
        isVisible = true;
        damage = 1;
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
}
