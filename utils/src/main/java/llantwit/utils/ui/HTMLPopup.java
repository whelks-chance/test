package llantwit.utils.ui;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 24/12/2011
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
public class HTMLPopup {

    public void showPopup(Component component, String title, String content){
        String htmlString = htmlify( title, content );
        showPopupHTML(component, title, htmlString);
    }

    public static String htmlify(String title, String content){
       StringBuilder htmlString = new StringBuilder("<html>")
                .append("<body>")
                .append("<table border='0px' cxellpadding='10px' height='100%'>")
                .append("<tr>")
                .append("<td valign='center'>")
                .append(title)
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td>")
                .append(content)
                .append("<td>")
                .append("</td>")
                .append("</table>")
                .append("</body>")
                .append("</html>");
        return htmlString.toString();
    }

    public void showPopupHTML(Component component, String title, String htmlString){
        JEditorPane editorPane = getEditorPane( htmlString );
        JScrollPane scrollPane = new JScrollPane(editorPane);
        JOptionPane.showMessageDialog(component, scrollPane, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static JEditorPane getEditorPane(String html) {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.setFocusable(true);
        editorPane.setContentType("text/html");
        editorPane.setText(html);
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(final HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            // TIP: Show hand cursor
                            SwingUtilities.getWindowAncestor(editorPane).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            // TIP: Show URL as the tooltip
                            editorPane.setToolTipText(e.getURL().toExternalForm());
                        }
                    });
                } else if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            // Show default cursor
                            SwingUtilities.getWindowAncestor(editorPane).setCursor(Cursor.getDefaultCursor());
                            // Reset tooltip
                            editorPane.setToolTipText(null);
                        }
                    });
                } else if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        });
        return editorPane;
    }
}
