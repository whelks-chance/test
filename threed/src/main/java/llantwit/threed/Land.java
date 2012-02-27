package llantwit.threed;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

import javax.media.j3d.Shape3D;
import javax.vecmath.Point3f;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 26/12/2011
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class Land extends Shape3D{

    private static Point3f A;
    private static Point3f B;
    private static Point3f C;
    private static Point3f D;
    private static Point3f E;
    private static Point3f F;
    private static Point3f G;
    private static Point3f H;

    public Land(int width, int length, int height,  int x, int y, int z){

        A = new Point3f(0.0f + x, 0.0f + y, 0.0f + z);
        B = new Point3f(0.0f + x, -length + y, 0.0f + z);
        C = new Point3f(width + x, -length + y, 0.0f + z);
        D = new Point3f(width + x, 0.0f + y, 0.0f + z);

        E = new Point3f(0.0f + x, 0.0f + y, 0.0f + z - 1);
        F = new Point3f(0.0f + x, -length + y, 0.0f + z - 1);
        G = new Point3f(width + x, -length + y, 0.0f + z - 1);
        H = new Point3f(width + x, 0.0f + y, 0.0f + z - 1);


//        Point3f[] pts = new Point3f[8];
        Point3f[] pts = new Point3f[24];
// front
        pts[0]=A;
        pts[1]=B;
        pts[2]=C;
        pts[3]=D;

////back
//        pts[4]=C;
//        pts[5]=B;
//        pts[6]=A;
//        pts[7]=D;

        pts[4] = H;
        pts[5] = G;
        pts[6] = F;
        pts[7] = E;

//        side 1
        pts[8] = B;
        pts[9] = A;
        pts[10] = E;
        pts[11] = F;

//        side 2
        pts[12] = C;
        pts[13] = B;
        pts[14] = F;
        pts[15] = G;

//        side 3
        pts[16] = D;
        pts[17] = C;
        pts[18] = G;
        pts[19] = H;

//        side 4
        pts[20] = A;
        pts[21] = D;
        pts[22] = H;
        pts[23] = E;




        int[] stripCounts= new int[6];
        stripCounts[0]=4;
        stripCounts[1]=4;
        stripCounts[2]=4;
        stripCounts[3]=4;
        stripCounts[4]=4;
        stripCounts[5]=4;

        int[] contourCount=new int[6];
        contourCount[0]=1;
        contourCount[1]=1;
        contourCount[2]=1;
        contourCount[3]=1;
        contourCount[4]=1;
        contourCount[5]=1;

        GeometryInfo gInf = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

        gInf.setCoordinates(pts);
        gInf.setStripCounts(stripCounts);
        gInf.setContourCounts(contourCount);

        NormalGenerator ng= new NormalGenerator();
        ng.setCreaseAngle ((float) Math.toRadians(30));
        ng.generateNormals(gInf);

        this.setGeometry(gInf.getGeometryArray());
    }
}
