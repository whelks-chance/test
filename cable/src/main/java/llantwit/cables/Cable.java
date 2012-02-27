package llantwit.cables;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cable {
    private Box fromBox;
    private Box toBox;

    public Cable(Box fromBox, Box toBox) {
        this.fromBox = fromBox;
        this.toBox = toBox;
    }

    public Box getFromBox(){
        return fromBox;
    }

    public Box getToBox(){
        return toBox;
    }

    public Point getFrom() {
        return fromBox.getExit();
    }

    public Point getTo() {
        return toBox.getEntrance();
    }

    public void disconnect(){
        fromBox.setOutCable(null);
        toBox.setInCable(null);
    }
}
