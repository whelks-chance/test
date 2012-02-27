package llantwit.jaxbtest;

import org.shiwa.desktop.data.description.SHIWABundle;
import org.shiwa.desktop.data.description.bundle.BundleFile;
import org.shiwa.desktop.data.description.core.Configuration;
import org.shiwa.desktop.data.description.core.WorkflowImplementation;
import org.shiwa.desktop.data.description.resource.AggregatedResource;
import org.shiwa.desktop.data.transfer.WorkflowController;
import org.shiwa.desktop.data.util.exception.SHIWADesktopIOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 14/02/2012
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class RunJSDLBundle {

    public RunJSDLBundle(String path) throws SHIWADesktopIOException, JAXBException {
        File zipfile = new File(path);
        SHIWABundle bundle = new SHIWABundle(zipfile);
        WorkflowController workflowController = new WorkflowController(bundle);

        for(Configuration config : workflowController.getConfigurations()){
            Set<BundleFile> files = config.getBundleFiles();
            for (BundleFile next : files) {

            }
        }

        WorkflowImplementation imp = workflowController.getWorkflowImplementation();

        for(AggregatedResource resource : imp.getAggregatedResources()){
            if(resource instanceof Configuration){
                String configType = ((Configuration) resource).getType().getString();
                Set<BundleFile> files = resource.getBundleFiles();

                for(BundleFile file : files){
                }
            }

        }

        byte[] defBytes = imp.getDefinition().getBytes();
        readJSDL(defBytes);

    }

    public void readJSDL(byte[] defBytes) throws JAXBException {
        InputStream inputStream = new ByteArrayInputStream(defBytes);

        JAXBContext jsdlContext = JAXBContext.newInstance("org.ggf.schemas.jsdl._2005._11.jsdl");

        Unmarshaller unmarshaller = jsdlContext.createUnmarshaller();

        JAXBElement jsdl = (JAXBElement) unmarshaller.unmarshal(inputStream);

    }

    public static void main(String[] args) throws SHIWADesktopIOException, JAXBException {
        new RunJSDLBundle("/Users/ian/stuff/jsdlBundle.zip");
    }
}
