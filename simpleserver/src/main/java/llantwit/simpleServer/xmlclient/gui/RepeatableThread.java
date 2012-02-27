package llantwit.simpleServer.xmlclient.gui;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 2/3/12
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class RepeatableThread extends Thread{
    private boolean end;
    private Repeatable repeatable;
    private int time;

    public void end(){
        end = true;
    }
    public void run(){
        while(!end){

            repeatable.repeat();

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {

            }
        }
    }

    public RepeatableThread(final Repeatable repeatable, final int time){
        this.repeatable = repeatable;
        this.time = time;
    }

}
