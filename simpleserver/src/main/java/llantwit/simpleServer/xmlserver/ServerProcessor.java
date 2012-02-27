package llantwit.simpleServer.xmlserver;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 20/12/2011
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
public interface ServerProcessor {

    String process( String uuid, String input);

    String process(String input);

    void clientDisconnect(String clientHandlerThread);

    void newUser(String uuid);
}
