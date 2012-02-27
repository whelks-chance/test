package llantwit.utils.panes;

import llantwit.utils.ui.HTMLPopup;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public class HTMLPane extends JPanel{

    public HTMLPane(String title, String content){
        init(title, content);
    }

    private void init(String title, String content) {
        this.setLayout(new BorderLayout());

        String html = HTMLPopup.htmlify(title, content);

        JEditorPane editorpane = HTMLPopup.getEditorPane(html);
        this.add(editorpane, BorderLayout.CENTER);
        this.setSize(200, 100);
        this.validate();
    }
}
