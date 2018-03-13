/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import db.HibernateUtil;
import entities.TestSet;
import entities.TreeTableNode;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbTreeTable")
@SessionScoped
public class MBTreeTable implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;
    private List<TestSet> allSets;

    @PostConstruct
    public void init() {
        loadSets();
        populateTreeTable();
    }

    /**
     * Creates a new instance of MBTreeTable
     */
    public MBTreeTable() {
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
        this.allSets = testSetList;
    }

    private void populateTreeTable() {
        root = new DefaultTreeNode("Root", null);
        for(TestSet t: allSets){
            TreeTableNode ttn = new TreeTableNode(t);
            TreeNode node = new DefaultTreeNode(t,root);
        }
                
    }

}
