package triana.bundle;

import org.shiwa.desktop.data.description.SHIWABundle;
import org.trianacode.shiwa.bundle.TrianaBundle;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 27/02/2012
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public class ExecuteBundle implements Executor{

    public Object execute(Object object) {

        SHIWABundle bundle = null;
        if(object instanceof SHIWABundle){
            bundle = (SHIWABundle) object;
        }
        if(bundle != null){
            TrianaBundle trianaBundle = new TrianaBundle();
            trianaBundle.executeBundle(bundle, new String[]{});
            return bundle;
        }
        return null;
    }
}
