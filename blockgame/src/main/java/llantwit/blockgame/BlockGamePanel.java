package llantwit.blockgame;

import llantwit.blockgame.gameObjects.Block;
import llantwit.simpleServer.xmlclient.XMLClient;
import llantwit.simpleServer.xmlclient.XMLProcessor;
import llantwit.simpleServer.xmlclient.gui.Repeatable;
import llantwit.simpleServer.xmlclient.gui.RepeatableThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/4/12
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BlockGamePanel extends JPanel implements Repeatable, MouseListener, MouseMotionListener, KeyListener {
    private Point mousePoint;

    protected XMLClient xmlClient;
    private String playerID = "";

    private String worldData = "";

    protected ArrayList<Block> blocks = new ArrayList<Block>();
    protected int worldSizeX = 12;
    protected int worldSizeY = 12;
    protected int worldSizeZ = 1;
    private Image backgroundTile;

    public BlockGamePanel(String serverIp, int port, XMLProcessor xmlProcessor) {
        setupXMLClient(serverIp, port, xmlProcessor);
        init();
    }

    public BlockGamePanel() {
        init();
    }

    public void setupXMLClient(String serverIp, int port, XMLProcessor xmlProcessor){
        if(serverIp.equals("")){
            serverIp = "127.0.0.1";
        }

        try {
            xmlClient = new XMLClient(serverIp, 4444, xmlProcessor);
        } catch (IOException e) {
            System.out.println("CRITICAL FAILURE : no connection to server");
        }

    }

    private void init(){
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        new RepeatableThread(this, 500).start();
        this.setFocusable(true);
        this.requestFocus();

        initWorld(worldData, worldSizeX, worldSizeY);
    }

    protected void initWorld(String worldData, int width, int height) {
        char[] a = worldData.toCharArray();
        blocks.clear();

        for( int j = 0; j < height; j ++){
            for(int i = 0 ; i < width ; i++){
                int pos = (width * j) + i;

                if(pos < a.length){
                    char c = a[pos];

                    Block block = getBlockForChar(c);

                    if(block != null){
                        block.setX(i);
                        block.setY(j);
                        blocks.add(block);
                    }

                }
            }
        }
    }

    protected abstract Block getBlockForChar(char c);

    public void setWorldData(String worldData){
        this.worldData = worldData;
        initWorld(worldData, worldSizeX, worldSizeY);

        if(this.getX() != worldSizeX*64){
            this.setSize(worldSizeX * 64, worldSizeY * 64);
            this.getParent().validate();
        }
    }

    public void setBackgroundTile(Image backgroundTile){
        this.backgroundTile = backgroundTile;
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        BufferedImage offScreen = new BufferedImage(worldSizeX * 64, worldSizeY * 64, BufferedImage.TYPE_INT_RGB);
        Graphics g = offScreen.getGraphics();

        if(backgroundTile != null){
            for(int j = 0; j < worldSizeY; j++){
                for(int i = 0; i < worldSizeX; i++){
                    g.drawImage(backgroundTile, i*64, j*64, this);
                }
            }
        }

        for(Block block : blocks){
            g.drawImage(block.getImage(), block.getX() * 64, block.getY() * 64, this);
        }

        drawGameObjects(g);


        graphics.drawImage(offScreen, 0, 0, this);


    }

    protected abstract void drawGameObjects(Graphics g);

//    public void mouseDragged(MouseEvent mouseEvent) {
//        mousePoint = mouseEvent.getPoint();
//    }
//
//    public void mouseMoved(MouseEvent mouseEvent) {
//        mousePoint = mouseEvent.getPoint();
//    }

//    public void mouseClicked(MouseEvent mouseEvent) {
//    }
//
//    public void mousePressed(MouseEvent mouseEvent) {
//
//    }
//
//    public void mouseReleased(MouseEvent mouseEvent) {
//
//    }
//
//    public void mouseEntered(MouseEvent mouseEvent) {}
//
//    public void mouseExited(MouseEvent mouseEvent) {}
//
//    public void keyTyped(KeyEvent keyEvent) {
//
//    }
//
//    public void keyPressed(KeyEvent keyEvent) {
//    }
//
//
//    public void keyReleased(KeyEvent keyEvent) {
//    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public void setWorldSize(String sizeString) {
        String[] sizes = sizeString.split(",");
        worldSizeX = Integer.parseInt(sizes[0]);
        worldSizeY = Integer.parseInt(sizes[1]);
        worldSizeZ = Integer.parseInt(sizes[2]);
    }

    public void repeat() {
        doServerChat();
        repaint();
    }

    private void doServerChat() {
        if (xmlClient != null){
            try {
//                String xmlString = XMLUtils.xmlify(this);
                String xmlString = xmlClient.xmlify(this);
                String returned = xmlClient.sendAndProcessResponse(xmlString);

                System.out.println(returned);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
