package llantwit.utils.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/4/12
 * Time: 12:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtils {

    public Image getImageFromPath(String url){
         try{
            URL loc = this.getClass().getClassLoader().getResource(url);
            ImageIcon iia = new ImageIcon(loc);
            return iia.getImage();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
