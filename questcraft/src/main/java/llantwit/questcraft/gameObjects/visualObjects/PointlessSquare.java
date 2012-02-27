package llantwit.questcraft.gameObjects.visualObjects;

import llantwit.utils.drawing.VisualObject;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class PointlessSquare extends VisualObject {

    public PointlessSquare(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.drawRect(this.getX() * 64, this.getY() * 64, this.getWidth(), this.getHeight());
    }

}
