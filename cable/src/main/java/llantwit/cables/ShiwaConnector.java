package llantwit.cables;

import llantwit.cables.Text.TextStrings;
import llantwit.cables.bundle.LoadBundleAction;
import llantwit.cables.script.ProduceScriptAction;
import llantwit.utils.LFrame;
import llantwit.utils.actions.PopupAction;
import llantwit.utils.panes.TextAreaPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShiwaConnector{

    private ConnectionSurface connectionSurface;

    public ShiwaConnector(){
        JTextArea textArea = new JTextArea();
        textArea.setRows(5);
        DescriptionPanel descriptionPanel = new DescriptionPanel(textArea);
        connectionSurface = new ConnectionSurface(descriptionPanel);

        LFrame lFrame = new LFrame();
        lFrame.addMenuItem("Bundle", "Load bundle 1", new LoadBundleAction(connectionSurface, LoadBundleAction.PortType.output));
        lFrame.addMenuItem("Bundle", "Load bundle 2", new LoadBundleAction(connectionSurface, LoadBundleAction.PortType.input));
        lFrame.addMenuItem("Script", "Produce script", new ProduceScriptAction(connectionSurface));
        lFrame.addMenuItem("Help", "Help", new PopupAction(
                new TextAreaPane(
                        TextStrings.getHelpString()
                )
        ));

        lFrame.add(descriptionPanel.getTextComponent(), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(connectionSurface);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        lFrame.add(scrollPane, BorderLayout.CENTER);

//        connectionSurface.addBox(new Box("F1r5T", 100, 100, 100, 100));
//        connectionSurface.addBox(new Box("Second", 300, 100, 100, 100));
//        connectionSurface.addBox(new Box("Third", 100, 300, 100, 100 ));

        lFrame.setVisible(true);
        System.out.println(lFrame.isVisible());
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ShiwaConnector();
            }
        });
    }


}
