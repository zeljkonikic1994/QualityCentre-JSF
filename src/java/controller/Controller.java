/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.HibernateUtil;
import dto.StepDTO;
import dto.TestDTO;
import dto.TestSetDTO;
import entities.Step;
import entities.Test;
import entities.TestSet;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import entities.Type;
import entities.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.EntityHelper;
import util.TestConverter;
import util.TestSetConverter;

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

    public User getUserWithSpecifiedUserName(String username) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Criteria query = session.createCriteria(User.class);
        User user = (User) query.add(Restrictions.eq("userName", username)).uniqueResult();
        session.getTransaction().commit();

        session.flush();
        session.close();

        return user;
    }

    public List<Type> getTypes() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(Type.class);
        query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Type> types = (List<Type>) query.list();
        session.getTransaction().commit();

        session.flush();
        session.close();

        return types;
    }

    public void saveUser(User newUser) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(newUser);
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

    public void updateUser(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Criteria query = session.createCriteria(User.class);

        session.update(user);
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

    public List<User> getAllUsers() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(User.class);
        List<User> users = (List<User>) query.list();
        session.getTransaction().commit();

        session.flush();
        session.close();

        return users;
    }

    public void deleteUser(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(user);
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public List<Test> loadTests() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.getNamedQuery("Test.findAll");
        List<Test> testList = query.list();
        session.getTransaction().commit();

        session.flush();
        session.close();

        return testList;
    }

    public void saveTest(TestDTO testDTO) {
        Test testForDB = TestConverter.convertTo(testDTO);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(testForDB);

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void saveSteps(List<StepDTO> stepList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Step> stepsForDB = EntityHelper.convertToStepList(stepList);

        for (Step step : stepsForDB) {
            session.saveOrUpdate(step);
        }
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void deleteStepList(List<StepDTO> removedSteps) {
        List<Step> listForRemoval = EntityHelper.convertToStepList(removedSteps);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        for (Step step : listForRemoval) {
            System.out.println("Deleting step " + step);
            session.delete(step);
        }

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void deleteTest(TestDTO selectedTest) {
        Test test = TestConverter.convertTo(selectedTest);
        test.setStepList(EntityHelper.convertToStepList(selectedTest.getStepList()));

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(test);
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

    public List<TestSet> loadSets() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.getNamedQuery("TestSet.findAll");
        List<TestSet> testSetList = query.list();
        session.getTransaction().commit();

        session.flush();
        session.close();

        return testSetList;
    }

    public void saveSet(TestSetDTO selectedSet) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(TestSetConverter.convertToTestSet(selectedSet));
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void deleteSet(TestSetDTO selectedSet) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.delete(TestSetConverter.convertToTestSet(selectedSet));
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

    public void deleteSpecificStepList(List<StepDTO> stepsForRemoval) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (StepDTO step : stepsForRemoval) {
            String sql = "DELETE FROM specificstep where id='" + step.getStepId() + "' and test_set_id='" + step.getTestId() + "'";
            session.createSQLQuery(sql).executeUpdate();
        }
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void updateSet(TestSetDTO selectedSet) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(TestSetConverter.convertToTestSet(selectedSet));
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

}
