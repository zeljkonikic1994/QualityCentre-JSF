/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.HibernateUtil;
import entities.Step;
import entities.StepPK;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import dao.DaoInterface;

/**
 *
 * @author ZXNIKIC
 */
public class StepDao implements DaoInterface<Step, StepPK> {

    private Session currentSession;

    private Transaction currentTransaction;

    public StepDao() {
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
    public void save(Step entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Step entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Step findById(StepPK id) {
        Step step = (Step) getCurrentSession().get(Step.class, id);
        return step;
    }

    @Override
    public void delete(Step entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<Step> findAll() {
        List<Step> steps = getCurrentSession().createQuery("from Step").list();
        return steps;
    }

    @Override
    public void deleteAll() {
        List<Step> steps = findAll();
        for (Step step : steps) {
            delete(step);
        }
    }

    @Override
    public void saveOrUpdate(Step step) {
        getCurrentSession().saveOrUpdate(step);
    }

}
