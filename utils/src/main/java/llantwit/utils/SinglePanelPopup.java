package llantwit.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 22/11/2011
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class SinglePanelPopup extends JDialog{
    public SinglePanelPopup(JPanel panel){
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                dispose();
            }
        });
        this.add(closeButton, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
