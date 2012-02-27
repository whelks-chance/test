package llantwit.simpleServer.xmlserver;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 20/12/2011
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */
public class SimpleServer {

    private boolean listening = true;

    public SimpleServer(int port, Class<? extends AbstractClientHandler> clientHandlerClass, ServerProcessor processor) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ServerSocket serverSocket = new ServerSocket(port);

        while(listening){
            Socket socket = serverSocket.accept();
            Constructor<? extends AbstractClientHandler> constructor = clientHandlerClass.getDeclaredConstructor(Socket.class, ServerProcessor.class);
            AbstractClientHandler clientHandler = constructor.newInstance(socket, processor);
            clientHandler.start();
        }

    }

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        new SimpleServer(4444, ClientHandlerThread.class, new EchoProcessor());
    }

}
