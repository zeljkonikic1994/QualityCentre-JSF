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
import entities.CompletionStatus;
import entities.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbTestRun")
@SessionScoped
public class MBTestRun implements Serializable {

    @Inject
    Controller controller;

    TestSet testSet;
    TreeNode root;
    List<CompletionStatus> statuses;

    /**
     * Creates a new instance of MBTestRun
     */
    public MBTestRun() {
        if (controller == null) {
            controller = new Controller();
        }
        statuses = controller.getAllStatuses();
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TestSet getTestSet() {
        return testSet;
    }

    public void setTestSet(TestSet testSet) {
        this.testSet = testSet;
        populateTreeTable();
    }

    private void populateTreeTable() {
        root = new DefaultTreeNode("Root", null);
        TreeNode setNode = new DefaultTreeNode(new TreeTableNode(testSet), root);
        setNode.setExpanded(true);
        for (Folder folder : testSet.getFolderList()) {
            TreeNode folderNode = new DefaultTreeNode(new TreeTableNode(folder), setNode);
            folderNode.setSelectable(false);
            folderNode.setExpanded(true);
            for (Step step : folder.getStepList()) {
                TreeNode stepNode = new DefaultTreeNode(new TreeTableNode(step), folderNode);
                stepNode.setSelectable(false);
                stepNode.setExpanded(true);
            }
        }
    }

    public void handleChange(ValueChangeEvent event) {
        System.out.println(event.getOldValue() + " " + event.getNewValue());
    }

    public List<CompletionStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CompletionStatus> statuses) {
        this.statuses = statuses;
    }

    public void exitWithSaving() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        testSet.setModifiedBy(user.getUserName());
        controller.updateTestSet(testSet);
        PrimeFaces.current().dialog().closeDynamic(null);
        this.testSet = null;
    }

    public void exitWithoutSaving() {
        PrimeFaces.current().dialog().closeDynamic(null);
        this.testSet = null;
    }

}
