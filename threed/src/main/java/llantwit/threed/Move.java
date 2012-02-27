package llantwit.threed;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 28/12/2011
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class Move extends Behavior{
    private WakeupCondition wakeuper = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);

    private Transform3D camTrans;
    private Vector3d camVec;
    private TransformGroup camTG;
    private Point3d player;

    private TransformGroup ourTG;

    private double x;
    private double y;
    private double z;

    public Move(Alpha alpha, Morph morph){
//        player = new Point3d(x, y, z);

    }

    public Move( ) {
//        this.ourTG = ourTG;
    }

    @Override
    public void initialize() {
        wakeupOn( wakeuper );
        initCamera();
    }

    @Override
    public void processStimulus(Enumeration stimulus) {

        while (stimulus.hasMoreElements()) {
            Object o = stimulus.nextElement();
            if(o instanceof WakeupOnAWTEvent){
                processAWTEvent(((WakeupOnAWTEvent) o).getAWTEvent());
            }
        }
    }

    private void processAWTEvent(AWTEvent[] awtEvent) {
        for (int i = 0; i < awtEvent.length; i++) {
            AWTEvent event = awtEvent[i];

            if(event instanceof KeyEvent){
                Transform3D ourTrans = new Transform3D();
                ourTG.getTransform(ourTrans);

                Transform3D tempTrans = new Transform3D();

                System.out.println("key pressed");
                int keyCode = ((KeyEvent) event).getKeyCode();

                switch( keyCode ) {
                    case KeyEvent.VK_UP:
                        // handle up
//                        x = x + 0.1;
                        tempTrans.setTranslation(new Vector3d(0.0, 1.0, 1));
                        tempTrans.mul(ourTrans);

                        break;
                    case KeyEvent.VK_DOWN:
                        // handle down

                        break;
                    case KeyEvent.VK_LEFT:
                        // handle left

                        break;
                    case KeyEvent.VK_RIGHT :
                        // handle right

                        break;
                }

//                player = new Point3d(x,y,z);
                ourTrans.mul(tempTrans);
                ourTG.setTransform(ourTrans);
                translateCamera(ourTrans);
            }
        }
    }

    private void initCamera()
    {
        camTrans = new Transform3D();
        camVec = new Vector3d();
        camTG.getTransform(camTrans);
        camTrans.get(camVec);
    }

    private void translateCamera(Transform3D ourTrans)
    {
        Vector3d ourVec=new Vector3d();
        Vector3d up = new Vector3d(0, 1, 0);
        ourTrans.get(ourVec);
        ourVec.add(camVec);

        Point3d cam = new Point3d();

        camTrans.setTranslation(ourVec);
        ourTrans.transform(player);
        camTrans.transform(cam);

        camTrans.lookAt(cam, player, up);
        camTrans.invert();

        camTG.setTransform(camTrans);
    }
}
