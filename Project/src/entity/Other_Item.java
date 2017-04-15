package entity;

/**
 * Created by Apple on 15/04/2017.
 */
public class Other_Item extends Item{

    private boolean isPickable;

    public Other_Item(int x, int y, boolean isPickable) {

        super(x, y);
        this.isPickable = isPickable;
    }

    public boolean isPickable() {
        return isPickable;
    }

    public void setPickable(boolean pickable) {
        isPickable = pickable;
    }
}