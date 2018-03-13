/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import db.HibernateUtil;
import dto.StepDTO;
import dto.TestDTO;
import entities.Step;
import entities.StepPK;
import entities.Test;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import util.EntityHelper;
import util.TestConverter;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbTestTreeView2")
@SessionScoped
public class MBTestTreeView2 implements Serializable {

    private TreeNode root;
    private TestDTO selectedTest;
    private List<TestDTO> allTests;
    private StepDTO newStep;
    private List<StepDTO> removedSteps;

    @PostConstruct
    public void init() {
        loadTests();
        setStepNumbers(allTests);
        populateTree();

        removedSteps = new ArrayList<>();

        newStep = new StepDTO();
        newStep.setDescription("");
        newStep.setExpected("");
        newStep.setName("");
    }

    /**
     * Creates a new instance of MBTestTreeView2
     */
    public MBTestTreeView2() {
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
            for (TestDTO test : allTests) {
                if (test.getTestId() == selectedTest.getTestId()) {
                    selectedTest = test;
                }
            }
        }
        List<TestDTO> dtos = EntityHelper.convertFromTestList(testList);
        this.allTests = dtos;
    }

    private void setStepNumbers(List<TestDTO> testList) {
        for (TestDTO testDTO : testList) {
            int no = 1;
            for (StepDTO stepDTO : testDTO.getStepList()) {
                stepDTO.setStepId(no);
                no++;
            }
        }
    }

    private void populateTree() {
        root = new DefaultTreeNode("Root", null);
        for (TestDTO test : allTests) {
            TreeNode node = new DefaultTreeNode("test", test, root);
            for (StepDTO step : test.getStepList()) {
                TreeNode child = new DefaultTreeNode("step", step, node);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (event.getTreeNode().getData() instanceof TestDTO) {
            this.selectedTest = (TestDTO) event.getTreeNode().getData();
        } else if (event.getTreeNode().getData() instanceof StepDTO) {
            this.selectedTest = ((StepDTO) event.getTreeNode().getData()).getTest();
        }
    }

    public void newTest() {
        selectedTest = new TestDTO();
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(TestConverter.convertTo(selectedTest));
        session.getTransaction().commit();
        session.flush();
        session.close();

        clearFields();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    private void clearFields() {
        selectedTest = null;
        removedSteps = new ArrayList<>();

        newStep = new StepDTO();
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

            PrimeFaces.current().dialog().openDynamic("dialogEditTest_1", options, null);
        } else {
            showMessage("Error", "You must select a test to edit!");
        }
    }

    public void showMessage(String title, String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, text);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void onRowEdit(RowEditEvent event) {
        StepDTO s = (StepDTO) event.getObject();
        for (TestDTO test : allTests) {
            for (StepDTO step : test.getStepList()) {
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

    public void deleteStep(StepDTO s) {

        selectedTest.getStepList().remove(s);
        removedSteps.add(s);
        setStepNumbers(selectedTest);
    }

    private void setStepNumbers(TestDTO selectedTest) {
        int no = 1;
        for (StepDTO s : selectedTest.getStepList()) {
            s.setStepId(no);
            no++;
        }
    }

    public void addNewStep() {
        StepDTO step = new StepDTO(selectedTest.getStepList().size() + 1, selectedTest.getTestId(), newStep.getName(), newStep.getDescription(), newStep.getExpected(), selectedTest);
        selectedTest.addStep(step);
        setStepNumbers(selectedTest);
    }

    public void exitWithSaving() {

        deleteSteps();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Test testForDB = TestConverter.convertTo(selectedTest);
        testForDB.setStepList(EntityHelper.convertToStepList(selectedTest.getStepList()));

        session.saveOrUpdate(testForDB);
        for (Step step : testForDB.getStepList()) {
            session.saveOrUpdate(step);
        }
        session.getTransaction().commit();
        session.flush();
        session.close();
        clearFields();
//        HibernateUtil.getSessionFactory().close();
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TestDTO getSelectedTest() {
        return selectedTest;
    }

    public void setSelectedTest(TestDTO selectedTest) {
        this.selectedTest = selectedTest;
    }

    public List<TestDTO> getAllTests() {
        return allTests;
    }

    public void setAllTests(List<TestDTO> allTests) {
        this.allTests = allTests;
    }

    public StepDTO getNewStep() {
        return newStep;
    }

    public void setNewStep(StepDTO newStep) {
        this.newStep = newStep;
    }

    private void deleteSteps() {
        if (removedSteps.isEmpty()) {
            return;
        }

        List<Step> listForRemoval = EntityHelper.convertToStepList(removedSteps);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        for (Step step : listForRemoval) {
            session.delete(step);
        }

        session.getTransaction().commit();
        session.flush();
        session.close();
        removedSteps = new ArrayList<>();
    }

    public void deleteTest() {
        if (selectedTest != null) {
            Test test = TestConverter.convertTo(selectedTest);
            test.setStepList(EntityHelper.convertToStepList(selectedTest.getStepList()));
            deleteSelectedTest(test);
            clearFields();
        } else {
            showMessage("Error", "You must select a test to delete!");
        }
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

}
