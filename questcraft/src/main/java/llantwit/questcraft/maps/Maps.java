package llantwit.questcraft.maps;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 1/15/12
 * Time: 2:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class Maps {

    public static final char DIAMOND = '*';
    public static final char ORE = '0';
    public static final char DOOR_LOCKED = '1';
    public static final char DOOR_UNLOCKED = '2';

    public static final char[] MAP1 =
            ("##########" +
                    "#        #" +
                    "#        #" +
                    "#  #######" +
                    "#        #" +
                    "#####    #" +
                    "#        #" +
                    "#        ^" +
                    "#        #" +
                    "####^#####" +
                    "#        #" +
                    "#####^####").toCharArray();
    public static final String KEY = "k";

    public static char[] createMap(int mapWidth, int mapHeight) {
        int length = mapWidth * mapHeight;
        char [] map = new char[length];
        Random random = new Random(System.currentTimeMillis());

        for(int i = 0; i < length; i++){
            int ran = random.nextInt(4);
            char c;

            switch (ran){
                case 1:
                    c = ' ';
                    break;
                default:
                    c = '#';
            }
            map[i] = c;
        }

        for(int i = 0; i < mapWidth; i++){

            map[i] = getRandomChar(new char[]{'#', '1', '2'}, new int[]{20, 3, 1});
            map[(mapWidth * (mapHeight -1)) + i] = getWallChar();
        }
        for(int j = 0; j < (mapHeight - 1); j++){
            map[(mapWidth * j) + mapWidth -1] = getWallChar();
            map[mapWidth * j] = getWallChar();
        }
        map[mapWidth + 1 ] = ' ';
        System.out.println(new String(map));
        return map;
    }

    public static char getWallChar(){
        Random random = new Random(System.nanoTime());
        int r = random.nextInt(15);
        System.out.println(r);
        if(r == 1){
            return DOOR_LOCKED;
        } else {
            return '#';
        }
    }

    public static char getRandomChar(char[] chars, int[] probs){
        if(chars.length != probs.length){
            return ' ';
        } else{
            int total = 0;
            for (int prob : probs) {
                total = total + prob;
            }

            ArrayList<Character> characters = new ArrayList<Character>();
            for (int i = 0; i < probs.length; i++) {
                int prob = probs[i];
                for(int j = 0 ; j < prob; j++){
                    characters.add(chars[i]);
                }
            }
            Random random = new Random(System.nanoTime());
            int rand = random.nextInt(total);
            return characters.get(rand);
        }
    }

    public static Image getImage(String url){
        URL loc = Maps.class.getClassLoader().getResource(url);
        ImageIcon iia = new ImageIcon(loc);
        return iia.getImage();
    }

}
