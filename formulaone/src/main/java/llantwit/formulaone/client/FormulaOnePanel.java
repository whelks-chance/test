package llantwit.formulaone.client;

import llantwit.blockgame.BlockGamePanel;
import llantwit.blockgame.gameObjects.Block;
import llantwit.formulaone.visuals.RedRoad;
import llantwit.formulaone.visuals.TarmacBlock;
import llantwit.utils.ui.ImageUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/3/12
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormulaOnePanel extends BlockGamePanel{

    private static final String TESTMAP = ".............###########.#.........#.#...#####.#.#...#...#.#.#...###.###.#.....#.@.@.#.###.#.@.@.###.###.@@@............";

    public FormulaOnePanel(String ip, int port){
        super();
        this.setupXMLClient(ip, port, new FormulaOneXMLProcesser(this));

        setBackgroundTile(new ImageUtils().getImageFromPath("config/icons/grass.gif"));

        setWorldSize("14,10,1");
        initWorld(TESTMAP, 12, 12);
    }

    @Override
    protected Block getBlockForChar(char c) {
        if(c == TarmacBlock.CHAR){
            return new TarmacBlock(0, 0);
        }
        if(c == RedRoad.CHAR){
            return new RedRoad(0, 0);
        }
        return null;    }

    @Override
    protected void drawGameObjects(Graphics g) {
    }

    public void repeat() {
        repaint();
    }


    public void keyTyped(KeyEvent keyEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void keyPressed(KeyEvent keyEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void keyReleased(KeyEvent keyEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mousePressed(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
