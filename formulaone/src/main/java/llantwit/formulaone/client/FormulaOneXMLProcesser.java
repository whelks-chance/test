package llantwit.formulaone.client;

import llantwit.simpleServer.xmlclient.XMLProcessor;
import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/3/12
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormulaOneXMLProcesser implements XMLProcessor {

    private FormulaOnePanel formulaOnePanel;

    public FormulaOneXMLProcesser(FormulaOnePanel formulaOnePanel) {
        this.formulaOnePanel = formulaOnePanel;
    }

    public void process(Document document) {
    }

    public String xmlify(Object o) {
        return null;
    }
}
