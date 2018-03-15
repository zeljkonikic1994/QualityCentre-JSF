/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.HibernateUtil;
import entities.Type;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ZXNIKIC
 */
public class TypeDao implements DaoInterface<Type, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    public TypeDao() {
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
    public void save(Type entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Type entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Type findById(Integer id) {
        Type type = (Type) getCurrentSession().get(Type.class, id);
        return type;
    }

    @Override
    public void delete(Type entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<Type> findAll() {
        List<Type> types = getCurrentSession().createQuery("from Type").list();
        return types;
    }

    @Override
    public void deleteAll() {
        List<Type> typeList = findAll();
        for (Type type : typeList) {
            delete(type);
        }
    }

    public Type findByName(String name) {
        Criteria query = getCurrentSession().createCriteria(Type.class);
        Type type = (Type) query.add(Restrictions.eq("name", name)).uniqueResult();
        return type;
    }

    @Override
    public void saveOrUpdate(Type entity) {
        getCurrentSession().saveOrUpdate(entity);
    }
}
