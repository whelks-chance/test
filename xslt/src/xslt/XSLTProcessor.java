package xslt;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileOutputStream;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 08/03/2011
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class XSLTProcessor {
    public static boolean process(String inputFilePath, String outputFilePath, String transformerFile) {
        try{
            String home = System.getProperty("user.home") + File.separator;

            System.out.println(home + inputFilePath + " " +
                home + outputFilePath + " " +
                home + transformerFile
            );

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(home + transformerFile));

            transformer.transform(new StreamSource(home + inputFilePath),
                    new StreamResult(new FileOutputStream(home + outputFilePath)));

            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        boolean success = XSLTProcessor.process("iwir.xml", "output.xml", "iwir.xsl");
        System.out.println("Completed : " + success);
    }
}
