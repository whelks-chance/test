package llantwit.questcraft.gameObjects;

import llantwit.utils.drawing.VisualObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class GameObjects {

    private static GameObjects gameObjects = new GameObjects();
    private ArrayList<VisualObject> visualObjects;

    private User playerObject;
    private static HashMap<String, User> knownUsers;

    private GameObjects(){
        visualObjects = new ArrayList<VisualObject>();
        knownUsers = new HashMap<String, User>();
    }

    public static GameObjects getGameObjects(){
        return gameObjects;
    }

    public static ArrayList<VisualObject> getVisualObjects() {
        return getGameObjects().visualObjects;
    }

    public static void putVisualObject( VisualObject visualObject){
        getGameObjects().visualObjects.add(visualObject);
    }

    public static void setPlayerObject( User user ){
        getGameObjects().playerObject = user;
    }

    public static User getPlayerObject(){
        return getGameObjects().playerObject;
    }

    public static User getUser(String userUUID) {
        return knownUsers.get(userUUID);
    }

    public static void addUser(User userIn) {
        knownUsers.put(userIn.getUserUUID(), userIn);
    }

    public static Collection<User> getUsers() {
        return knownUsers.values();
    }

    public static void removeUser(String disconnectedID) {
        if(knownUsers.containsKey(disconnectedID)){
            knownUsers.remove(disconnectedID);
        }
    }
}
