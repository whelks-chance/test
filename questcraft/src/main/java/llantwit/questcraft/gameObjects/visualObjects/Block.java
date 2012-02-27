package llantwit.questcraft.gameObjects.visualObjects;

import llantwit.utils.drawing.VisualObject;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 1/13/12
 * Time: 11:43 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Block extends VisualObject{

    private Image image;

    private int fullStrength = 8;
    private int remainingStrength = 8;

    public Block(int x, int y, String url){
        super(x, y, -1, -1);

        try{
            URL loc = this.getClass().getClassLoader().getResource(url);
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public abstract boolean isSolid();

    public int getFullStrength() {
        return fullStrength;
    }

    public void setFullStrength(int fullStrength) {
        this.fullStrength = fullStrength;
    }

    public int getRemainingStrength() {
        return remainingStrength;
    }

    public void setRemainingStrength(int remainingStrength) {
        this.remainingStrength = remainingStrength;
    }

    public void hit(int hitPower){
        setRemainingStrength(getRemainingStrength() - hitPower);
    }
}
