package llantwit.jaxbtest;

import edu.isi.pegasus.schema.dax.Adag;
import edu.isi.pegasus.schema.dax.FilenameType;
import org.ggf.schemas.jsdl._2005._11.jsdl.*;

import javax.xml.bind.*;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 09/02/2012
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public class JaxbTest{

    public JaxbTest() throws JAXBException {
        Object jaxbObject = importXML(new File("/Users/ian/stuff/Inspiral_30.xml"),
                "edu.isi.pegasus.schema.dax");
        if(jaxbObject instanceof Adag){
            readDaxInfo((Adag) jaxbObject);
        }

//        JAXBElement jaxbElement = importJSDL(new File("/Users/ian/stuff/largeJsdl.jsdl"),
//                "org.ggf.schemas.jsdl._2005._11.jsdl");
//
//        readJSDLInfo(jaxbElement);

//        testMarshall();
    }

    private void readDaxInfo(Adag adag ) {
        System.out.println(adag.getName());

        for(Adag.Job job : adag.getJob()){
            System.out.println(job.getName());
            System.out.println(job.getArgument());
            for(FilenameType filenameType : job.getUses()){
                System.out.println(filenameType.getFile());
            }
        }

    }

    private Object importXML(File file, String packageName) throws JAXBException {
        JAXBContext xmlContext = JAXBContext.newInstance(packageName);

        Unmarshaller unmarshaller = xmlContext.createUnmarshaller();

        return unmarshaller.unmarshal(file);

    }

    public void readJSDLInfo(JAXBElement jsdl){
        JobDefinitionType jobDefinitionType = (JobDefinitionType) jsdl.getValue();

        System.out.println("****" + jobDefinitionType.getJobDescription().getJobIdentification().getJobName() + "\n");
        JobDescriptionType jobDescription = jobDefinitionType.getJobDescription();
        for (DataStagingType next : jobDescription.getDataStaging()) {
            System.out.println(next.getSource().getURI());
            System.out.println(next.getTarget().getURI() + "\n");
        }
    }


    public static void main(String[] args) throws JAXBException {
        new JaxbTest();
    }

    public void testMarshall() throws JAXBException {
//        JAXBContext posixContext = JAXBContext.newInstance("org.ggf.schemas.jsdl._2005._11.jsdl_posix");
//        org.ggf.schemas.jsdl._2005._11.jsdl_posix.ObjectFactory posixFactory = new org.ggf.schemas.jsdl._2005._11.jsdl_posix.ObjectFactory();
//
//        POSIXApplicationType posixApplicationType = posixFactory.createPOSIXApplicationType();
//        posixApplicationType.setName("a job");
//        FileNameType fileNameType = new FileNameType();
//        fileNameType.setValue("/usr/bin/ls");
//        fileNameType.setFilesystemName("ls");
//        posixApplicationType.setExecutable(fileNameType);


        JAXBContext jsdlContext = JAXBContext.newInstance("org.ggf.schemas.jsdl._2005._11.jsdl");
        ObjectFactory jsdlFactory = new ObjectFactory();

        JobDefinitionType jobDefinitionType = jsdlFactory.createJobDefinitionType();
        jobDefinitionType.setJobDescription(new JobDescriptionType());

        JobDescriptionType jobDescription = jobDefinitionType.getJobDescription();

        JobIdentificationType jobIdentificationType = jsdlFactory.createJobIdentificationType();
        jobIdentificationType.setJobName("a job");
        jobIdentificationType.setDescription("an application that does nothing, as it doesnt exist.");

        jobDescription.setJobIdentification(jobIdentificationType);

        ApplicationType applicationType = jsdlFactory.createApplicationType();
        applicationType.setApplicationName("ls");
//        applicationType.getAny().add(posixApplicationType);

        jobDescription.setApplication(applicationType);


//        PurchaseOrderType type = factory.createPurchaseOrderType();
//        type.setComment("comment");
//        Items items = factory.createItems();
//        Items.Item o = factory.createItemsItem();
//        o.setPartNum("test");
//        o.setComment("part comment");
//        o.setProductName("productname");
//        o.setQuantity(2);
//
//        items.getItem().add(o);
//        type.setItems(items);
//
//        JAXBElement<purchaseordertype> element = factory.createPurchaseOrder(type);
        JAXBElement<JobDefinitionType> element = jsdlFactory.createJobDefinition(jobDefinitionType);
        Marshaller m = jsdlContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(element, System.out);
    }
}
