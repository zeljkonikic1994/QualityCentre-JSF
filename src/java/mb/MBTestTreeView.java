/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import controller.Controller;
import dto.Step;
import dto.Test;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbTestTreeView")
@SessionScoped
public class MBTestTreeView implements Serializable {

    @Inject
    Controller controller;
    
    private TreeNode root;
    private Test selectedTest;
    private List<Test> allTests;
    private Step newStep;
    private List<Step> removedSteps;

    @PostConstruct
    public void init() {
        loadTests();
        setStepNumbers(allTests);
        populateTree();

        removedSteps = new ArrayList<>();

        newStep = new Step();
        newStep.setDescription("");
        newStep.setExpected("");
        newStep.setName("");
    }

    /**
     * Creates a new instance of MBTestTreeView2
     */
    public MBTestTreeView() {
    }

    public void loadTests() {
        this.allTests = controller.getTests();
        if (selectedTest != null) {
            for (Test test : allTests) {
                if (test.getTestId() == selectedTest.getTestId()) {
                    selectedTest = test;
                }
            }
        }
    }

    private void setStepNumbers(List<Test> testList) {
        for (Test testDTO : testList) {
            int no = 1;
            for (Step stepDTO : testDTO.getStepList()) {
                stepDTO.setStepId(no);
                no++;
            }
        }
    }

    private void populateTree() {
        root = new DefaultTreeNode("Root", null);
        for (Test test : allTests) {
            TreeNode node = new DefaultTreeNode("test", test, root);
            for (Step step : test.getStepList()) {
                TreeNode child = new DefaultTreeNode("step", step, node);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (event.getTreeNode().getData() instanceof Test) {
            this.selectedTest = (Test) event.getTreeNode().getData();
        } else if (event.getTreeNode().getData() instanceof Step) {
            this.selectedTest = ((Step) event.getTreeNode().getData()).getTest();
        }
    }

    public void newTest() {
        selectedTest = new Test();
        selectedTest.setTestId(0);
        selectedTest.setDateCreated(new Date());
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 211);
        options.put("height", 234);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("closable", true);

        PrimeFaces.current().dialog().openDynamic("dialogAddTest", options, null);
    }

    public void saveTest() {
        controller.saveTest(selectedTest);
        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    private void clearFields() {
        selectedTest = null;
        removedSteps = new ArrayList<>();

        newStep = new Step();
        newStep.setDescription("");
        newStep.setExpected("");
        newStep.setName("");
        loadTests();
        setStepNumbers(allTests);
        populateTree();
    }

    public void exitWithoutSaving() {
        PrimeFaces.current().dialog().closeDynamic(null);
        clearFields();
    }

    public void viewTest() {
        if (selectedTest != null) {

            Map<String, Object> options = new HashMap<String, Object>();
            options.put("resizable", true);
            options.put("modal", true);
            options.put("width", 850);
            options.put("height", 550);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("headerElement", "customheader");
            options.put("closable", false);

            PrimeFaces.current().dialog().openDynamic("dialogEditTest", options, null);
        } else {
            showMessage("Error", "You must select a test to edit!");
        }
    }

    public void showMessage(String title, String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, text);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void onRowEdit(RowEditEvent event) {
        Step s = (Step) event.getObject();
        for (Test test : allTests) {
            for (Step step : test.getStepList()) {
                if (step.equals(s)) {
                    step.setName(s.getName());
                    step.setDescription(s.getDescription());
                    step.setExpected(s.getExpected());
                }
            }
        }
        FacesMessage msg = new FacesMessage("Step edited", (s.getName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", (((Step) event.getObject()).getName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteStep(Step s) {
        selectedTest.getStepList().remove(s);
        removedSteps.add(s);
        setStepNumbers(selectedTest);
    }

    private void setStepNumbers(Test selectedTest) {
        int no = 1;
        for (Step s : selectedTest.getStepList()) {
            s.setStepId(no);
            no++;
        }
    }

    public void addNewStep() {
        Step step = new Step(selectedTest.getStepList().size() + 1, selectedTest.getTestId(), newStep.getName(), newStep.getDescription(), newStep.getExpected(), selectedTest);
        selectedTest.addStep(step);
        setStepNumbers(selectedTest);
    }

    public void exitWithSaving() {
        deleteSteps();
        saveSteps();
        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public Test getSelectedTest() {
        return selectedTest;
    }

    public void setSelectedTest(Test selectedTest) {
        this.selectedTest = selectedTest;
    }

    public List<Test> getAllTests() {
        return allTests;
    }

    public void setAllTests(List<Test> allTests) {
        this.allTests = allTests;
    }

    public Step getNewStep() {
        return newStep;
    }

    public void setNewStep(Step newStep) {
        this.newStep = newStep;
    }

    private void deleteSteps() {
        if (removedSteps.isEmpty()) {
            return;
        }
        controller.deleteStepList(removedSteps);
        removedSteps = new ArrayList<>();
    }

    public void deleteTest() {
        if (selectedTest != null) {
            deleteSelectedTest();
            clearFields();
        } else {
            showMessage("Error", "You must select a test to delete!");
        }
    }
    private void deleteSelectedTest() {
        controller.deleteTest(selectedTest);
    }

    private void saveSteps() {
        controller.saveSteps(selectedTest.getStepList());
    }

}
