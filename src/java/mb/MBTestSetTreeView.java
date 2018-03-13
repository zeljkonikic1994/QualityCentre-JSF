/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import db.HibernateUtil;
import entities.Folder;
import entities.SpecificStep;
import entities.SpecificStepPK;
import entities.Step;
import entities.StepPK;
import entities.Test;
import entities.TestSet;
import entities.TestSetDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    private TreeNode root;
    private TreeNode sourceRoot;
    private TreeNode destinationRoot;
    private TreeNode selectedSourceNode;
    private TreeNode selectedDestinationNode;
    private TestSet selectedSet;
    private List<TestSet> allSets;
    private List<Test> sourceList;

    private TestSetDTO testSet;
    private List<Step> stepsForRemoval;

    /**
     * Creates a new instance of MBTestSetTreeView
     */
    public MBTestSetTreeView() {
    }

    @PostConstruct
    public void init() {
        loadSets();
        stepsForRemoval = new ArrayList<>();
        populateTree();
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

    public void newSet() {
        selectedSet = new TestSet();
        selectedSet.setId(0);
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(selectedSet);
        session.getTransaction().commit();
        session.flush();
        session.close();
        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public void exitWithoutSaving() {
        PrimeFaces.current().dialog().closeDynamic(null);
        clearFields();
    }

    private void clearFields() {
        selectedSet = null;
        loadSets();
        populateTree();
    }

    private void loadSets() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.getNamedQuery("TestSet.findAll");
        List<TestSet> testSetList = query.list();
        session.getTransaction().commit();

        session.flush();
        session.close();
//        if (selectedTest != null) {
//            for (Test test : testList) {
//                if (test.getTestId() == selectedTest.getTestId()) {
//                    selectedTest = test;
//                }
//            }
//        }

        this.allSets = testSetList;
    }

    private void populateTree() {
        root = new DefaultTreeNode("Root", null);
        for (TestSet t : allSets) {
            TreeNode node = new DefaultTreeNode(t, root);
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        this.selectedSet = (TestSet) event.getTreeNode().getData();
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
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

    public void showMessage(String title, String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, text);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    private void prepareDialog() {
        loadSets();
        stepsForRemoval = new ArrayList<>();
        populateTree();
        
        testSet = new TestSetDTO(selectedSet.getName());
        testSet.setDateCreated(selectedSet.getDateCreated());
        testSet.setTestSetId(selectedSet.getId());
        List<Folder> folderList = new ArrayList<>();
        for (SpecificStep ss : selectedSet.getSpecificStepList()) {
            Folder folder = findFolderByName(folderList, ss.getFolder());
            if (folder == null) {
                folder = new Folder(ss.getFolder());
                folderList.add(folder);
            }
            Step step = new Step();
//            step.setStepPK(new StepPK(Integer.MIN_VALUE, Integer.MIN_VALUE));
            step.setStepPK(new StepPK(ss.getSpecificStepPK().getId(), ss.getSpecificStepPK().getTestSetId()));
            step.setName(ss.getName());
            step.setDescription(ss.getDescription());
            step.setExpected(ss.getExpected());
            step.setTest(null);
            folder.addStep(step);
        }
        testSet.setFolderList(folderList);
        loadTests();
        populateTrees();

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
        TreeNode node = new DefaultTreeNode(testSet, destinationRoot);
        node.setExpanded(true);
        for (Folder folder : testSet.getFolderList()) {
            TreeNode folderNode = new DefaultTreeNode("folder", folder, node);
            folderNode.setExpanded(true);
            for (Step step : folder.getStepList()) {
                TreeNode stepNode = new DefaultTreeNode("step", step, folderNode);
//                stepNode.setSelectable(false);
            }
        }
    }

    private Folder findFolderByName(List<Folder> folderList, String folderName) {
        for (Folder folder : folderList) {
            if (folder.getName().equals(folderName)) {
                return folder;
            }
        }
        return null;
    }

    private void loadTests() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.getNamedQuery("Test.findAll");
        List<Test> testList = query.list();
        session.getTransaction().commit();
        session.flush();
        session.close();
        this.sourceList = testList;
    }

    public void onDragDrop2(TreeDragDropEvent event) {
        populateTrees();
    }

    public void onDragDrop(TreeDragDropEvent event) {
//        if(event.getComponent().getId().equals("tree1")){
//             populateTrees();
//             return;
//        }
        TreeNode dragNode = event.getDragNode();
        Object dragData = dragNode.getData();
        TreeNode dropNode = event.getDropNode();
        Object dropData = dropNode.getData();

        boolean dropped = false;
        if (dragData instanceof Test) {
            if (dropData instanceof TestSetDTO) {
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
            if (dropData instanceof TestSetDTO) {
                dropStepToTestSet(dragData);
                dropped = true;
            } else if (dropData instanceof Folder) {
                dropStepToFolder(dragData, dropData);
                dropped = true;
            } else if (dropData instanceof Step) {
                dropStepToStep(dragData, dropData);
                dropped = true;
            }
        } else if(dragData instanceof Folder){
            populateTrees();
        }
        if (dropped) {
            populateTrees();
        }
    }

    private void dropTestToFolder(Object dragData, Object dropData) {
        Test dragTest = (Test) dragData;
        Folder dropFolder = (Folder) dropData;
        for (Folder folder : testSet.getFolderList()) {
            if (folder.equals(dropFolder)) {
                folder.addSteps(dragTest.getStepList());
                removeFromSource(dragTest);
            }
        }
    }

    private void dropTestToStep(Object dragData, Object dropData) {
        Step dropStep = (Step) dropData;
        Test dragTest = (Test) dragData;

        for (Folder folder : testSet.getFolderList()) {
            if (folder.getStepList().contains(dropStep)) {
                folder.addSteps(dragTest.getStepList());
                removeFromSource(dragTest);
            }
        }
    }

    private void dropStepToFolder(Object dragData, Object dropData) {
        Folder dropFolder = (Folder) dropData;
        Step dragStep = (Step) dragData;
        if(dropFolder.getStepList().contains(dragStep)){
            return;
        }
        Folder forRemoval = null;
        for (Folder folder : testSet.getFolderList()) {
            if (folder.equals(dropFolder)) {
                folder.addStep(dragStep);
                if (!removeFromSource(dragStep)) {
                    forRemoval = folder;
                }
            }
        }
        if (forRemoval != null) {
            removeFromDestination(dragStep, forRemoval);
        }

    }

    private void dropStepToStep(Object dragData, Object dropData) {
        Step dragStep = (Step) dragData;
        Step dropStep = (Step) dropData;
        for (Folder folder : testSet.getFolderList()) {
            if (folder.getStepList().contains(dropStep)) {
                folder.addStep(dragStep);
                removeFromSource(dragStep);
            }
        }
    }

    private void removeFromSource(Test dragTest) {
        sourceList.remove(dragTest);
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

    private void dropTestToTestSet(Object dragData) {
        Test dragTest = (Test) dragData;
        Folder folder = new Folder(dragTest.getName());
        folder.setStepList(dragTest.getStepList());
        testSet.addFolder(folder);
        removeFromSource(dragTest);
    }

    private void dropStepToTestSet(Object dragData) {
        Step step = (Step) dragData;
        System.out.println("dropStepToTestSet - " + step);

        int newFolderCount = countNewFolders();
        System.out.println("newFolderCount - " + newFolderCount);
        Folder folder = new Folder();
        if (newFolderCount > 0) {
            folder.setName("New_folder_" + (newFolderCount + 1));
        } else {
            folder.setName("New_folder");
        }
        folder.addStep(step);
        System.out.println("Added step " + step + " to folder " + folder);
        testSet.addFolder(folder);
        System.out.println("Added folder " + folder + " to testSet " + testSet);
        if (!removeFromSource(step)) {
            System.out.println("Didn't remove from source");
            removeFromDestination(step, folder);
        }
    }

    private void removeFromDestination(Step step, Folder newFolder) {
        Folder forRemoval = null;
        for (Folder folder : testSet.getFolderList()) {
            if (folder.equals(newFolder)) {
                continue;
            }
            if (folder.getStepList().contains(step)) {
                folder.removeStep(step);
            }
            System.out.println("Folder " + folder.getName() + " step list size: " + folder.getStepList().size());
            if (folder.getStepList().isEmpty()) {
                forRemoval = folder;
            }
        }
        if (forRemoval != null) {
            testSet.removeFolder(forRemoval);
        }
    }

    private int countNewFolders() {
        int counter = 0;
        for (Folder folder : testSet.getFolderList()) {
            if (folder.getName().contains("New_folder")) {
                counter++;
            }
        }
        return counter;
    }

    private void removeFromDestination(Step selectedDestinationStep) {
        Folder forRemoval = null;
        for (Folder folder : testSet.getFolderList()) {
            if (folder.getStepList().contains(selectedDestinationStep)) {
                folder.removeStep(selectedDestinationStep);
            }
            if (folder.getStepList().isEmpty()) {
                forRemoval = folder;
            }
        }
        if (forRemoval != null) {
            testSet.removeFolder(forRemoval);
        }
    }

    private void addToSource(Step selectedDestinationStep) {
        if (selectedDestinationStep.getTest() == null) {
            stepsForRemoval.add(selectedDestinationStep);
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

    private void removeFromDestination(Folder selectedDestinationFolder) {
        testSet.removeFolder(selectedDestinationFolder);

    }

    private void addToSource(Folder selectedDestinationFolder) {
        for (Step step : selectedDestinationFolder.getStepList()) {
            addToSource(step);
        }
    }

    public void onSaveNodeLabel(Object node) {
        System.out.println(node);
    }

    public void saveTestSet() {
//        selectedSet.setDateModified(new Date());
        TestSet ts = new TestSet(testSet.getTestSetId());
        ts.setName(testSet.getName());
        ts.setDateCreated(testSet.getDateCreated());
        ts.setDateModified(new Date());
        List<SpecificStep> specificSteps = new ArrayList<>();
        int stepCount = 1;
        for (Folder folder : testSet.getFolderList()) {
            for (Step step : folder.getStepList()) {
                SpecificStep ss = new SpecificStep();
                ss.setName(step.getName());
                ss.setDescription(step.getDescription());
                ss.setExpected(step.getExpected());
                ss.setFolder(folder.getName());
                ss.setSpecificStepPK(new SpecificStepPK(stepCount, selectedSet.getId()));
                ss.setTestSet(ts);
                specificSteps.add(ss);
                stepCount++;
            }
        }
        deleteStepsForRemoval();
        ts.setSpecificStepList(specificSteps);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(ts);
        session.getTransaction().commit();
        session.flush();
        session.close();
        
        clearFields();
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

    public TreeNode getSourceRoot() {
        return sourceRoot;
    }

    public void setSourceRoot(TreeNode sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    public TreeNode getDestinationRoot() {
        return destinationRoot;
    }

    public void setDestinationRoot(TreeNode destinationRoot) {
        this.destinationRoot = destinationRoot;
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

    public List<Test> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Test> sourceList) {
        this.sourceList = sourceList;
    }

    public TestSetDTO getTestSet() {
        return testSet;
    }

    public void setTestSet(TestSetDTO testSet) {
        this.testSet = testSet;
    }

    public void deleteSet() {
        if (selectedSet != null) {
            deleteSelectedSet(selectedSet);
            clearFields();
        } else {
            showMessage("Error", "You must select a set to delete!");
        }
    }

    private void deleteSelectedSet(TestSet selectedSet) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(selectedSet);
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

    private void deleteStepsForRemoval() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        for (Step step : stepsForRemoval) {
            String sql = "DELETE FROM specificstep where id='"+step.getStepPK().getStepId()+"' and test_set_id='"+step.getStepPK().getTestId()+"'";
            session.createSQLQuery(sql).executeUpdate();
        }
        
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

}
