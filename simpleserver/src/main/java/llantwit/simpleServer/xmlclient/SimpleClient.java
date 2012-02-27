package llantwit.simpleServer.xmlclient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 20/12/2011
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class SimpleClient {

    private boolean open = true;
    private Socket socket;
    private PrintWriter writer;
    public BufferedReader reader;
    public ClientProcessor processor;
    private boolean ready;

    public SimpleClient(String ip, int port) throws IOException {
        init(ip, port);
    }

    public SimpleClient(String ip, int port, Scanner scanner) throws IOException {
        init(ip, port);
        while(open){
            String lineIn = scanner.nextLine();
            if( lineIn != null && socket.isConnected()) {
                writer.println(lineIn);

                preprocess(getResult(reader));

            } else {
                open = false;
            }
        }
    }

    private void init(String ip, int port) throws IOException {
        socket = new Socket(ip, port);

        InputStream inputStream = socket.getInputStream();

        OutputStream outputStream = socket.getOutputStream();

        reader = new BufferedReader(new InputStreamReader(inputStream));

        writer = new PrintWriter(outputStream, true);

        ready = true;
    }

    public void preprocess(String result) {
        if(processor == null){
            process(result);
        } else {
            processor.process(result);
        }
    }

    public abstract void process(String receivedString);


    public String send(String lineIn) {
        if( lineIn != null && socket.isConnected()) {
            writer.println(lineIn);

            try {
                return getResult(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return lineIn;
    }

    public String sendAndProcessResponse(String lineIn){
        String reply = send( lineIn );
        if(reply != null){
            preprocess( reply );
        }
        return reply;
    }

    private String getResult(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    public void setProcessor(ClientProcessor processor) {
        this.processor = processor;
    }

    public boolean isReady() {
        return ready;
    }

}
