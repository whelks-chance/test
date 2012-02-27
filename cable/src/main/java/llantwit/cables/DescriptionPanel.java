package llantwit.cables;

import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class DescriptionPanel {
    private JTextComponent jTextComponent;

    public DescriptionPanel(JTextComponent jTextArea) {

        this.jTextComponent = jTextArea;
    }

    public void putText(String description, boolean append) {
        String text = "";
        if(append){
           text += jTextComponent.getText();
        }
        jTextComponent.setText(text + "\n" + description);
    }

    public Component getTextComponent() {
        return jTextComponent;
    }
}
