package llantwit.formulaone.visuals;

import llantwit.blockgame.gameObjects.Block;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/4/12
 * Time: 1:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class TarmacBlock extends Block {

    public static final char CHAR = '#';
    private Orientation orientation;

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public enum Orientation{
        right,
        left,
        down,
        up
    }

    public TarmacBlock(int x, int y) {
        super(x, y, "config/icons/asphalt.gif");
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
