package llantwit.cables.script;

import llantwit.cables.Cable;
import llantwit.cables.ConnectionSurface;
import llantwit.utils.MenuAction;
import llantwit.utils.SinglePanelPopup;
import llantwit.utils.panes.TextAreaPane;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProduceScriptAction extends MenuAction {
    private ConnectionSurface connectionSurface;

    public ProduceScriptAction(ConnectionSurface connectionSurface) {
        this.connectionSurface = connectionSurface;
    }

    @Override
    public void doAction(ActionEvent actionEvent) {
        try {
            makeXML(connectionSurface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeXML(ConnectionSurface connectionSurface) throws Exception {

        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        ////////////////////////
        //Creating the XML tree

        //create the root element and add it to the document
        Element root = doc.createElement("shiwaConnection");
        doc.appendChild(root);

        //create a comment and put it in the root element
        Comment comment = doc.createComment("Connection data for joining together the outports " +
                "of a SHIWA bundle and the inports of another");
        root.appendChild(comment);


        ArrayList<Cable> cables = connectionSurface.getCables();
        System.out.println("Adding " + cables.size() + " cables.");
        for(int i = 0; i < cables.size(); i++){
            Cable cable = cables.get(i);
            Element child = doc.createElement("connection_" + i);

            Element input = doc.createElement("input");
            input.setAttribute("node_name", cable.getToBox().getName());
            input.setAttribute("bundle_name", cable.getToBox().getBundleName());

            Element output = doc.createElement("output");
            output.setAttribute("node_name", cable.getFromBox().getName());
            output.setAttribute("bundle_name", cable.getFromBox().getBundleName());


            child.appendChild(input);
            child.appendChild(output);

            root.appendChild(child);
        }

        writeXmlFile(doc);
    }

    public void writeXmlFile(Document doc) {
        File saveFile = getSaveFile();
        try {

            Source source = new DOMSource(doc);
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setParameter(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            if(saveFile != null){
                Result result = new StreamResult(saveFile);
                xformer.transform(source, result);
            }

            Writer outputWriter = new StringWriter();
            Result stringOut = new StreamResult(outputWriter);
            xformer.transform(source, stringOut);
            connectionSurface.showDescription(outputWriter.toString(), false);

            new SinglePanelPopup(new TextAreaPane(outputWriter.toString()));

            Result systemOut = new StreamResult(System.out);
            xformer.transform(source, systemOut);

        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }
    }


    public File getSaveFile(){
        File file = null;
        try{
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = chooser.showDialog(connectionSurface, "Save");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                System.out.println(file.getAbsolutePath());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

}
