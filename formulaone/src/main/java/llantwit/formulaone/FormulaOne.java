package llantwit.formulaone;

import llantwit.formulaone.client.CarDesign;
import llantwit.formulaone.client.FormulaOnePanel;
import llantwit.formulaone.client.trackdesign.TrackDesignMainPanel;
import llantwit.formulaone.client.trackdesign.TrackDesignPanel;
import llantwit.utils.LFrame;
import llantwit.utils.actions.PopupAction;
import llantwit.utils.panes.HTMLPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/3/12
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormulaOne {

    public FormulaOne(){
        LFrame lFrame = new LFrame();
        lFrame.setSize(16*64, 11 * 64);
        lFrame.addMenuItem("Help", "Help", new PopupAction(
                new HTMLPane("Help", "Here will be some help, perhaps.")
        ));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(new FormulaOnePanel("127.0.0.1", 4444), "Track");

        tabbedPane.add(new TrackDesignMainPanel(new TrackDesignPanel()), "Track Design");

        tabbedPane.add(new CarDesign(), "Car");

        lFrame.add(tabbedPane , BorderLayout.CENTER);

        lFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new FormulaOne();
                    }
                });

    }
}
