package llantwit.formulaone.client.trackdesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 07/02/2012
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public class TrackDesignMainPanel extends JPanel implements ActionListener {

    private JTextArea textArea;
    private TrackDesignPanel trackDesignPanel;

    public TrackDesignMainPanel(TrackDesignPanel trackDesignPanel){
        this.trackDesignPanel = trackDesignPanel;

        this.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1,4));
        JButton button = new JButton("Trace track");
        button.addActionListener(this);
        buttonPanel.add(button);
        this.add(buttonPanel, BorderLayout.NORTH);

        this.add(new JScrollPane(trackDesignPanel), BorderLayout.CENTER) ;

        JPanel lowerPanel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        lowerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(lowerPanel, BorderLayout.SOUTH);
    }

    public void showText(String text){
        textArea.append(text);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        trackDesignPanel.trace();
    }
}
