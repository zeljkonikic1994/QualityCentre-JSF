/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import controller.Controller;
import dto.Folder;
import dto.Step;
import dto.Test;
import dto.TestSet;
import dto.TreeTableNode;
import entities.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbTestSetTreeView")
@SessionScoped
public class MBTestSetTreeView implements Serializable {

    @Inject
    Controller controller;

    private TreeNode root;
    private TreeNode sourceRoot;
    private TreeNode destinationRoot;
    private TreeNode selectedSourceNode;
    private TreeNode selectedDestinationNode;

    private TestSet selectedSet;
    private List<TestSet> allSets;
    private List<Test> sourceList;
    private Step stepEdit;

    @PostConstruct
    public void init() {
        loadSets();
        populateTree();
    }

    /**
     * Creates a new instance of MBTestSetTreeView2
     */
    public MBTestSetTreeView() {
    }

    private void loadSets() {
        this.allSets = controller.getSets();
        if (selectedSet != null) {
            for (TestSet set : allSets) {
                if (set.getTestSetId() == selectedSet.getTestSetId()) {
                    selectedSet = set;
                }
            }
        }
    }

    private void populateTree() {
        root = new DefaultTreeNode("Root", null);
        for (TestSet testSet : allSets) {
            TreeNode node = new DefaultTreeNode(testSet, root);
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        this.selectedSet = (TestSet) event.getTreeNode().getData();
    }

    public void newSet() {
        selectedSet = new TestSet();
        selectedSet.setTestSetId(0);
        selectedSet.setDateCreated(new Date());
        selectedSet.setDateModified(new Date());

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 211);
        options.put("height", 234);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("closable", true);

        PrimeFaces.current().dialog().openDynamic("dialogAddSet", options, null);
    }

    public void saveSet() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        selectedSet.setModifiedBy(user.getUserName());
        controller.saveTestSet(selectedSet);
        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    private void clearFields() {
        selectedSet = null;
        loadSets();
        populateTree();
    }

    public void deleteSet() {
        if (selectedSet != null) {
            deleteSelectedSet();
            clearFields();
        } else {
            showMessage("Error", "You must select a set to delete!");
        }
    }

    public void showMessage(String title, String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, text);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    private void deleteSelectedSet() {
        controller.deleteTestSet(selectedSet);
    }

    public void viewSet() {
        if (selectedSet != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("resizable", true);
            options.put("modal", true);
            options.put("width", 850);
            options.put("height", 550);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("headerElement", "customheader");
            options.put("closable", true);

            prepareDialog();

            PrimeFaces.current().dialog().openDynamic("dialogEditTestSet", options, null);
        } else {
            showMessage("Error!", "Select the test set you wish to edit!");
        }
    }

    private void prepareDialog() {
        loadSets();
        populateTree();
        loadTests();
        populateTrees();
    }

    private void loadTests() {
        this.sourceList = controller.getTests();
    }

    private void populateTrees() {
        populateSourceTree();
        populateDestinationTree();
    }

    private void populateSourceTree() {
        sourceRoot = new DefaultTreeNode("Root", null);
        for (Test t : sourceList) {
            if (t.getStepList().isEmpty()) {
                continue;
            }
            TreeNode node = new DefaultTreeNode(t, sourceRoot);
            for (Step s : t.getStepList()) {
                TreeNode child = new DefaultTreeNode(s, node);
            }
            node.setExpanded(true);
        }
    }

    private void populateDestinationTree() {
        destinationRoot = new DefaultTreeNode("Root", null);
        TreeNode node = new DefaultTreeNode(selectedSet, destinationRoot);
        node.setExpanded(true);
        for (Folder folder : selectedSet.getFolderList()) {
            TreeNode folderNode = new DefaultTreeNode("folder", folder, node);
            folderNode.setExpanded(true);
            for (Step step : folder.getStepList()) {
                if(step.getTestId() == 29){
                    System.out.println("populateDestinationTree "+step);
                }
                TreeNode stepNode = new DefaultTreeNode("step", step, folderNode);
            }
        }
    }

    public void onDragDrop2(TreeDragDropEvent event) {
        populateTrees();
    }

    public TreeNode getSelectedSourceNode() {
        return selectedSourceNode;
    }

    public void setSelectedSourceNode(TreeNode selectedSourceNode) {
        this.selectedSourceNode = selectedSourceNode;
    }

    public TreeNode getSelectedDestinationNode() {
        return selectedDestinationNode;
    }

    public void setSelectedDestinationNode(TreeNode selectedDestinationNode) {
        this.selectedDestinationNode = selectedDestinationNode;
    }

