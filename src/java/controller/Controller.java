/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StepService;
import dao.TestService;
import dao.TypeService;
import db.HibernateUtil;
import dao.UserService;
import entities.Step;
import entities.StepPK;
import entities.Test;
import entities.TestSet;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import entities.Type;
import entities.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        TypeService typeService = new TypeService();
        Type type = typeService.findByName(name);
        return type;
    }

    public User getUserWithSpecifiedUserName(String username) {
        UserService userService = new UserService();
        User user = userService.findById(username);
        return user;
    }

    public List<Type> getTypes() {
        TypeService typeService = new TypeService();
        List<Type> types = typeService.findAll();
        return types;
    }

    public void saveUser(User newUser) {
        UserService userService = new UserService();
        userService.save(newUser);
    }

    public void updateUser(User user) {
        UserService userService = new UserService();
        userService.update(user);
    }

    public List<User> getAllUsers() {
        UserService userService = new UserService();
        List<User> users = userService.findAll();
        return users;
    }

    public void deleteUser(String username) {
        UserService userService = new UserService();
        userService.delete(username);
    }

    private static List<Test> loadTests() {
        TestService testService = new TestService();
        List<entities.Test> testList = testService.findAll();
        return testList;
    }

    public void saveTest(dto.Test testDTO) {
        TestService testService = new TestService();
        testService.save(TestConverter.convertTo(testDTO));
    }

    public void saveSteps(List<dto.Step> stepList) {
        StepService stepService = new StepService();

        List<Step> stepsForDB = EntityHelper.convertToStepList(stepList);
        for (Step step : stepsForDB) {
            stepService.saveOrUpdate(step);
        }
    }

    public void deleteStepList(List<dto.Step> removedSteps) {
        StepService stepService = new StepService();
        for (dto.Step removedStep : removedSteps) {
            stepService.delete(removedStep.getStepId(), removedStep.getTestId());
        }
    }

    public void deleteTest(dto.Test selectedTest) {
        TestService testService = new TestService();
        testService.delete(selectedTest.getTestId());
    }

    private static List<TestSet> loadSets() {
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

    public void saveSet(dto.TestSet selectedSet) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(TestSetConverter.convertToTestSet(selectedSet));
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void deleteSet(dto.TestSet selectedSet) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.delete(TestSetConverter.convertToTestSet(selectedSet));
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

    public void deleteSpecificStepList(List<dto.Step> stepsForRemoval) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (dto.Step step : stepsForRemoval) {
            String sql = "DELETE FROM specificstep where id='" + step.getStepId() + "' and test_set_id='" + step.getTestId() + "'";
            session.createSQLQuery(sql).executeUpdate();
        }
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void updateSet(dto.TestSet selectedSet) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(TestSetConverter.convertToTestSet(selectedSet));
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public List<dto.Test> getTests() {
        return EntityHelper.convertFromTestList(loadTests());
    }

    public List<dto.TestSet> getSets() {
        return EntityHelper.convertFromTestSetList(loadSets());
    }

}
