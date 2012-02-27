package llantwit.questcraft.gameObjects.visualObjects;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 1/13/12
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class WallBlock extends Block {

    public WallBlock(int x, int y) {
        super(x, y, "config/icons/minecraft-gold-icon.png");
    }

    @Override
    public void draw(Graphics g) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
