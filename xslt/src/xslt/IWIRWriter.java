import org.shiwa.fgi.iwir.*;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 3/9/11
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class IWIRWriter {

    public IWIRWriter(){
        IWIR iwir = new IWIR();
        iwir.setWfname("test");

        Task task1 = new Task("main", "consumer");
        InputPort t1i1 = new InputPort("t1in1", "file");
        InputPort t1i2 = new InputPort("t1in2", "file");
        task1.addInputPort(t1i1);
        task1.addInputPort(t1i2);
        OutputPort t1o1 = new OutputPort("t1out0", "file");
        task1.addOutputPort(t1o1);

        Task task2 = new Task("main", "consumer");
        InputPort t2i1 = new InputPort("t2in1", "file");
        InputPort t2i2 = new InputPort("t2in2", "file");
        task2.addInputPort(t2i1);
        task2.addInputPort(t2i2);
        OutputPort t2o1 = new OutputPort("t2out0", "file");
        task2.addOutputPort(t2o1);



        iwir.setTask(task1);

        System.out.println(XMLHandler.writeString(iwir));

    //    org.shiwa.fgi.iwir.examples.CrossProduct.main(new String[0]);

    }

    public static void main(String[] args) {
        new IWIRWriter();
    }
}