package triana.bundle;


import org.shiwa.desktop.data.description.SHIWABundle;
import org.shiwa.desktop.data.util.exception.SHIWADesktopIOException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 27/02/2012
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public class Executer {

    public Executer(){

        File bundle = new File("/Users/ian/stuff/concatBundle.bundle.zip");

        SHIWABundle shiwaBundle = null;
        try {
            shiwaBundle = new SHIWABundle(bundle);
        } catch (SHIWADesktopIOException e) {
            e.printStackTrace();
        }

        String addonDir = "";

        File addonFolder = new File(addonDir);
        System.out.println("Addon folder : " + addonFolder.getAbsolutePath());
        try {
            ClassRegister classRegister = new ClassRegister(new URL[]{addonFolder.toURI().toURL()}, Executer.class.getClassLoader());

            Class clazz = (Class) classRegister.getWorkflowExecutor(Executor.class.getCanonicalName());

            Executor executor = (Executor) clazz.newInstance();

            executor.execute(shiwaBundle);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new Executer();
    }
}
