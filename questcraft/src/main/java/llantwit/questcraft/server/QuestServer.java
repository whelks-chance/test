package llantwit.questcraft.server;

import llantwit.simpleServer.xmlserver.SimpleServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 20/12/2011
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */
public class QuestServer {

    public QuestServer() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        QuestServerProcessor gameProcessor = new QuestServerProcessor();

        new SimpleServer(4444, QuestHandlerThread.class, gameProcessor);
    }

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        new QuestServer();
    }
}
