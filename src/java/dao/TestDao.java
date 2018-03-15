/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DaoInterface;
import db.HibernateUtil;
import entities.Test;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ZXNIKIC
 */
public class TestDao implements DaoInterface<Test, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    public TestDao() {
    }

    
    public Session openCurrentSession(){
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
    public void save(Test entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Test entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Test findById(Integer id) {
        Test test = (Test) getCurrentSession().get(Test.class, id);
        return test;
    }

    @Override
    public void delete(Test entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<Test> findAll() {
        List<Test> tests = getCurrentSession().createQuery("from Test").list();
        return tests;
    }

    @Override
    public void deleteAll() {
        List<Test> tests = findAll();
        for (Test test : tests) {
            delete(test);
        }
    }

}
