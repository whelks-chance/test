package llantwit.questcraft.gameObjects.visualObjects;

import llantwit.questcraft.maps.Maps;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 1/14/12
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DoorBlock extends Block {

    private boolean isOpen = true;

    public DoorBlock(int x, int y, boolean open) {
        super(x, y, "config/icons/door.png");
        setOpen(open);
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public boolean isSolid() {
        return !isOpen();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
        BufferedImage bufferedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(this.getImage(), 0, 0, null);

        if(open){
            g.drawImage(Maps.getImage("config/icons/unlock1.png"), 30, 24, null);
        } else {
            g.drawImage(Maps.getImage("config/icons/lock1.png"), 30, 24, null);
        }
        this.setImage(bufferedImage);
        g.dispose();
    }
}
