package llantwit.cables;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Box {
    private Cable inCable = null;
    private Cable outCable = null;

    private Rectangle bounds;
    private String name;
    private String type;
    private String description;
    private String bundleName;

    public Box(String name, int x, int y, int width, int height) {
        this.name = name;
        bounds = new Rectangle(x, y, width, height);
    }

    public Point getExit(){
        return new Point((int)(bounds.getX() + bounds.getWidth()), (int)(bounds.getY() + (bounds.getHeight()/2)));
    }

    public Rectangle2D getBounds() {
        return bounds.getBounds2D();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getEntrance() {
        return new Point((int)bounds.getX(), (int)(bounds.getY() + (bounds.getHeight()/2)));
    }

    public void setOutCable(Cable cable) {
        if(cable != null && outCable != null){
            outCable.disconnect();
        }
        outCable = cable;
    }

    public void setInCable(Cable cable) {
        if(cable != null && inCable != null){
            inCable.disconnect();
        }
        inCable = cable;
    }

    public Cable[] getCables() {
        return new Cable[]{inCable, outCable};
    }

    public Cable getOutCable() {
        return outCable;
    }

    public Cable getInCable(){
        return inCable;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setX(int x) {
        bounds.setLocation(x, (int)bounds.getY());
    }
}
