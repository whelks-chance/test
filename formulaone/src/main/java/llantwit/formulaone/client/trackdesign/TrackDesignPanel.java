package llantwit.formulaone.client.trackdesign;

import llantwit.blockgame.BlockGamePanel;
import llantwit.blockgame.gameObjects.Block;
import llantwit.formulaone.visuals.RedRoad;
import llantwit.formulaone.visuals.TarmacBlock;
import llantwit.utils.ui.ImageUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/4/12
 * Time: 1:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class TrackDesignPanel extends BlockGamePanel {

    private static final String TESTMAP = "";
    private Point mousepoint;
    private int tileSize = 64;
    private String worldInfo = "";

    public TrackDesignPanel(){
        super();

        setBackgroundTile(new ImageUtils().getImageFromPath("config/icons/grass.gif"));

        initWorld(TESTMAP, 14, 8);
    }

    @Override
    protected Block getBlockForChar(char c) {
        if(c == TarmacBlock.CHAR){
            return new TarmacBlock(0, 0);
        }
        if(c == RedRoad.CHAR){
            return new RedRoad(0, 0);
        }
        return null;
    }

    @Override
    protected void drawGameObjects(Graphics g) {
        for(int i = 0; i < worldSizeX * tileSize; i+= tileSize){
            g.drawLine(i, 0, i, worldSizeY * tileSize);
        }

        for(int j = 0; j < worldSizeY* tileSize; j += tileSize){
            g.drawLine(0, j, worldSizeX * tileSize, j);
        }
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
        mousepoint = mouseEvent.getPoint();
        flipBlockForPoint(mousepoint, mouseEvent.getButton());
        outputWorldInfo();
    }

    private void outputWorldInfo() {
        worldInfo = "";
        for(int j = 0; j < worldSizeY; j++){
            for(int i = 0 ; i < worldSizeX; i++){

                Block found = null;
                for(Block block: blocks){
                    if(block.getX() == i && block.getY() == j){
                        found = block;
                    }
                }

                if(found == null){
                    worldInfo += ".";
                }else{
                    worldInfo += found.getChar();
                }
            }
        }
        System.out.println("WorldInfo : " + worldInfo);
    }

    private void flipBlockForPoint(Point point, int button) {
        int px1 = ((int) (Math.floor(point.x / tileSize)));
        int py1 = ((int) (Math.floor(point.y / tileSize)));

        Block found = getBlockForGridRef(px1, py1);
        if(found == null){

            char c;
            if(button == MouseEvent.BUTTON3){
                c = '@';
            }
            else{
                c = '#';
            }

            Block newblock = getBlockForChar(c);
            if(newblock != null){
                newblock.setX(px1);
                newblock.setY(py1);
                blocks.add(newblock);
            }
        }else {
            blocks.remove(found);
        }
    }

    private Block getBlockForGridRef(int px1, int py1){
        Block found = null;
        for(Block block : blocks){
            if(block.getX() == px1 && block.getY() == py1){
                found = block;
            }
        }
        return found;
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

    private Block getBlockForInt(int position){
        System.out.println(position);
        int yn = position;
        int y = 0;
        while( yn > worldSizeY ){
            y++;
            yn =- worldSizeY;
        }
        int x = position - (y * worldSizeX);
        System.out.println(x + ", " + y);
        return getBlockForGridRef(x, y);
    }

    public void trace() {
        ArrayList<Block> trackBlocks = new ArrayList<Block>();
        System.out.println(worldInfo);
        int start = worldInfo.indexOf(TarmacBlock.CHAR);
        Block startBlock = getBlockForInt(start);
        if (startBlock != null && startBlock.getChar() == TarmacBlock.CHAR){
            paintBlock(startBlock, null);
        }
        Block previousBlock = null;

        for(int i = 0; i < getTarmacCount(worldInfo) + 1 ; i++){
            Block nextBlock = findConnectingBlock(startBlock, previousBlock);
            if(nextBlock != null){
                trackBlocks.add(startBlock);
//                paintBlock(nextBlock);
                previousBlock = startBlock;
                startBlock = nextBlock;
            }
        }

        TarmacBlock.Orientation prev = null;
        for(Block block : trackBlocks){
            if(block instanceof TarmacBlock){
                TarmacBlock tarmacBlock = (TarmacBlock) block;
                if(prev != null){
                    System.out.println(prev.name() + " : " + tarmacBlock.getOrientation());
                    if(tarmacBlock.getOrientation() != prev){
                        System.out.println("corner at " + block.getX() + ", " + block.getY());
                    }
                }
                prev = tarmacBlock.getOrientation();
            }
        }
    }

    private int getTarmacCount(String worldInfo) {
        int count = 0;
        for (int i = 0; i < worldInfo.toCharArray().length; i++) {
            char c = worldInfo.toCharArray()[i];
            if(c == TarmacBlock.CHAR){
                count++;
            }
        }
        return count;
    }

    private void paintBlock(Block block, TarmacBlock.Orientation pattern){
        block.setImageToDefault();
        if(block instanceof TarmacBlock){
            ((TarmacBlock) block).setOrientation(pattern);
            BufferedImage bufferedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(block.getImage(), 0, 0, this);

            if(pattern == TarmacBlock.Orientation.right){
                g.setColor(Color.red);
                g.drawLine(0, 32, 64, 32);
                g.drawLine(64, 32, 54, 22);
                g.drawLine(64, 32, 54, 42);
            } else if(pattern == TarmacBlock.Orientation.left){
                g.setColor(Color.red);
                g.drawLine(0, 32, 64, 32);
                g.drawLine(0, 32, 10, 22);
                g.drawLine(0, 32, 10, 42);

            } else if(pattern == TarmacBlock.Orientation.down){
                g.setColor(Color.red);
                g.drawLine(32, 0, 32, 64);
                g.drawLine(32, 64, 42, 54);
                g.drawLine(32, 64, 22, 54);

            } else if (pattern == TarmacBlock.Orientation.up){
                g.setColor(Color.red);
                g.drawLine(32, 0, 32, 64);
                g.drawLine(32, 0, 42, 10);
                g.drawLine(32, 0, 22, 10);
            }

            block.setImage(bufferedImage);
        }
    }

    private Block findConnectingBlock(Block startBlock, Block previousBlock) {
        ArrayList<Block> foundBlocks = new ArrayList<Block>();
        for(Block block : blocks){
            if (block != startBlock){
                if(block.getX() == startBlock.getX() + 1 && block.getY() == startBlock.getY()){
                    foundBlocks.add(block);
                }
                if(block.getX() == startBlock.getX() - 1 && block.getY() == startBlock.getY()){
                    foundBlocks.add(block);
                }
                if(block.getX() == startBlock.getX() && block.getY() == startBlock.getY() + 1){
                    foundBlocks.add(block);
                }
                if(block.getX() == startBlock.getX() && block.getY() == startBlock.getY() - 1){
                    foundBlocks.add(block);
                }
            }
        }
        for(Block block : foundBlocks){
            if (block != previousBlock){
                if(block.getX() == startBlock.getX() + 1 && block.getY() == startBlock.getY()){
                    paintBlock(startBlock, TarmacBlock.Orientation.right);
                }
                if(block.getX() == startBlock.getX() - 1 && block.getY() == startBlock.getY()){
                    paintBlock(startBlock, TarmacBlock.Orientation.left);
                }
                if(block.getX() == startBlock.getX() && block.getY() == startBlock.getY() + 1){
                    paintBlock(startBlock, TarmacBlock.Orientation.down);
                }
                if(block.getX() == startBlock.getX() && block.getY() == startBlock.getY() - 1){
                    paintBlock(startBlock, TarmacBlock.Orientation.up);
                }
                return block;
            }
        }
        return null;
    }
}
