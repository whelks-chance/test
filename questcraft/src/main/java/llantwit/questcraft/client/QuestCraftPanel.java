package llantwit.questcraft.client;

import llantwit.questcraft.gameObjects.GameObjects;
import llantwit.questcraft.gameObjects.User;
import llantwit.questcraft.gameObjects.visualObjects.*;
import llantwit.questcraft.maps.Maps;
import llantwit.simpleServer.xmlclient.XMLClient;
import llantwit.simpleServer.xmlclient.gui.Repeatable;
import llantwit.simpleServer.xmlclient.gui.RepeatableThread;
import llantwit.utils.drawing.VisualObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestCraftPanel extends JPanel implements Repeatable, MouseListener, MouseMotionListener, KeyListener{

    private boolean selection;
    private Point mousePoint;

    private XMLClient xmlClient;
    private String playerID = "";
    public boolean startup = true;

    private String worldData = "";

    ArrayList<Block> blocks = new ArrayList<Block>();
    private int worldSizeX = 10;
    private int worldSizeY = 12;
    private int worldSizeZ = 1;
    private int highlightedY = -1;
    private int highlightedX = -1;
    private Block highlightedBlock = null;
    private ArrayList<Integer> brokenBlocks = new ArrayList<Integer>();
    private BufferedImage bufferedImage;

    public QuestCraftPanel(String serverIp) {

        if(serverIp.equals("")){
            serverIp = "127.0.0.1";
        }

        try {
            xmlClient = new XMLClient(serverIp, 4444, new QuestXMLProcessor(this));
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
//        GameObjects.setPlayerObject(new PointlessSquare(50, 100, 20, 30));
        GameObjects.setPlayerObject(new User(playerID));

        Image animate = Maps.getImage("config/icons/barron.png");
        System.out.println(animate.getWidth(this) + " " + animate.getHeight(this));
        bufferedImage = new BufferedImage(animate.getWidth(this), animate.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g = bufferedImage.getGraphics();
        g.drawImage(animate, 0, 0, this);

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

    private void initWorld(String worldData, int width, int height) {
        char[] a = worldData.toCharArray();
//        System.out.println(width + " " + height);
//        System.out.println(a.length);
        blocks.clear();

        for( int j = 0; j < height; j ++){
            for(int i = 0 ; i < width ; i++){
                int pos = (width * j) + i;

                if(pos < a.length){
                    char c = a[pos];

                    if(c == '#'){
                        blocks.add(new WallBlock(i, j));
                    }
                    if(c == Maps.DOOR_LOCKED){
                        blocks.add(new DoorBlock(i, j, false));
                    }
                    if(c == Maps.DOOR_UNLOCKED){
                        blocks.add(new DoorBlock(i, j, true));
                    }
                    if(c == '@'){
                        blocks.add(new Monster(i, j));
                    }
                    if(c == Maps.DIAMOND){
                        blocks.add(new Diamond(i,j));
                    }
                    if(c == Maps.ORE){
                        blocks.add(new Ore(i,j));
                    }
                }
            }
        }
    }

    public void setWorldData(String worldData){
        this.worldData = worldData;
        initWorld(worldData, worldSizeX, worldSizeY);

        if(this.getX() != worldSizeX*64){
            this.setSize(worldSizeX * 64, worldSizeY * 64);
            this.getParent().validate();
        }
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        BufferedImage offScreen = new BufferedImage(worldSizeX * 64, worldSizeY * 64, BufferedImage.TYPE_INT_RGB);
        Graphics g = offScreen.getGraphics();

        Image image = Maps.getImage("config/icons/floor.png");
        for(int j = 0; j < worldSizeY; j++){
            for(int i = 0; i < worldSizeX; i++){
                g.drawImage(image, i*64, j*64, this);
            }
        }

//        getScaler();

        for(Block block : blocks){
//            Image scaledImage = block.getImage().getScaledInstance(getScaler() / block.getImage().getWidth(this), -1, Image.SCALE_FAST );
            g.drawImage(block.getImage(), block.getX() * 64, block.getY() * 64, this);

        }

        Image subImage = bufferedImage.getSubimage(65, 105, 65, 105);
//        g.drawImage(subImage, 100, 100, this);

        User playerUser = GameObjects.getPlayerObject();
//        g.drawImage(playerUser.getPlayerBlock().getImage(),
        g.drawImage(subImage,
                playerUser.getPlayerBlock().getX() * 64,
                playerUser.getPlayerBlock().getY() * 64,
                this);

        for(VisualObject object : GameObjects.getVisualObjects()){
            object.draw(g);
        }

        for (User user : GameObjects.getUsers()){
            user.getVisualObject().draw(g);
            g.drawImage(user.getPlayerBlock().getImage(),
                    user.getPlayerBlock().getX() * 64,
                    user.getPlayerBlock().getY() * 64,
                    this);
        }

        if( highlightedBlock != null){
//            System.out.println(highlightedBlock.getRemainingStrength() + "/" + highlightedBlock.getFullStrength());
            Color prev = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(highlightedBlock.getX() *64, highlightedBlock.getY() *64, 64, 64);
            g.setColor(prev);

            if(highlightedBlock.getRemainingStrength() <= (highlightedBlock.getFullStrength() * 0.75)){
                g.drawImage(Maps.getImage("config/icons/crack.png"), highlightedBlock.getX() * 64, highlightedBlock.getY() * 64, this);
            }
            if(highlightedBlock.getRemainingStrength() <= (highlightedBlock.getFullStrength() * 0.25)){
                g.drawImage(Maps.getImage("config/icons/crack2.png"), highlightedBlock.getX() * 64, highlightedBlock.getY() * 64, this);
            }
        }

        graphics.drawImage(offScreen, 0, 0, this);

//        for(int i = 0; i < 831; i += 59){
//            for(int j = 0; j < 410; j += 100 ){
//
//                System.out.println(i + " " + j + " " + (i+59) + " " + (j+100));
//                Image subImage = bufferedImage.getSubimage(i, j, i + 59, j + 100);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                graphics.drawImage(subImage, 0, 0, this);
//            }
//        }


    }

    public void mouseDragged(MouseEvent mouseEvent) {
        mousePoint = mouseEvent.getPoint();
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        mousePoint = mouseEvent.getPoint();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {

    }

    public void mouseReleased(MouseEvent mouseEvent) {

        selection = false;
    }

    public void mouseEntered(MouseEvent mouseEvent) {}

    public void mouseExited(MouseEvent mouseEvent) {}

    public void keyTyped(KeyEvent keyEvent) {
        if(keyEvent.getKeyChar() == 'e'){
            System.out.println("an event");
        }
    }

    public void keyPressed(KeyEvent keyEvent) {

        User player = GameObjects.getPlayerObject();
        PlayerBlock playerBlock = player.getPlayerBlock();
        Block block = null;

        if( keyEvent.getKeyChar() == 'd'){
//            System.out.println("d pressed");
            breakHighlightedBlock();
        } else if (keyEvent.getKeyChar() == 'e'){
            actOnHighlightedBlock();
        } else {
            int keyCode = keyEvent.getKeyCode();

            switch( keyCode ) {
                case KeyEvent.VK_UP:
                    // handle up
                    block = checkMovementY(-1, playerBlock);
                    if( block == null || !block.isSolid()){
                        playerBlock.moveY(-1);
                    } else {
                        highlightBlock( block );
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    // handle down
                    block = checkMovementY(1, playerBlock);
                    if( block == null || !block.isSolid()){
                        playerBlock.moveY(1);
                    } else {
                        highlightBlock(block);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    // handle left
                    block = checkMovementX(-1, playerBlock);
                    if( block == null || !block.isSolid()){
                        playerBlock.moveX(-1);
                    } else {
                        highlightBlock(block);
                    }
                    break;
                case KeyEvent.VK_RIGHT :
                    // handle right
                    block = checkMovementX(1, playerBlock);
                    if( block == null || !block.isSolid()){
                        playerBlock.moveX(1);
                    } else {
                        highlightBlock(block);
                    }
                    break;
            }
        }
    }

    private void actOnHighlightedBlock() {
        if(highlightedBlock != null){
            if(highlightedBlock instanceof DoorBlock){
                boolean hasObject = GameObjects.getPlayerObject().hasItem(Maps.KEY);

            }
        }
    }

    private void breakHighlightedBlock() {

        if(highlightedBlock != null){
            highlightedBlock.hit(1);
            if( highlightedBlock.getRemainingStrength() < 0){
                brokenBlocks.add((highlightedBlock.getY() * worldSizeX) + highlightedBlock.getX());
                highlightedBlock = null;
                System.out.println("Broken" + brokenBlocks.toString());
            }
        }
    }

    public ArrayList<Integer> getBrokenBlocks(){
        return brokenBlocks;
    }

    private void highlightBlock(Block block) {
        this.highlightedBlock = block;
//        this.highlightedX = block.getX() * 64;
//        this.highlightedY = block.getY() * 64;
    }

    private void clearHighlighted() {
        highlightBlock(null);
        highlightedX = -1;
        highlightedY = -1;
    }

    private Block checkMovementY(int movement, PlayerBlock playerBlock) {
        clearHighlighted();
        for(Block block : blocks){
            if(playerBlock.getY() + movement == block.getY() && playerBlock.getX() == block.getX()){
                return block;
            }
        }
        System.out.println("x " + movement + " null");
        return null;
    }

    private Block checkMovementX(int movement, PlayerBlock playerBlock){
        clearHighlighted();
        for(Block block : blocks){
            if(playerBlock.getY() == block.getY() && playerBlock.getX() + movement == block.getX()){
                return block;
            }
        }
        System.out.println("y " + movement + " null");
        return null;
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

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
