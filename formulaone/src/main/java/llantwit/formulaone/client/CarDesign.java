package llantwit.formulaone.client;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/5/12
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CarDesign extends JPanel {
    public CarDesign(){

        this.setLayout(new GridLayout(5, 2));

        for(int i = 0; i < 5; i++){
            this.add(new Selector(50));
        }
    }

    class Selector extends JPanel implements ActionListener, ChangeListener {

        private int value;
        private JSlider slider;
        private JTextField textField;

        public int getValue(){
            return value;
        }

        public void setValue(int value){
            this.value = value;
        }

        public Selector(int value){
            this.value = value;
            this.setLayout(new GridLayout(1,3));

            slider = new JSlider();
            slider.setMaximum(100);
            slider.setMinimum(0);
            slider.setMajorTickSpacing(10);
            slider.setMinorTickSpacing(5);
            slider.setPaintTicks(true);
            slider.addChangeListener(this);
            textField = new JTextField();
            textField.setActionCommand("textField");
            textField.addActionListener(this);
            JPanel buttonPanel = new JPanel(new GridLayout(2,1));

            JButton upButton = new JButton("up");
            upButton.setActionCommand("upButton");
            upButton.addActionListener(this);
            JButton downButton = new JButton("down");
            downButton.setActionCommand("downButton");
            downButton.addActionListener(this);

            buttonPanel.add(upButton);
            buttonPanel.add(downButton);

            add(slider);
            add(textField);
            add(buttonPanel);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getActionCommand().equals("upButton")){
                value++;
            }
            if(actionEvent.getActionCommand().equals("downButton")){
                value--;
            }
            if(actionEvent.getActionCommand().equals("textField")){
                try{
                    value = Integer.parseInt(textField.getText());
                }catch (Exception ignored){}
            }
            updateEverything();
        }

        public void stateChanged(ChangeEvent changeEvent) {
            if(changeEvent.getSource() == slider){
                value = slider.getValue();
            }
            updateEverything();
        }

        private void updateEverything() {
            slider.setValue(value);
            textField.setText(String.valueOf(value));

        }
    }
}
