package llantwit.simpleServer.xmlclient;

import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 25/12/2011
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public interface XMLProcessor {
    public void process(Document document);

    public String xmlify(Object o);
}
