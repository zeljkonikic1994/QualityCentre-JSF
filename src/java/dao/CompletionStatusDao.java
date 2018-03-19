/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.HibernateUtil;
import entities.CompletionStatus;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ZXNIKIC
 */
public class CompletionStatusDao implements DaoInterface<CompletionStatus, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

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
    public void save(CompletionStatus entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(CompletionStatus entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(CompletionStatus entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public CompletionStatus findById(Integer id) {
        CompletionStatus completionStatus = (CompletionStatus) getCurrentSession().get(CompletionStatus.class, id);
        return completionStatus;
    }

    @Override
    public void delete(CompletionStatus entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<CompletionStatus> findAll() {
        List<CompletionStatus> statuses = getCurrentSession().createQuery("from CompletionStatus").list();
        return statuses;
    }

    @Override
    public void deleteAll() {
        List<CompletionStatus> statuses = findAll();
        for (CompletionStatus status : statuses) {
            delete(status);
        }
    }

    public CompletionStatus findByName(String name) {
        Criteria query = getCurrentSession().createCriteria(CompletionStatus.class);
        CompletionStatus status = (CompletionStatus) query.add(Restrictions.eq("name", name)).uniqueResult();
        return status;
    }

}
