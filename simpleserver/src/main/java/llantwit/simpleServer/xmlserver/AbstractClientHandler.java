package llantwit.simpleServer.xmlserver;

import java.io.*;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 25/12/2011
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractClientHandler extends Thread{


    private SimpleServer simpleServer;
    private Socket socket;
    private boolean running = true;
    public ServerProcessor processor;

    public AbstractClientHandler(Socket socket, ServerProcessor processor) {
        this.socket = socket;
        this.processor = processor;
        System.out.println("Client heard on " + socket.getLocalPort());
    }

    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(outputStream, true);

            String input;
            while(running){
                input = reader.readLine();
                if(input != null) {

                    String output = process(input);

                    writer.println(output);

                }else {
                    running = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

         stopping();
    }

    public abstract String process(String input);

    public abstract void stopping();

}
