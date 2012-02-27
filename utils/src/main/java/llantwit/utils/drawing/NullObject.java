package llantwit.utils.drawing;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
public class NullObject extends VisualObject{

    public NullObject() {
        super(0,0,0,0);
    }

    @Override
    public void draw(Graphics g) {
    }

}
