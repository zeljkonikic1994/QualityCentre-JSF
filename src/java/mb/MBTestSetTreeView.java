/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import controller.Controller;
import dto.FolderDTO;
import dto.StepDTO;
import dto.TestDTO;
import entities.Test;
import entities.TestSet;
import dto.TestSetDTO;
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
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import util.EntityHelper;

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

    private TestSetDTO selectedSet;
    private List<TestSetDTO> allSets;
    private List<TestDTO> sourceList;

    private List<StepDTO> stepsForRemoval;

    @PostConstruct
    public void init() {
        loadSets();
        stepsForRemoval = new ArrayList<>();
        populateTree();
    }

    /**
     * Creates a new instance of MBTestSetTreeView2
     */
    public MBTestSetTreeView() {
    }

    private void loadSets() {
        
        List<TestSet> testSets = controller.loadSets();
        
        this.allSets = EntityHelper.convertFromTestSetList(testSets);
        
        if (selectedSet != null) {
            for (TestSetDTO set : allSets) {
                if(set.getTestSetId() == selectedSet.getTestSetId()){
                    selectedSet = set;
                }
            }
        }
    }

    private void populateTree() {
        root = new DefaultTreeNode("Root", null);
        for (TestSetDTO testSet : allSets) {
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
        this.selectedSet = (TestSetDTO) event.getTreeNode().getData();
    }

    public void newSet() {
        selectedSet = new TestSetDTO();
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
        controller.saveSet(selectedSet);
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
        controller.deleteSet(selectedSet);
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
        stepsForRemoval = new ArrayList<>();
        populateTree();
        loadTests();
        populateTrees();
    }

    private void loadTests() {
        List<Test> tests = controller.loadTests();
        this.sourceList = EntityHelper.convertFromTestList(tests);
    }

    private void populateTrees() {
        populateSourceTree();
        populateDestinationTree();
    }

    private void populateSourceTree() {
        sourceRoot = new DefaultTreeNode("Root", null);
        for (TestDTO t : sourceList) {
            if (t.getStepList().isEmpty()) {
                continue;
            }
            TreeNode node = new DefaultTreeNode(t, sourceRoot);
            for (StepDTO s : t.getStepList()) {
                TreeNode child = new DefaultTreeNode(s, node);
            }
            node.setExpanded(true);
        }
    }

    private void populateDestinationTree() {
        destinationRoot = new DefaultTreeNode("Root", null);
        TreeNode node = new DefaultTreeNode(selectedSet, destinationRoot);
        node.setExpanded(true);
        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
            TreeNode folderNode = new DefaultTreeNode("folder", folder, node);
            folderNode.setExpanded(true);
            for (StepDTO step : folder.getStepList()) {
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
        if (dragData instanceof TestDTO) {
            if (dropData instanceof TestSetDTO) {
                dropTestToTestSet(dragData);
                dropped = true;
            } else if (dropData instanceof FolderDTO) {
                dropTestToFolder(dragData, dropData);
                dropped = true;
            } else if (dropData instanceof StepDTO) {
                dropTestToStep(dragData, dropData);
                dropped = true;
            }
        } else if (dragData instanceof StepDTO) {
            if (dropData instanceof TestSetDTO) {
                dropStepToTestSet(dragData);
                dropped = true;
            } else if (dropData instanceof FolderDTO) {
                dropStepToFolder(dragData, dropData);
                dropped = true;
            } else if (dropData instanceof StepDTO) {
                dropStepToStep(dragData, dropData);
                dropped = true;
            }
        } else if (dragData instanceof FolderDTO) {
            populateTrees();
        }
        if (dropped) {
            populateTrees();
        }
    }

    private void dropStepToStep(Object dragData, Object dropData) {
        StepDTO dragStep = (StepDTO) dragData;
        StepDTO dropStep = (StepDTO) dropData;
        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
            if (folder.getStepList().contains(dropStep)) {
                folder.addStep(dragStep);
                removeFromSource(dragStep);
            }
        }
    }

    private void dropStepToFolder(Object dragData, Object dropData) {
        FolderDTO dropFolder = (FolderDTO) dropData;
        StepDTO dragStep = (StepDTO) dragData;
        if (dropFolder.getStepList().contains(dragStep)) {
            return;
        }
        FolderDTO forRemoval = null;
        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
            if (folder.equals(dropFolder)) {
                System.out.println("Found the same folder "+folder);
                folder.addStep(dragStep);
                System.out.println("Added a step "+dragStep);
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
        TestDTO dragTest = (TestDTO) dragData;
        FolderDTO folder = new FolderDTO(dragTest.getName());
        folder.setStepList(dragTest.getStepList());
        selectedSet.addFolderDto(folder);
        removeFromSource(dragTest);
    }

    private void dropTestToFolder(Object dragData, Object dropData) {
        TestDTO dragTest = (TestDTO) dragData;
        FolderDTO dropFolder = (FolderDTO) dropData;
        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
            if (folder.equals(dropFolder)) {
                folder.addSteps(dragTest.getStepList());
                removeFromSource(dragTest);
            }
        }
    }

    private void dropTestToStep(Object dragData, Object dropData) {
        StepDTO dropStep = (StepDTO) dropData;
        TestDTO dragTest = (TestDTO) dragData;

        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
            if (folder.getStepList().contains(dropStep)) {
                folder.addSteps(dragTest.getStepList());
                removeFromSource(dragTest);
            }
        }
    }

    private void dropStepToTestSet(Object dragData) {
        StepDTO step = (StepDTO) dragData;

        int newFolderCount = countNewFolders();
        System.out.println("newFolderCount - " + newFolderCount);
        FolderDTO folder = new FolderDTO();

        if (newFolderCount > 0) {
            folder.setName("New_folder_" + (newFolderCount + 1));
        } else {
            folder.setName("New_folder");
        }
        folder.addStep(step);
        selectedSet.addFolderDto(folder);
        if (!removeFromSource(step)) {
            removeFromDestination(step, folder);
        }
    }

    private boolean removeFromSource(StepDTO dragStep) {
        for (TestDTO test : sourceList) {
            if (test.getStepList().contains(dragStep)) {
                test.removeStep(dragStep);
                return true;
            }
        }
        return false;
    }

    private void removeFromDestination(StepDTO step, FolderDTO newFolder) {
        FolderDTO forRemoval = null;
        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
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
        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
            if (folder.getName().contains("New_folder")) {
                counter++;
            }
        }
        return counter;
    }

    private void removeFromSource(TestDTO dragTest) {
        sourceList.remove(dragTest);
    }

    public void onSaveNodeLabel(Object node) {
        System.out.println(node);
    }

    public void saveTestSet() {
//        selectedSet.setDateModified(new Date());
        deleteStepsForRemoval();
        controller.updateSet(selectedSet);
        
        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    private void deleteStepsForRemoval() {
        controller.deleteSpecificStepList(stepsForRemoval);
    }

    public void exitWithoutSaving() {
        PrimeFaces.current().dialog().closeDynamic(null);
        clearFields();
    }

    public void delete() {
        if (selectedDestinationNode.getData() instanceof StepDTO) {
            StepDTO selectedDestinationStep = (StepDTO) selectedDestinationNode.getData();
            removeFromDestination(selectedDestinationStep);
            addToSource(selectedDestinationStep);
        } else if (selectedDestinationNode.getData() instanceof FolderDTO) {
            FolderDTO selectedDestinationFolder = (FolderDTO) selectedDestinationNode.getData();
            removeFromDestination(selectedDestinationFolder);
            addToSource(selectedDestinationFolder);
        }
        populateTrees();
    }

    private void addToSource(FolderDTO selectedDestinationFolder) {
        for (StepDTO step : selectedDestinationFolder.getStepList()) {
            addToSource(step);
        }
    }

    private void removeFromDestination(FolderDTO selectedDestinationFolder) {
        selectedSet.removeFolder(selectedDestinationFolder);

    }

    private void addToSource(StepDTO selectedDestinationStep) {
        if (selectedDestinationStep.getTest() == null) {
            stepsForRemoval.add(selectedDestinationStep);
            return;
        }
        for (TestDTO t : sourceList) {
            if (t.getTestId() == selectedDestinationStep.getTest().getTestId()) {
                t.addStep(selectedDestinationStep);
                return;
            }
        }
        TestDTO t = new TestDTO(selectedDestinationStep.getTest().getTestId());
        t.setName(selectedDestinationStep.getTest().getName());
        List<StepDTO> stepList = new ArrayList<>();
        stepList.add(selectedDestinationStep);
        t.setStepList(stepList);
        sourceList.add(t);
    }

    private void removeFromDestination(StepDTO selectedDestinationStep) {
        FolderDTO forRemoval = null;
        for (FolderDTO folder : selectedSet.getFolderDtoList()) {
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

    public TestSetDTO getSelectedSet() {
        return selectedSet;
    }

    public void setSelectedSet(TestSetDTO selectedSet) {
        this.selectedSet = selectedSet;
    }

    public List<TestSetDTO> getAllSets() {
        return allSets;
    }

    public void setAllSets(List<TestSetDTO> allSets) {
        this.allSets = allSets;
    }

    public List<TestDTO> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<TestDTO> sourceList) {
        this.sourceList = sourceList;
    }

    public List<StepDTO> getStepsForRemoval() {
        return stepsForRemoval;
    }

    public void setStepsForRemoval(List<StepDTO> stepsForRemoval) {
        this.stepsForRemoval = stepsForRemoval;
    }

}
