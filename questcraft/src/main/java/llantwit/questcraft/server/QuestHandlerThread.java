package llantwit.questcraft.server;

import llantwit.simpleServer.xmlserver.AbstractClientHandler;
import llantwit.simpleServer.xmlserver.ServerProcessor;

import java.net.Socket;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 25/12/2011
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
public class QuestHandlerThread extends AbstractClientHandler {

    private String uuid;

    public QuestHandlerThread(Socket socket, ServerProcessor processor) {
        super(socket, processor);
        this.uuid = UUID.randomUUID().toString();
        processor.newUser(uuid);
    }

    @Override
    public String process(String input) {
        if (processor != null) {
            return processor.process(uuid, input);
        }
        return null;
    }

    @Override
    public void stopping() {
        processor.clientDisconnect(uuid);
    }


}
