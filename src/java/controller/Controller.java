/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.HibernateUtil;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import entities.Type;
import entities.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author zeljk
 */
@Named("Controller")
@ApplicationScoped
public class Controller implements Serializable {

    public Type getTypeByName(String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Criteria query = session.createCriteria(Type.class);
        Type type = (Type) query.add(Restrictions.eq("name", name)).uniqueResult();
        session.getTransaction().commit();

        session.flush();
        session.close();
        return type;
    }

    public List<Type> loadTypes() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(Type.class);
        query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Type> types = (List<Type>) query.list();

        session.getTransaction().commit();
        session.flush();
        session.close();

//        Set<Type> hs = new HashSet<>();
//        hs.addAll(types);
//        types.clear();
//        types.addAll(hs);
        System.out.println("Velicina liste iz baze: " + types.size());

        return types;
    }

}
