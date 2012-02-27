package llantwit.questcraft.gameObjects;

import llantwit.questcraft.gameObjects.visualObjects.PlayerBlock;
import llantwit.utils.drawing.VisualObject;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private String userUUID;
    private VisualObject visualObject;
    private PlayerBlock playerBlock;
    private ArrayList<Character> carrying;

    public User(String userUUID) {

        this.userUUID = userUUID;
        setPlayerBlock(new PlayerBlock(1, 1));
        carrying = new ArrayList<Character>();
    }

    public VisualObject getVisualObject() {
        return visualObject;
    }

    public void setVisualObject(VisualObject visualObject) {
        this.visualObject = visualObject;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void updateVisuals(User userIn) {

        int x = userIn.getVisualObject().getX();
        int y = userIn.getVisualObject().getY();
        this.getVisualObject().setX(x);
        this.getVisualObject().setY(y);

        PlayerBlock playerBlock = this.getPlayerBlock();
        playerBlock.setX(x);
        playerBlock.setY(y);
    }

    public PlayerBlock getPlayerBlock() {
        return playerBlock;
    }

    public void setPlayerBlock(PlayerBlock playerBlock) {
        this.playerBlock = playerBlock;
    }

    public void giveItem(char item) {
        carrying.add(item);
    }

    public ArrayList<Character> getItemList(){
        return carrying;
    }

    public void updateItemList(String carryingString) {
        carryingString.replaceAll(",", "");
        char [] chars = carryingString.toCharArray();
//        String[] items = carryingString.split(",");
        carrying.clear();
        for(char c : chars){
            carrying.add(c);
        }
        System.out.println(carryingString);
    }

    public boolean hasItem(String key) {
        return carrying.contains(key);
    }
}
