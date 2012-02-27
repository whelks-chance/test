package llantwit.simpleServer.xmlclient;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 25/12/2011
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class RawTextClient extends SimpleClient{

    public RawTextClient(String ip, int port, Scanner scanner) throws IOException {
        super(ip, port, scanner);
    }

    @Override
    public void process(String receivedString) {
        System.out.println( receivedString );
    }

    public static void main(String[] args) throws IOException {
        new RawTextClient("127.0.0.1", 4444, new Scanner(System.in));
    }
}
