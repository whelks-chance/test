package llantwit.cables.bundle;

import llantwit.cables.ConnectionSurface;
import llantwit.utils.MenuAction;
import org.shiwa.desktop.data.description.SHIWABundle;
import org.shiwa.desktop.data.description.core.AbstractWorkflow;
import org.shiwa.desktop.data.description.core.Configuration;
import org.shiwa.desktop.data.description.core.WorkflowImplementation;
import org.shiwa.desktop.data.description.handler.TransferPort;
import org.shiwa.desktop.data.description.handler.TransferSignature;
import org.shiwa.desktop.data.description.resource.AggregatedResource;
import org.shiwa.desktop.data.description.resource.ConfigurationResource;
import org.shiwa.desktop.data.description.resource.ReferableResource;
import org.shiwa.desktop.data.description.workflow.InputPort;
import org.shiwa.desktop.data.description.workflow.OutputPort;
import org.shiwa.desktop.data.util.exception.SHIWADesktopIOException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;


/**
 * Created by IntelliJ IDEA.
 * User: ian
 * Date: 11/18/11
 * Time: 3:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadBundleAction extends MenuAction {

    private ConnectionSurface connectionSurface;
    private PortType portType;
    private ArrayList<WorkflowImplementation> workflowImplementations;
    private ArrayList<Configuration> configurationList;
    private boolean bundleInit;

    public enum PortType{
        input,
        output
    }

    public LoadBundleAction(ConnectionSurface connectionSurface, PortType portType){
        this.connectionSurface = connectionSurface;
        this.portType = portType;
    }

    @Override
    public void doAction(ActionEvent actionEvent) {
        try{
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = chooser.showDialog(connectionSurface, "File");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                if (f != null && f.exists()) {
                    System.out.println(f.getAbsolutePath());
                    readBundleFile(f);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readBundleFile(File f) throws SHIWADesktopIOException {
        SHIWABundle shiwaBundle = new SHIWABundle(f);
        initBundle(shiwaBundle);
        WorkflowImplementation o = chooseImp();
        System.out.println("Name = " + o.getDefinition().getFilename());
        TransferSignature signature = null;
        if(configurationList == null || configurationList.size() < 1){
            signature = buildSignature(o, null);
        } else {
            signature = buildSignature(o, configurationList.get(0));
        }
        System.out.println("Number outputs " + signature.getOutputs().size());
        System.out.println("Number inputs " + signature.getInputs().size());

        if(portType == LoadBundleAction.PortType.output){
            for(TransferPort outputPort : signature.getOutputs()){
                connectionSurface.addInputBox(outputPort.getName(), signature.getName(),  outputPort.getType(), outputPort.getDescription());
            }
        }

        if(portType == LoadBundleAction.PortType.input){
            for(TransferPort port : signature.getInputs()){
                connectionSurface.addOutputBox(port.getName(), signature.getName(), port.getType(), port.getDescription());
            }
        }

    }

    private void initBundle(SHIWABundle bundle) throws SHIWADesktopIOException {
//        bundle.init();

        workflowImplementations = new ArrayList<WorkflowImplementation>();

        configurationList = new ArrayList<org.shiwa.desktop.data.description.core.Configuration>();

        AggregatedResource aggregatedResource = bundle.getAggregatedResource();

        if (aggregatedResource instanceof AbstractWorkflow) {
            for (AggregatedResource resource : aggregatedResource.getAggregatedResources()) {
                if (resource instanceof WorkflowImplementation) {
                    workflowImplementations.add((WorkflowImplementation) resource);
                } else if (resource instanceof org.shiwa.desktop.data.description.core.Configuration) {
                    org.shiwa.desktop.data.description.core.Configuration configuration = (org.shiwa.desktop.data.description.core.Configuration) resource;
                    if (configuration.getType() == org.shiwa.desktop.data.description.core.Configuration.ConfigType.DATA_CONFIGURATION) {
                        configurationList.add(configuration);
                    }
                }
            }

        } else if (aggregatedResource instanceof WorkflowImplementation) {
            workflowImplementations.add((WorkflowImplementation) aggregatedResource);

            for (AggregatedResource resource : aggregatedResource.getAggregatedResources()) {
                if (resource instanceof AbstractWorkflow) {

                } else if (resource instanceof org.shiwa.desktop.data.description.core.Configuration) {
                    org.shiwa.desktop.data.description.core.Configuration configuration = (org.shiwa.desktop.data.description.core.Configuration) resource;
                    if (configuration.getType() == org.shiwa.desktop.data.description.core.Configuration.ConfigType.DATA_CONFIGURATION) {
                        configurationList.add(configuration);
                    }
                }
            }
        }
    }

    private WorkflowImplementation chooseImp() {
        WorkflowImplementation chosenImp = null;
        for (WorkflowImplementation implementation : workflowImplementations) {
            System.out.println(implementation.getEngine());
            if (implementation.getEngine().equalsIgnoreCase("Triana")) {
                chosenImp = implementation;
            }
        }

        if (chosenImp == null) {
            for (WorkflowImplementation implementation : workflowImplementations) {
                if (implementation.getLanguage().getShortId().equalsIgnoreCase("IWIR")) {
                    chosenImp = implementation;
                }
            }
        }
        return chosenImp;
    }

    private TransferSignature buildSignature(WorkflowImplementation workflow, Configuration configuration) {
        TransferSignature signature = new TransferSignature();

        signature.setName(workflow.getDefinition().getFilename());
        System.out.println("Signatures name " + signature.getName());

        for (ReferableResource referableResource : workflow.getSignature().getPorts()) {
            if (referableResource instanceof InputPort) {
                String value = null;
                boolean isReference = false;

                if (configuration != null) {
                    for (ConfigurationResource portRef : configuration.getResources()) {
                        if (portRef.getReferableResource().getId() == referableResource.getId()) {
                            value = portRef.getValue();
                            isReference = (portRef.getRefType() == ConfigurationResource.RefTypes.FILE_REF);
                        }
                    }
                }

                if (value != null) {
                    if (isReference) {
                        signature.addInputReference(referableResource.getTitle(), referableResource.getDataType(), value);
                    } else {
                        signature.addInput(referableResource.getTitle(), referableResource.getDataType(), value);
                    }
                } else {
                    signature.addInput(referableResource.getTitle(), referableResource.getDataType());
                }
            } else if (referableResource instanceof OutputPort) {
                signature.addOutput(referableResource.getTitle(), referableResource.getDataType());
            }
        }

//        for (Dependency dependency : workflow.getDependencies()) {
//            Constraint constraint = new Constraint(dependency.getTitle(), dependency.getDataType(), dependency.getDescription());
//
//            if (configuration != null) {
//                for (ConfigurationResource dependencyRef : configuration.getResources()) {
//                    if (dependencyRef.getReferableResource() == dependency) {
//                        if (dependencyRef.getRefType() == ConfigurationResource.RefTypes.FILE_REF) {
//                            constraint.setValueType(TransferSignature.ValueType.BUNDLED_FILE);
////                            constraint.setValueReference(dependencyRef.getValue());
//                        } else {
//                            constraint.setValue(dependencyRef.getValue());
//                        }
//                    }
//                }
//            }
//
//            signature.addConstraint(constraint);
//        }

        if (configuration != null) {
            signature.setHasConfiguration(true);
        }
        return signature;
    }
}