    public TreeNode getDestinationRoot() {
        return destinationRoot;
    }

    public void setDestinationRoot(TreeNode destinationRoot) {
        this.destinationRoot = destinationRoot;
    }

    public TreeNode getSourceRoot() {
        return sourceRoot;
    }

    public void setSourceRoot(TreeNode sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    public void onDragDrop(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        Object dragData = dragNode.getData();
        TreeNode dropNode = event.getDropNode();
        Object dropData = dropNode.getData();
        boolean dropped = false;
        if (dragData instanceof Test) {
            if (dropData instanceof TestSet) {
                dropTestToTestSet(dragData);
                dropped = true;
            } else if (dropData instanceof Folder) {
                dropTestToFolder(dragData, dropData);
                dropped = true;
            } else if (dropData instanceof Step) {
                dropTestToStep(dragData, dropData);
                dropped = true;
            }
        } else if (dragData instanceof Step) {
            if (dropData instanceof TestSet) {
                dropStepToTestSet(dragData);
                dropped = true;
            } else if (dropData instanceof Folder) {
                dropStepToFolder(dragData, dropData);
                dropped = true;
            } else if (dropData instanceof Step) {
                dropStepToStep(dragData, dropData);
                dropped = true;
            }
        } else if (dragData instanceof Folder) {
            populateTrees();
        }
        if (dropped) {
            populateTrees();
        }
    }

    private void dropStepToStep(Object dragData, Object dropData) {
        Step dragStep = (Step) dragData;
        Step dropStep = (Step) dropData;
        for (Folder folder : selectedSet.getFolderList()) {
            if (folder.getStepList().contains(dropStep)) {
                folder.addStep(dragStep);
                removeFromSource(dragStep);
            }
        }
    }

    private void dropStepToFolder(Object dragData, Object dropData) {
        Folder dropFolder = (Folder) dropData;
        Step dragStep = (Step) dragData;
        if (dropFolder.getStepList().contains(dragStep)) {
            return;
        }
        Folder forRemoval = null;
        for (Folder folder : selectedSet.getFolderList()) {
            if (folder.equals(dropFolder)) {
                System.out.println("Found the same folder " + folder);
                folder.addStep(dragStep);
                System.out.println("Added a step " + dragStep);
                if (!removeFromSource(dragStep)) {
                    System.out.println("couldn't remove from source");
                    forRemoval = folder;
                }
            }
        }
        if (forRemoval != null) {
            System.out.println("removing from destination");
            removeFromDestination(dragStep, forRemoval);
        }

    }

    private void dropTestToTestSet(Object dragData) {
        Test dragTest = (Test) dragData;
        Folder folder = new Folder(dragTest.getName());
        folder.setStepList(dragTest.getStepList());
        selectedSet.addFolder(folder);
        removeFromSource(dragTest);
    }

    private void dropTestToFolder(Object dragData, Object dropData) {
        Test dragTest = (Test) dragData;
        Folder dropFolder = (Folder) dropData;
        for (Folder folder : selectedSet.getFolderList()) {
            if (folder.equals(dropFolder)) {
                folder.addSteps(dragTest.getStepList());
                removeFromSource(dragTest);
            }
        }
    }

    private void dropTestToStep(Object dragData, Object dropData) {
        Step dropStep = (Step) dropData;
        Test dragTest = (Test) dragData;

        for (Folder folder : selectedSet.getFolderList()) {
            if (folder.getStepList().contains(dropStep)) {
                folder.addSteps(dragTest.getStepList());
                removeFromSource(dragTest);
            }
        }
    }

    private void dropStepToTestSet(Object dragData) {
        Step step = (Step) dragData;

        int newFolderCount = countNewFolders();
        System.out.println("newFolderCount - " + newFolderCount);
        Folder folder = new Folder();

        if (newFolderCount > 0) {
            folder.setName("New_folder_" + (newFolderCount + 1));
        } else {
            folder.setName("New_folder");
        }
        folder.addStep(step);
        selectedSet.addFolder(folder);
        if (!removeFromSource(step)) {
            removeFromDestination(step, folder);
        }
    }

    private boolean removeFromSource(Step dragStep) {
        for (Test test : sourceList) {
            if (test.getStepList().contains(dragStep)) {
                test.removeStep(dragStep);
                return true;
            }
        }
        return false;
    }

    private void removeFromDestination(Step step, Folder newFolder) {
        Folder forRemoval = null;
        for (Folder folder : selectedSet.getFolderList()) {
            if (folder.equals(newFolder)) {
                continue;
            }
            if (folder.getStepList().contains(step)) {
                folder.removeStep(step);
            }
            if (folder.getStepList().isEmpty()) {
                forRemoval = folder;
            }
        }
        if (forRemoval != null) {
            selectedSet.removeFolder(forRemoval);
        }
    }

    private int countNewFolders() {
        int counter = 0;
        for (Folder folder : selectedSet.getFolderList()) {
            if (folder.getName().contains("New_folder")) {
                counter++;
            }
        }
        return counter;
    }

    private void removeFromSource(Test dragTest) {
        sourceList.remove(dragTest);
    }

    public void onSaveNodeLabel(Object node) {
        
    }

    public void saveTestSet() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        selectedSet.setModifiedBy(user.getUserName());
        selectedSet.setDateModified(new Date());
        for (Folder folder : selectedSet.getFolderList()) {
            for (Step step : folder.getStepList()) {
                if(step.getTestId()==29){
                    System.out.println("PRE CUVANJA: "+step);
                }
            }
        }
        controller.updateTestSet(selectedSet);
        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public void exitWithoutSaving() {
        PrimeFaces.current().dialog().closeDynamic(null);
        clearFields();
    }

    public void exitEditStepWithoutSaving() {
        stepEdit = null;
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public void delete() {
        if (selectedDestinationNode.getData() instanceof Step) {
            Step selectedDestinationStep = (Step) selectedDestinationNode.getData();
            removeFromDestination(selectedDestinationStep);
            addToSource(selectedDestinationStep);
        } else if (selectedDestinationNode.getData() instanceof Folder) {
            Folder selectedDestinationFolder = (Folder) selectedDestinationNode.getData();
            removeFromDestination(selectedDestinationFolder);
            addToSource(selectedDestinationFolder);
        }
        populateTrees();
    }

    public void editStep() {
        this.stepEdit = (Step) selectedDestinationNode.getData();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("closable", true);
        options.put("resizable", true);

        PrimeFaces.current().dialog().openDynamic("dialogEditStep", options, null);
    }

    private void addToSource(Folder selectedDestinationFolder) {
        for (Step step : selectedDestinationFolder.getStepList()) {
            addToSource(step);
        }
    }

    private void removeFromDestination(Folder selectedDestinationFolder) {
        selectedSet.removeFolder(selectedDestinationFolder);

    }

    private void addToSource(Step selectedDestinationStep) {
        if (selectedDestinationStep.getTest() == null) {
            return;
        }
        for (Test t : sourceList) {
            if (t.getTestId() == selectedDestinationStep.getTest().getTestId()) {
                t.addStep(selectedDestinationStep);
                return;
            }
        }
        Test t = new Test(selectedDestinationStep.getTest().getTestId());
        t.setName(selectedDestinationStep.getTest().getName());
        List<Step> stepList = new ArrayList<>();
        stepList.add(selectedDestinationStep);
        t.setStepList(stepList);
        sourceList.add(t);
    }

    private void removeFromDestination(Step selectedDestinationStep) {
        Folder forRemoval = null;
        for (Folder folder : selectedSet.getFolderList()) {
            if (folder.getStepList().contains(selectedDestinationStep)) {
                folder.removeStep(selectedDestinationStep);
            }
            if (folder.getStepList().isEmpty()) {
                forRemoval = folder;
            }
        }
        if (forRemoval != null) {
            selectedSet.removeFolder(forRemoval);
        }
    }

    public TestSet getSelectedSet() {
        return selectedSet;
    }

    public void setSelectedSet(TestSet selectedSet) {
        this.selectedSet = selectedSet;
    }

    public List<TestSet> getAllSets() {
        return allSets;
    }

    public void setAllSets(List<TestSet> allSets) {
        this.allSets = allSets;
    }

    public List<Test> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Test> sourceList) {
        this.sourceList = sourceList;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Step getStepEdit() {
        return stepEdit;
    }

    public void setStepEdit(Step editStep) {
        this.stepEdit = editStep;
    }

    public void updateStep() {
        if (stepEdit != null) {
            
            for (TestSet set : allSets) {
                if (set.getTestSetId() == stepEdit.getTestId()) {
                    for (Folder folder : set.getFolderList()) {
                        for (Step step : folder.getStepList()) {
                            if (step.getStepId() == stepEdit.getStepId()) {
                                step.setName(stepEdit.getName());
                                step.setDescription(stepEdit.getDescription());
                                step.setExpected(stepEdit.getExpected());
                            }
                        }
                    }
                    
                }
            }
        }
//        stepEdit = null;
        populateDestinationTree();
        PrimeFaces.current().dialog().closeDynamic(null);
    }
}
