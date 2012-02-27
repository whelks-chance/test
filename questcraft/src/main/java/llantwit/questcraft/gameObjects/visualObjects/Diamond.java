package llantwit.questcraft.gameObjects.visualObjects;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 1/16/12
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class Diamond extends Block {
    public Diamond(int i, int j) {
        super(i, j, "config/icons/diamond.png");
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void draw(Graphics g) {
    }
}
