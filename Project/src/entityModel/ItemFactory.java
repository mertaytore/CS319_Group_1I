package entityModel;

import entityModel.Items.*;

/**
 * Created by Apple on 16/04/2017.
 */
public class ItemFactory {

        public Other_Item getItem(String itemType, String addType, int damage){
            if(itemType == null){
                return null;
            }
            if(itemType.equalsIgnoreCase("TANK")){

                return new Tank(false, "Tank", addType);

            } else if(itemType.equalsIgnoreCase("MINE")){

                return new Mine(false, "Mine", damage);

            } else if(itemType.equalsIgnoreCase("BULLET")){

                return new Bullet(false, "Bullet", addType, damage);
            }
            else if(itemType.equalsIgnoreCase("POWER UP")) {

                return new Power_Up(true, "Power Up", addType);
            }
            else if(itemType.equalsIgnoreCase("OBSTACLE")) {

                return new Obstacle(false, "Obstacle", addType);
            }
            return null;
        }
}
