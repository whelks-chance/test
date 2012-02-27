package llantwit.simpleServer.xmlserver;

import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 20/12/2011
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class ClientHandlerThread extends AbstractClientHandler {

    public ClientHandlerThread(Socket socket, ServerProcessor processer) {
        super(socket, processer);
    }

    public String process(String input) {
        if (processor != null) {
            return processor.process(input);
        }
        return null;
    }

    public void stopping(){

        System.out.println("Stopping server handler");
    }
}
