package llantwit.utils.drawing;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public abstract class VisualObject {

    private int width;
    private int height;
    private int x;
    private int y;

    public VisualObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public abstract void draw(Graphics g);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveX(int movement){
        x = x + movement;
    }

    public void moveY(int movement){
        y = y + movement;
    }
}
