package llantwit.simpleServer.xmlclient;

import llantwit.utils.xml.SimpleXML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 25/12/2011
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */
public class XMLClient extends SimpleClient{


    private XMLProcessor xmlProcessor;

    public XMLClient(String ip, int port, XMLProcessor xmlProcessor) throws IOException {
        super(ip, port);
        this.xmlProcessor = xmlProcessor;
    }

    @Override
    public void process(String receivedString) {
        try {
            Document document = SimpleXML.documentFromString(receivedString);
            xmlProcessor.process( document );
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String xmlify(Object o) {
       return xmlProcessor.xmlify(o);
    }

}
