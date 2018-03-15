/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.HibernateUtil;
import entities.SpecificStep;
import entities.SpecificStepPK;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ZXNIKIC
 */
public class SpecificStepDao implements DaoInterface<SpecificStep, SpecificStepPK> {

    private Session currentSession;

    private Transaction currentTransaction;

    public SpecificStepDao() {
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
    public void save(SpecificStep entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(SpecificStep entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(SpecificStep entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public SpecificStep findById(SpecificStepPK id) {
        SpecificStep step = (SpecificStep) getCurrentSession().get(SpecificStep.class, id);
        return step;
    }

    @Override
    public void delete(SpecificStep entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<SpecificStep> findAll() {
        List<SpecificStep> steps = getCurrentSession().createQuery("from SpecificStep").list();
        return steps;
    }

    @Override
    public void deleteAll() {
        List<SpecificStep> steps = findAll();
        for (SpecificStep step : steps) {
            delete(step);
        }
    }

}
