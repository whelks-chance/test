package llantwit.cables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionSurface extends JPanel implements MouseListener, MouseMotionListener{

    ArrayList<Box> boxes = new ArrayList<Box>();
    private boolean selection;
    private Box fromBox;
    private Point mousePoint;
    private DescriptionPanel descriptionPanel;

    public ConnectionSurface(){
        init();
    }

    public ConnectionSurface(DescriptionPanel descriptionPanel) {
        this.descriptionPanel = descriptionPanel;
        init();
    }

    private void init(){
        addMouseListener(this);
        addMouseMotionListener(this);
        new RepaintThread(this, 100).start();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    private Box getBox(Point point) {
        for(Box box : boxes){
            if (box.getBounds().contains(point)){
                selection = true;
                return box;
            }
        }
        return null;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        Box box = getBox(mouseEvent.getPoint());
        if(box != null){
            System.out.println("From box " + box.getName());
            showDescription("Bundle " + box.getBundleName(), false);
            showDescription("Port name : " + box.getName(), true);
            showDescription("Description : " + box.getDescription(), true);
            showDescription("Data type : " + box.getType(), true);
            fromBox = box;
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        Box box = getBox(mouseEvent.getPoint());
        if(box != null && fromBox != null){
            System.out.println("To box " + box.getName());
            Cable cable = createCable(fromBox, box);
        }
        selection = false;
    }

    private Cable createCable(Box fromBox, Box toBox) {
        if(fromBox != toBox){
            Cable cable = new Cable(fromBox, toBox);
            fromBox.setOutCable(cable);
            toBox.setInCable(cable);
            return cable;
        } else {
            return null;
        }
    }

    public void mouseEntered(MouseEvent mouseEvent) {}

    public void mouseExited(MouseEvent mouseEvent) {}

    public void addBox(Box box) {
        boxes.add(box);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Box box : boxes){
            Rectangle2D rectangle2D = box.getBounds();
            int x;
            if(box instanceof InputBox){
                x = (int)rectangle2D.getX();
            } else if (box instanceof OutputBox){
                x = this.getSize().width - 150;
                box.setX(x);
            } else {
                x = (int)rectangle2D.getX();
            }
            g.draw3DRect(x, (int)rectangle2D.getY(), (int)rectangle2D.getWidth(), (int)rectangle2D.getHeight(), true);
            g.drawString(box.getName(), x, (int)rectangle2D.getY());

            for(Cable cable : box.getCables()){
                if(cable != null){
                    Point from = cable.getFrom();
                    Point to = cable.getTo();
                    g.drawLine(from.x, from.y, to.x, to.y);
                    g.fillOval(to.x -5, to.y-5, 10, 10);
                }
            }
        }
        if(selection && fromBox != null){
            g.drawLine(fromBox.getExit().x, fromBox.getExit().y, mousePoint.x, mousePoint.y);
        }
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        mousePoint = mouseEvent.getPoint();

    }

    public void mouseMoved(MouseEvent mouseEvent) {
        mousePoint = mouseEvent.getPoint();
    }

    public void addInputBox(String name) {
        addInputBox(name, "", "", "");
    }

    public void addOutputBox(String name) {
        addOutputBox(name, "", "", "");
    }

    public ArrayList<Cable> getCables() {
        ArrayList<Cable> cables = new ArrayList<Cable>();
        for(Box box : boxes){
            if(box instanceof InputBox){
                cables.add(box.getOutCable());
            }
        }
        return cables;
    }

    public void addOutputBox(String name, String bundleName, String type, String description) {
        int numberOutputBoxes = 1;
        for(Box box : boxes){
            if(box instanceof OutputBox){
                numberOutputBoxes ++;
            }
        }
        Box outputBox = new OutputBox(name, this.getSize().width - 150, 100 * numberOutputBoxes, 100, 50);
        setHeight((100 * numberOutputBoxes) + 100);
        outputBox.setType(type);
        outputBox.setDescription(description);
        outputBox.setBundleName(bundleName);
        boxes.add(outputBox);
    }

    public void addInputBox(String name, String bundleName, String type, String description) {
        int numberInputBoxes = 1;
        for(Box box : boxes){
            if(box instanceof InputBox){
                numberInputBoxes ++;
            }
        }
        Box inputBox = new InputBox(name, 75, 100 * numberInputBoxes, 100, 50);
        setHeight((100 * numberInputBoxes) + 100);
        inputBox.setType(type);
        inputBox.setDescription(description);
        inputBox.setBundleName(bundleName);
        boxes.add(inputBox);
    }

    public void showDescription(String description, boolean append){
        if(descriptionPanel != null){
            descriptionPanel.putText(description, append);
        }
    }

    private void setHeight(int i){
        this.setSize(this.getSize().width, i);
        this.getParent().validate();
    }

    class RepaintThread extends Thread{
        private boolean end;
        private Component component;
        private int time;

        public void end(){
            end = true;
        }
        public void run(){
            while(!end){
                component.repaint();
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {

                }
            }
        }

        public RepaintThread(final Component component, final int time){
            this.component = component;
            this.time = time;
        }
    }
}
