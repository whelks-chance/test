package llantwit.questcraft.gameObjects.visualObjects;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 1/14/12
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Monster extends Block {
    public Monster(int i, int j) {
        super(i, j, "config/icons/monster.png");
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
