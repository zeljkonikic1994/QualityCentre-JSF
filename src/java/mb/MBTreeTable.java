/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import controller.Controller;
import dto.Folder;
import dto.Step;
import dto.TestSet;
import dto.TreeTableNode;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
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

    private List<TestSet> testSets;

    private TreeNode root;

    /**
     * Creates a new instance of MBTreeTable
     */
    @PostConstruct
    public void init() {
        testSets = controller.getSets();
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
                for (Step step : folder.getStepList()) {
                    TreeNode stepNode = new DefaultTreeNode(new TreeTableNode(step), folderNode);
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
    
    public void runTest(){
        System.out.println("POZVAO!!!");
    }

}
