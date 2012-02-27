package llantwit.threed;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import llantwit.utils.LFrame;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 26/12/2011
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class ThreedTest {

    private ViewingPlatform ourView;
    private OrbitBehavior B;
    private BoundingSphere bounds;
    private Transform3D locator;

    public ThreedTest(){
        LFrame lFrame = new LFrame();

        bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        GraphicsConfiguration graphicsConfiguration = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(graphicsConfiguration);

        lFrame.add(canvas3D, BorderLayout.CENTER);

        SimpleUniverse simpleUniverse = new SimpleUniverse(canvas3D);

        ourView = simpleUniverse.getViewingPlatform();

        locator = new Transform3D();
        locator.setTranslation(new Vector3f(0, 3f, -3f));
        locator.lookAt(new Point3d(0d, 9d, -1d), new Point3d(0d, 0d, 1d), new Vector3d(0d, 1d, 0d));
        locator.invert();

        ourView.getViewPlatformTransform().setTransform(locator);

        BranchGroup scene = createSceneGraph();
        simpleUniverse.addBranchGraph(scene);

        B = new OrbitBehavior(canvas3D);
        B.setSchedulingBounds(bounds);
        ourView.setViewPlatformBehavior(B);

        lFrame.setVisible(true);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        int landWidth = 3;
        int landLength = 3;
        int landDepth = 3;

//        for(int i = -(landWidth / 2) ; i < (landWidth / 2) ; i++){
//            for(int j = -(landDepth / 2) ; j < (landDepth / 2); j++){

        for (int i = 0; i < landWidth; i++){
            for (int j = 0; j < landLength; j ++){
                for(int k = 0; k < landDepth; k++){
                    Land os2 = new Land(1, 1, 1, i, j, k);

                    Appearance app = new Appearance();
                    app.setColoringAttributes(new ColoringAttributes(getColor3f(), 50));
//                Color3f objColour = new Color3f(0.3f, 0.2f, 0.8f);
//                    Color3f objColour = new Color3f(Color.yellow);
//                    Color3f objColour = getColor3f();
//                    Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
//                app.setMaterial(new Material(objColour, black, objColour, black, 80.0f));
                    os2.setAppearance(app);

                    objRoot.addChild(os2);
                }
                flipStartColor();
                flip = flipStart;
            }
//            flipStartColor();
            flip = flipStart;
        }


//        Color3f alColour = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f alColour = new Color3f(Color.white);
        AmbientLight aLgt = new AmbientLight(alColour);
        aLgt.setInfluencingBounds(bounds);

        objRoot.addChild(aLgt);


        Move move = new Move();
        move.setSchedulingBounds(bounds);
        objRoot.addChild(move);
//        objRoot.addChild(getPlayer());

        objRoot.compile();
        return objRoot;

    }

//    private Shape3D getPlayer() {
//
//        Shape3D shape3D = new Shape3D();
//
////        GeometryArray geometryArray[] = new GeometryArray[]{null, null, null};
////
////        Morph morph = new Morph(geometryArray);
////        morph.setCapability(Morph.ALLOW_WEIGHTS_READ);
////        morph.setCapability(Morph.ALLOW_WEIGHTS_WRITE);
////        morph.setCapability(Morph.ALLOW_GEOMETRY_ARRAY_READ);
////
////        Appearance app = new Appearance();
////        app.setColoringAttributes(new ColoringAttributes(new Color3f(Color.GREEN), 50));
////        morph.setAppearance(app);
////
////        Alpha alpha = new Alpha();
//
//        Move move = new Move();
//        move.setSchedulingBounds(bounds);
//
//
//
//
//        return shape3D;
//    }

    public static void main(String[] args) {
        new ThreedTest();
    }

    boolean flipStart = true;
    boolean flip = true;
    private Color3f getColor3f(){
        Color3f color3f;
        if(flip){
            color3f = new Color3f(Color.yellow);
//            System.out.println("yellow");
            flipColor();
        } else {
            color3f = new Color3f(Color.cyan);
//            System.out.println("cyan");
            flipColor();
        }
//        color3f = new Color3f(Color.yellow);

        return color3f;
    }

    private void flipColor() {
        flip = !flip;
    }

    private void flipStartColor(){
        flipStart = !flipStart;
    }

}
