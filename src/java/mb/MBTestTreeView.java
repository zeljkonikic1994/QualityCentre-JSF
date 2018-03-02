/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import db.HibernateUtil;
import entities.Step;
import entities.StepPK;
import entities.Test;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ZXNIKIC
 */
@ManagedBean(name = "mbTestTreeView")
@SessionScoped
public class MBTestTreeView implements Serializable {

    private TreeNode root;
    private Test selectedTest;
    private List<Test> allTests;
    private Step newStep;

    @PostConstruct
    public void init() {
        loadTests();
        setStepNumbers(allTests);
        populateTree();
        newStep = new Step();
        newStep.setDescription("");
        newStep.setExpected("");
        newStep.setName("");
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

    public void deleteTest() {
        if (selectedTest != null) {
            deleteSelectedTest(selectedTest);
            clearFields();
        } else {
            showMessage("Error", "You must select a test to delete!");
        }
    }

    public void showMessage(String title, String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, text);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void saveTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(selectedTest);
        session.getTransaction().commit();
        session.flush();
        session.close();
        
        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public void deleteStep(Step s) {
        selectedTest.getStepList().remove(s);
        setStepNumbers(selectedTest);
    }

    public void addNewStep() {
        Step step = new Step();
        step.setName(newStep.getName());
        step.setDescription(newStep.getDescription());
        step.setExpected(newStep.getExpected());
        step.setTest(selectedTest);
        step.setStepPK(new StepPK(selectedTest.getStepList().size() + 1, selectedTest.getTestId()));
        selectedTest.addStep(step);
        setStepNumbers(selectedTest);
    }

    public void exitWithSaving() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(selectedTest);
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

    private void setStepNumbers(List<Test> testList) {
        for (Test t : testList) {
            int no = 1;
            for (Step s : t.getStepList()) {
                s.setStepPK(new StepPK(no, t.getTestId()));
                no++;
            }
        }
    }

    private void setStepNumbers(Test selectedTest) {
        int no = 1;
        for (Step s : selectedTest.getStepList()) {
            s.setStepPK(new StepPK(no, selectedTest.getTestId()));
            no++;
        }
    }

    private void clearFields() {
        selectedTest = null;
        newStep = new Step();
        newStep.setDescription("");
        newStep.setExpected("");
        newStep.setName("");
        loadTests();
        setStepNumbers(allTests);
        populateTree();
    }

    private void deleteSelectedTest(Test selectedTest) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(selectedTest);
        session.getTransaction().commit();

        session.flush();
        session.close();
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Test " + selectedTest.getName()+ " is deleted", null);
//        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void loadTests() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.getNamedQuery("Test.findAll");
        List<Test> testList = query.list();
        session.getTransaction().commit();

        session.flush();
        session.close();
        if (selectedTest != null) {
            for (Test test : testList) {
                if (test.getTestId() == selectedTest.getTestId()) {
                    selectedTest = test;
                }
            }
        }
        setStepNumbers(testList);

        this.allTests = testList;
    }

    public void populateTree() {
        root = new DefaultTreeNode("Root", null);
        for (Test t : allTests) {
            TreeNode node = new DefaultTreeNode("test", t, root);
            for (Step s : t.getStepList()) {
                TreeNode child = new DefaultTreeNode("step", s, node);
//                child.setSelectable(false);
            }
        }
    }

    public void onRowEdit(RowEditEvent event) {
        Step s = (Step) event.getObject();
        for (Test test : allTests) {
            for (Step step : test.getStepList()) {
                if(step.getStepPK().equals(s.getStepPK())){
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

    public TreeNode getRoot() {
        return root;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (event.getTreeNode().getData() instanceof Test) {
            this.selectedTest = (Test) event.getTreeNode().getData();
        }else if(event.getTreeNode().getData() instanceof Step){
            this.selectedTest = ((Step) event.getTreeNode().getData()).getTest();
        }
    }

    public Test getSelectedTest() {
        return selectedTest;
    }

    public void setSelectedTest(Test selectedTest) {
        this.selectedTest = selectedTest;
    }

    public Step getNewStep() {
        return newStep;
    }

    public void setNewStep(Step newStep) {
        this.newStep = newStep;
    }

}
