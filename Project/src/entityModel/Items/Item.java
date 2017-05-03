package entityModel.Items;

/**
 * Created by Apple on 15/04/2017.
 */
public class Item {

    private int xCoordinate;
    private int yCoordinate;

    public Item(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public Item(){
        this.xCoordinate = 0;
        this.yCoordinate = 0;
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

}
