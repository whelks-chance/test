package llantwit.simpleServer.xmlserver;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 25/12/2011
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class EchoProcessor implements ServerProcessor {
    public String process(String uuid, String input) {
        return process(input) + " : " + uuid;
    }

    public String process(String input) {
        return "Echo : " + input;
    }

    public void clientDisconnect(String clientHandlerThread) {
    }

    public void newUser(String uuid) {
    }
}
