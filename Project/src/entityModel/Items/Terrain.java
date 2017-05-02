package entityModel.Items;

import entityModel.ItemFactory;

/**
 * Created by Apple on 16/04/2017.
 */
public class Terrain extends Item {

    public static int tankNo;
    private int xCoordinate;
    private int yCoordinate;
    private boolean isVisible;
    Other_Item[] items;
    int index;
    private ItemFactory factory = new ItemFactory();

    public Terrain(int x , int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        items = new Other_Item[10];
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void addItem( String itemType, String addType, int damage){
        items[index] = factory.getItem(itemType, addType, damage);
        index++;
    }

    public void  setItem(Other_Item item){
        items[index] = item;
        index++;
    }

    public void removeItem( String itemType ){

        int removed = isInTerrain(itemType);

        if(removed != -1) {
            for (int i = removed; i < index - 1; i++){
                items[i] = items[i+1];
            }
            index--;
        }
    }

    public boolean updateItem(){

        int found;
        int found2 = -1;
        boolean time = false;
        if((found = isInTerrain("Power Up")) != -1){
            if(!items[found].isPickable())
                removeItem("Power Up");
        }
        if((found = isInTerrain("Tank")) != -1){
            if((found2 = isInTerrain("Power Up")) != -1){
                time = updatePowerUp(found, found2);
            }
        }
        boolean visible = false;
        do {
            if ((found = isInTerrain("Tank")) != -1) {
                if ((found2 = isInTerrain("Bullet")) != -1) {
                    updateTank(found, found2, "Bullet");
                }
                else if ((found2 = isInTerrain("Mine")) != -1 && !(visible = ((Harmful_Tool) items[found2]).isVisible())) {
                    updateTank(found, found2, "Mine");
                }
            }
            else if ((found = isInTerrain("Obstacle")) != -1) {
                if ((found2 = isInTerrain("Bullet")) != -1) {
                    updateObstacle(found, found2, "Bullet");
                }
                else if ((found2 = isInTerrain("Mine")) != -1) {
                    updateObstacle(found, found2, "Mine");
                }
            }
        }while(found != -1 && found2 != -1 && !visible);

        if((found = isInTerrain("Tank")) != -1) {
            if ( (found2 = isInTerrain("Obstacle")) != -1) {
                if(((Obstacle) items[found2]).getBrickType().equalsIgnoreCase("Steel"))
                    ((Tank) items[found]).setDestroyedSteel(((Tank) items[found]).getDestroyedSteel() + 1);
                else  if(((Obstacle) items[found2]).getBrickType().equalsIgnoreCase("Brick"))
                    ((Tank) items[found]).setDestroyedBrick(((Tank) items[found]).getDestroyedBrick() + 1);
                removeItem("Obstacle");
            }
        }
        return time;
    }
    public int isInTerrain(String itemType){
        for(int i = 0 ; i < index; i++){
            if(items[i].getItemType().equalsIgnoreCase(itemType)) {
                return i;
            }
        }
        return -1;
    }
    public Other_Item retrieveItemInfo(String itemType)
    {
        int found;
        if((found = isInTerrain(itemType)) != -1)
            return items[found];
        return null;
     }
    public boolean updatePowerUp(int tankIndex, int powerIndex){
         if(((Power_Up) items[powerIndex]).getPowerUpType().equalsIgnoreCase("Tank"))
             ((Tank) items[tankIndex]).upgradeTank();
         else if(((Power_Up) items[powerIndex]).getPowerUpType().equalsIgnoreCase("Bullet"))
             ((Tank) items[tankIndex]).setBulletLevel(((Tank) items[tankIndex]).getBulletLevel() + 1);
         else if(((Power_Up) items[powerIndex]).getPowerUpType().equalsIgnoreCase("Destructor"))
             ((Tank) items[tankIndex]).setDestructor(true);
         else if(((Power_Up) items[powerIndex]).getPowerUpType().equalsIgnoreCase("Mine"))
             ((Tank) items[tankIndex]).setMineNumber(((Tank) items[tankIndex]).getMineNumber() + 1);
         else if(((Power_Up) items[powerIndex]).getPowerUpType().equalsIgnoreCase("Time")){
             removeItem("Power Up");
             return true;
         }
         removeItem("Power Up");
         return false;
    }
    public void updateTank(int tankIndex, int bulletIndex, String type){
        ((Tank) items[tankIndex]).setHealth
                (((Tank) items[tankIndex]).getHealth() - ((Harmful_Tool) items[bulletIndex]).getDamage());
        removeItem(type);
    }
    public void updateObstacle(int obsIndex, int bulletIndex, String type){
        ((Obstacle) items[obsIndex]).setHealth
                (((Obstacle) items[obsIndex]).getHealth() - ((Harmful_Tool) items[bulletIndex]).getDamage());
        if(((Obstacle) items[obsIndex]).getHealth()<=0) {
            if(((Obstacle) items[obsIndex]).getBrickType().equalsIgnoreCase("Brick"))
                tankNo = ((Harmful_Tool) items[bulletIndex]).getTank();
            else if(((Obstacle) items[obsIndex]).getBrickType().equalsIgnoreCase("Steel"))
                tankNo = ((Harmful_Tool) items[bulletIndex]).getTank() + 2;
            removeItem("Obstacle");
        }
        removeItem(type);
    }


}
