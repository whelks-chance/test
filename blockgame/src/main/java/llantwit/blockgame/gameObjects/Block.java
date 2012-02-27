package llantwit.blockgame.gameObjects;

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
    private String url;

    public Block(int x, int y, String url){
        super(x, y, 64, 64);
        this.url = url;
        setImageToDefault();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public abstract boolean isSolid();

    public abstract char getChar();

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

    public void setImageToDefault() {
        try{
            URL loc = this.getClass().getClassLoader().getResource(url);
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
