package llantwit.utils.actions;

import llantwit.utils.MenuAction;

import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuitAction extends MenuAction {

    @Override
    public void doAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
