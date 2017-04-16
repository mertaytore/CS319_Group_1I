package entityModel.Items;

/**
 * Created by Apple on 15/04/2017.
 */
public class Other_Item extends Item{

    private boolean isPickable;
    private String itemType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Other_Item(boolean isPickable, String itemType) {

        this.isPickable = isPickable;
        this.itemType = itemType;

    }

    public boolean isPickable() {
        return isPickable;
    }

    public void setPickable(boolean pickable) {
        isPickable = pickable;
    }
}