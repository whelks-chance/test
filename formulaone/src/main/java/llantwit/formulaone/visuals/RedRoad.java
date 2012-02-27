package llantwit.formulaone.visuals;

import llantwit.blockgame.gameObjects.Block;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/4/12
 * Time: 2:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class RedRoad extends Block {
    public static final char CHAR = '@';

    public RedRoad(int i, int i1) {
        super(i, i1, "config/icons/red.gif");
    }

    @Override
    public boolean isSolid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public char getChar() {
        return CHAR;
    }

    @Override
    public void draw(Graphics g) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
