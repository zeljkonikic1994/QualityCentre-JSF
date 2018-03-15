/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.HibernateUtil;
import entities.TestSet;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ZXNIKIC
 */
public class TestSetDao implements DaoInterface<TestSet, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public TestSetDao() {
    }

    public Session openCurrentSession() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void save(TestSet entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(TestSet entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(TestSet entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public TestSet findById(Integer id) {
        TestSet testSet = (TestSet) getCurrentSession().get(TestSet.class, id);
        return testSet;
    }

    @Override
    public void delete(TestSet entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<TestSet> findAll() {
        List<TestSet> testSets = getCurrentSession().createQuery("from TestSet").list();
        return testSets;
    }

    @Override
    public void deleteAll() {
         List<TestSet> testSets = findAll();
         for (TestSet testSet : testSets) {
            delete(testSet);
        }
    }

}
