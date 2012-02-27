package llantwit.utils.panes;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextAreaPane extends JPanel {
    public TextAreaPane(){
        init("");
    }

    public TextAreaPane(String string){
        init(string);
    }

    private void init(String string){
        this.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setColumns(25);
        textArea.setRows(8);
        textArea.setText(string);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setSize(200, 100);
        this.validate();
    }
}
