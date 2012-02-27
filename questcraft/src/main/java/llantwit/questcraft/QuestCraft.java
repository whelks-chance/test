package llantwit.questcraft;

import llantwit.questcraft.client.QuestCraftPanel;
import llantwit.utils.LFrame;
import llantwit.utils.actions.PopupAction;
import llantwit.utils.panes.HTMLPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public class QuestCraft {

    public QuestCraft(String finalIp){

        LFrame lFrame = new LFrame();
        lFrame.setSize(16*64, 11 * 64);
        lFrame.addMenuItem("Help", "Help", new PopupAction(
                new HTMLPane("Help", "Here will be some help, perhaps.")
        ));

        lFrame.add(new QuestCraftPanel(finalIp), BorderLayout.CENTER);

        lFrame.setVisible(true);
    }

    public static void main(String[] args) {
        String ip = "";
        if(args.length > 0){
            ip = args[0];
        }
        final String finalIp = ip;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuestCraft(finalIp);
            }
        });

    }
}
