package llantwit.utils;

import llantwit.utils.actions.QuitAction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class LFrame extends JFrame{

    private JMenuBar jMenuBar;
    private HashMap<String, JMenu> menus = new HashMap<String, JMenu>();

    public LFrame(){
        decorateFrame(this);
        this.setLayout(new BorderLayout());


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
    }

    private void decorateFrame(JFrame frame) {
        jMenuBar = new JMenuBar();
        addMenuItem("File", "Quit", new QuitAction());
        frame.setJMenuBar(jMenuBar);
    }

    public void addMenuItem(String menuName, String menuItemName, MenuAction menuAction){
        JMenu menu = getMenu(menuName);
        JMenuItem menuItem = new JMenuItem(menuItemName);
        menuItem.addActionListener(menuAction);
        menu.add(menuItem);
        jMenuBar.add(menu);
    }

    private JMenu getMenu(String menuName){
        JMenu menu = null;
        if(menus.get(menuName) != null){
            System.out.println("found a menu " + menuName);
            menu = menus.get(menuName);
        } else {
            System.out.println("creating a menu " + menuName);
            menu = new JMenu(menuName);
            menus.put(menuName, menu);
        }
        return menu;
    }
}
