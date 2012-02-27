package llantwit.questcraft.client;

import llantwit.questcraft.server.QuestServerProcessor;
import llantwit.simpleServer.xmlclient.XMLProcessor;
import llantwit.questcraft.gameObjects.GameObjects;
import llantwit.questcraft.gameObjects.User;
import llantwit.utils.xml.DomPain;
import llantwit.utils.xml.SimpleXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 25/12/2011
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class QuestXMLProcessor implements XMLProcessor {

    private QuestCraftPanel questCraftPanel;

    public QuestXMLProcessor(QuestCraftPanel questCraftPanel) {

        this.questCraftPanel = questCraftPanel;
    }

    public void process(Document doc) {
//        System.out.println("Got some xml");

        String id = DomPain.getFirstChildElementContent(doc.getDocumentElement(), QuestServerProcessor.REPLY_TO);
        if(!questCraftPanel.getPlayerID().equals(id)){
            questCraftPanel.setPlayerID(id);
        }

        Element worldDataElement = DomPain.getFirstChildElement(doc.getDocumentElement(), QuestServerProcessor.MAP);
        if(worldDataElement != null){
            String sizeString = DomPain.getFirstChildElementContent(worldDataElement, QuestServerProcessor.MAPSIZE);
            String mapString = DomPain.getFirstChildElementContent(worldDataElement, QuestServerProcessor.MAPDATA);
            questCraftPanel.setWorldSize(sizeString);
            questCraftPanel.setWorldData(mapString);
        }

        Element disconnected = DomPain.getFirstChildElement(doc.getDocumentElement(), QuestServerProcessor.DISCONNECTED_USERS);
        for (int i = 0; i < disconnected.getChildNodes().getLength(); i++) {
              Node node = disconnected.getChildNodes().item(i);
            String disconnectedID = node.getTextContent();
            GameObjects.removeUser(disconnectedID);
        }

        ArrayList<User> users = QuestServerProcessor.getKnownUsers(doc);

        for( User userIn : users){
            if(!userIn.getUserUUID().equals( questCraftPanel.getPlayerID() )){

                User existingUser = GameObjects.getUser(userIn.getUserUUID());
                if( existingUser != null ){
                    existingUser.updateVisuals( userIn );
                } else {
                    GameObjects.addUser(userIn);
                }
            }
        }

        String carrying = DomPain.getFirstChildElementContent(doc.getDocumentElement(), QuestServerProcessor.CARRYING);
        GameObjects.getPlayerObject().updateItemList(carrying);

//        user.setVisualObject( new PointlessSquare(x, y, 50, 50));
//        GameObjects.putVisualObject( user.getVisualObject() );

    }

    public String xmlify(Object o) {
        if(o instanceof QuestCraftPanel){
            try {
                return xmlify((QuestCraftPanel)o);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }

    public String xmlify(QuestCraftPanel questCraftPanel) throws Exception {
        String userID = questCraftPanel.getPlayerID();
        User playerObject = GameObjects.getPlayerObject();
        if( playerObject != null){

            Document doc = SimpleXML.makeXMLDocument(QuestServerProcessor.ROOT);

            Element rootElement = doc.getDocumentElement();

            Element userDataElement = doc.createElement(QuestServerProcessor.USER_DATA);

            if( questCraftPanel.startup ){
                Element visualElement = SimpleXML.createElement(doc, userDataElement, QuestServerProcessor.VISUALS, null);
                SimpleXML.createElement(doc, visualElement, QuestServerProcessor.VISUAL_WIDTH, String.valueOf(playerObject.getPlayerBlock().getWidth()));
                SimpleXML.createElement(doc, visualElement, QuestServerProcessor.VISUAL_HEIGHT, String.valueOf(playerObject.getPlayerBlock().getHeight()));
                questCraftPanel.startup = false;
            }

            Element idElement = doc.createElement(QuestServerProcessor.USER_ID);
            idElement.setTextContent(userID);
            userDataElement.appendChild(idElement);

            Element userX = doc.createElement(QuestServerProcessor.USER_X_LOCATION);
            userX.setTextContent(String.valueOf(playerObject.getPlayerBlock().getX()));
            userDataElement.appendChild(userX);

            Element userY = doc.createElement(QuestServerProcessor.USER_Y_LOCATION);
            userY.setTextContent(String.valueOf(playerObject.getPlayerBlock().getY()));
            userDataElement.appendChild(userY);

            if(questCraftPanel.getBrokenBlocks().size() > 0){
                String brokenBlockString = "";
                for(Integer i : questCraftPanel.getBrokenBlocks()){
                    brokenBlockString += i + ",";
                }

                SimpleXML.createElement(doc, doc.getDocumentElement(), QuestServerProcessor.BROKEN_BLOCKS, brokenBlockString);
                questCraftPanel.getBrokenBlocks().clear();
            }

            rootElement.appendChild(userDataElement);

            String xmlString = SimpleXML.getXMLasString( doc );

//            System.out.println(xmlString);

            return SimpleXML.flattenString( xmlString );
        }
        return null;
    }
}
