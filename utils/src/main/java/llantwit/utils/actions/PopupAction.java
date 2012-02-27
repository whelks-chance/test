package llantwit.utils.actions;

import llantwit.utils.MenuAction;
import llantwit.utils.SinglePanelPopup;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PopupAction extends MenuAction{

    private JPanel panel;

    public PopupAction(JPanel panel){
        super();
        this.panel = panel;
    }

    @Override
    public void doAction(ActionEvent actionEvent) {
        new SinglePanelPopup(panel);
    }
}
