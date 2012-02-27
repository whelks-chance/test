package llantwit.questcraft.server;

import llantwit.questcraft.gameObjects.User;
import llantwit.questcraft.gameObjects.visualObjects.Monster;
import llantwit.questcraft.gameObjects.visualObjects.PointlessSquare;
import llantwit.questcraft.maps.Maps;
import llantwit.simpleServer.xmlserver.ServerProcessor;
import llantwit.utils.drawing.VisualObject;
import llantwit.utils.xml.DomPain;
import llantwit.utils.xml.SimpleXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 20/12/2011
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public class QuestServerProcessor implements ServerProcessor {

    public static final String COMMENT = "A simple comment";
    public static final String ROOT = "questCraft";
    public static final String RECEIVED = "Received";
    public static final String MAP = "map";
    public static final String MAPDATA = "mapData";
    public static final String MAPSIZE = "mapSize";

    public static final String USER_DATA = "userData";
    public static final String KNOWN_USERS = "knownUsers";
    public static final String USER_ID = "userID";
    public static final String USER_X_LOCATION = "userX";
    public static final String USER_Y_LOCATION = "userY";
    public static final String VISUALS = "visuals";
    public static final String VISUAL_HEIGHT = "visualHeight";
    public static final String VISUAL_WIDTH = "visualWidth";
    public static final String DISCONNECTED_USERS = "disconnectedUsers";
    public static final String REPLY_TO = "replyTo";
    public static final String CARRYING = "carrying";
    public static final String BROKEN_BLOCKS = "brokenBlocks";

    private HashMap<String, User> userHashMap = new HashMap<String, User>();
    //    private ArrayList<String> userUUIDs = new ArrayList<String>();
    private ArrayList<String> disconnectedUsers = new ArrayList<String>();
    private ArrayList<Monster> monsters = new ArrayList<Monster>();

    int mapWidth = 15;
    int mapHeight = 10;
    private char [] map;


    public QuestServerProcessor(){
        map = Maps.createMap(mapWidth, mapHeight);
        new GameThread(500).start();
        monsters.add(new Monster(5, 5));
        monsters.add(new Monster(8, 5));
    }

    public String process(String uuid, String input) {
        System.out.println("Processor in : " + input);
        String output = "error";
        try {
            Document outDocument = SimpleXML.makeXMLDocument(ROOT);
            SimpleXML.addRootComment(outDocument, COMMENT);

            if(input.contains("?xml version=")){

                Document inDocument = SimpleXML.documentFromString(input);

                Element userData = DomPain.getFirstChildElement(inDocument.getDocumentElement(), USER_DATA);

                String userUUID = DomPain.getFirstChildElementContent(userData, USER_ID);
                if(userUUID == null || userUUID.equals("")){
                    userUUID = uuid;
                }

                String broken = DomPain.getFirstChildElementContent(inDocument.getDocumentElement(), BROKEN_BLOCKS);
                if(broken != null){
                    String[] blocks = broken.split(",");
                    for (String block : blocks) {
                        char newChar = Maps.getRandomChar(new char[]{' ', '0', '*'}, new int[]{3, 6, 1});
                        map[Integer.parseInt(block)] = newChar;
                    }
                }

                User user = getUser(userUUID);

                VisualObject playerObject = user.getVisualObject();
                playerObject.setX(Integer.parseInt(DomPain.getFirstChildElementContent(userData, USER_X_LOCATION)));
                playerObject.setY(Integer.parseInt(DomPain.getFirstChildElementContent(userData, USER_Y_LOCATION)));

                checkPositionInteraction(user);

                Element disconnected = outDocument.createElement( QuestServerProcessor.DISCONNECTED_USERS );
                for(String id : disconnectedUsers){

                    SimpleXML.createElement(outDocument, disconnected, USER_ID, id);

                }
                outDocument.getDocumentElement().appendChild( disconnected );

                Element otherUsers = outDocument.createElement( QuestServerProcessor.KNOWN_USERS );
                for(User knownUser : userHashMap.values()){

                    Element userElement =  SimpleXML.createElement(outDocument, otherUsers, QuestServerProcessor.USER_DATA, null);
                    SimpleXML.createElement(outDocument, userElement, USER_ID, knownUser.getUserUUID());
                    SimpleXML.createElement(outDocument, userElement, USER_X_LOCATION, String.valueOf(knownUser.getVisualObject().getX()));
                    SimpleXML.createElement(outDocument, userElement, USER_Y_LOCATION, String.valueOf(knownUser.getVisualObject().getY()));

                }
                outDocument.getDocumentElement().appendChild( otherUsers );

                Element element = outDocument.createElement(REPLY_TO);
                element.setTextContent(userUUID);
                outDocument.getDocumentElement().appendChild(element);

                Element mapElement = outDocument.createElement(MAP);
                SimpleXML.createElement(outDocument, mapElement, MAPDATA, new String(map));
                SimpleXML.createElement(outDocument, mapElement, MAPSIZE, mapWidth + "," + mapHeight + ",1");
                outDocument.getDocumentElement().appendChild(mapElement);

                String items = "";
                for(Character item : user.getItemList()){
                    items += item;
                }
                SimpleXML.createElement(outDocument, outDocument.getDocumentElement(), CARRYING, items);

            } else {

                Element element = outDocument.createElement(RECEIVED);
                element.setTextContent(input);
                outDocument.getDocumentElement().appendChild(element);
            }

            output = SimpleXML.getXMLasString(outDocument);
            System.out.println("Processor out : " + output);

            output = SimpleXML.flattenString(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private void checkPositionInteraction(User user) {
        VisualObject visualObject = user.getVisualObject();
        int x = visualObject.getX();
        int y = visualObject.getY();
        int pos = (y * mapWidth) + x;
        if(pos < map.length){
            char posChar = map[pos];
            if(posChar != ' '){
                switch (posChar){
                    case '*':
                        user.giveItem(Maps.DIAMOND);
                        map[pos] = ' ';
                        break;

                    case '0':
                        user.giveItem(Maps.ORE);
                        map[pos] = ' ';
                }
            }
        }
    }

    public String process(String input) {
        return null;
    }

    public void clientDisconnect(String clientUUID) {
//        userUUIDs.remove(clientUUID);
        disconnectedUsers.add(clientUUID);
        userHashMap.remove(clientUUID);
    }

    public void newUser(String uuid) {
//        userUUIDs.add(uuid);
    }

    public static ArrayList<User> getKnownUsers(Document document){
        Element knownUsersElement = DomPain.getFirstChildElement(document.getDocumentElement(), QuestServerProcessor.KNOWN_USERS);

        List<Element> knownUsers = DomPain.getChildElements( knownUsersElement, QuestServerProcessor.USER_DATA);
        ArrayList<User> users = new ArrayList<User>();

        for(Element element : knownUsers){

            String userID = DomPain.getFirstChildElementContent(element, QuestServerProcessor.USER_ID);
            int x = Integer.parseInt(DomPain.getFirstChildElementContent(element, QuestServerProcessor.USER_X_LOCATION));
            int y = Integer.parseInt(DomPain.getFirstChildElementContent(element, QuestServerProcessor.USER_Y_LOCATION));

            User user = new User(userID);
            user.setVisualObject( new PointlessSquare(x, y, 50, 50));
            user.getPlayerBlock().setX(x);
            user.getPlayerBlock().setY(y);

            users.add(user);
        }

        return users;
    }

    private User getUser(String userUUID) {
        User user;
        if( userHashMap.containsKey(userUUID)){
            user = userHashMap.get(userUUID);
        } else {
            user = new User( userUUID );
            user.setVisualObject( new PointlessSquare(0,0,0,0));
            userHashMap.put(userUUID, user);
        }
        return user;
    }

    class GameThread extends Thread{
        private boolean end;
        private int time;

        public void end(){
            end = true;
        }
        public void run(){
            while(!end){
                Random random = new Random(System.currentTimeMillis());
                for( Monster monster : monsters){
                    int rand = random.nextInt(10);
                    int pos = (monster.getY() * mapWidth) + monster.getX();

                    char c;

                    switch (rand){
                        case 1:
                            c = map[pos + 1];
                            if( c == ' '){
//                                System.out.println( c + " = * *"  );
                                map[pos] = ' ';
                                monster.moveX(1);
                            } else{
//                                System.out.println("Blocked moving right");
                            }
                            break;
                        case 2:
                            c = map[pos - 1];
                            if( c == ' '){
//                                System.out.println( c + " = * *"  );
                                map[pos] = ' ';
                                monster.moveX(-1);
                            } else{
//                                System.out.println("Blocked moving left");
                            }
                            break;
                        case 3:
                            c = map[pos + mapWidth];
                            if(c == ' '){
//                                 System.out.println( c + " = * *"  );
                                map[pos] = ' ';
                                monster.moveY(1);
                            } else{
//                                System.out.println("Blocked moving down");
                            }
                            break;
                        case 4:
                            c = map[pos - mapWidth];
                            if( c == ' '){
//                                 System.out.println( c + " = * *"  );
                                map[pos] = ' ';
                                monster.moveY(-1);
                            } else{
//                                System.out.println("Blocked moving up");
                            }
                            break;
                    }

                    pos = (monster.getY() * mapWidth) + monster.getX();
                    map[pos] = '@';
                }

                try {
                    Thread.sleep(time);
                } catch (InterruptedException ignored) {}
            }
        }

        public GameThread(final int time){
            this.time = time;
        }
    }

}
