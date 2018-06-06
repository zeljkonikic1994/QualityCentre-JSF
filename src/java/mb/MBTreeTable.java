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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbTreeTable")
@SessionScoped
public class MBTreeTable implements Serializable {

    @Inject
    Controller controller;

    @Inject
    MBTestRun mbTestRun;
    private List<TestSet> testSets;

    private TreeNode root;
    private TreeNode selectedNode;

    /**
     * Creates a new instance of MBTreeTable
     */
    @PostConstruct
    public void init() {
        loadSets();
        populateTreeTable();
    }

    public MBTreeTable() {
    }

    private void populateTreeTable() {
        root = new DefaultTreeNode("Root", null);
        for (TestSet testSet : testSets) {
            TreeNode setNode = new DefaultTreeNode(new TreeTableNode(testSet), root);
            for (Folder folder : testSet.getFolderList()) {
                TreeNode folderNode = new DefaultTreeNode(new TreeTableNode(folder), setNode);
                folderNode.setSelectable(false);
                for (Step step : folder.getStepList()) {
                    TreeNode stepNode = new DefaultTreeNode(new TreeTableNode(step), folderNode);
                    stepNode.setSelectable(false);
                }
            }
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void runTest() {
        if (selectedNode != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("resizable", false);
            options.put("modal", true);
            options.put("width", 800);
            options.put("height", 600);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("headerElement", "customheader");
            options.put("closable", true);
            mbTestRun.setTestSet((TestSet)((TreeTableNode) selectedNode.getData()).getObject());
            PrimeFaces.current().dialog().openDynamic("dialogTestRun", options, null);
        } else {
            showMessage("Error!", "You must select a test to run!");
        }
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void showMessage(String title, String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, text);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    private void loadSets() {
        testSets = controller.getSets();
    }

    public void refresh() {
        loadSets();
        populateTreeTable();
    }
}
