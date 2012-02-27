package llantwit.utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MenuAction implements ActionListener{

    public MenuAction(){

    }

    public void actionPerformed(final ActionEvent actionEvent){
        SwingWorker swingWorker = new SwingWorker(){

            @Override
            protected Object doInBackground() throws Exception {
                doAction(actionEvent);
                return null;
            }
        };
        swingWorker.execute();
    }

    public abstract void doAction(ActionEvent actionEvent);
}
