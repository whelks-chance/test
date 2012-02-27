package llantwit.questcraft.gameObjects.visualObjects;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 1/14/12
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerBlock extends Block{

    public PlayerBlock(int x, int y) {
        super(x, y, "config/icons/man.png");
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
