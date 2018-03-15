/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.UserDao;
import entities.Test;
import entities.User;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestService {
    
    private static TestDao testDao;

    public TestService() {
        this.testDao = new TestDao();
    }
    
    
    public void save(Test entity){
        testDao.openCurrentSessionWithTransaction();
        testDao.save(entity);
        testDao.closeCurrentSessionwithTransaction();
    }
    
    public void update(Test entity){
        testDao.openCurrentSessionWithTransaction();
        testDao.update(entity);
        testDao.closeCurrentSessionwithTransaction();
    }
    
    public Test findById(int id){
        testDao.openCurrentSession();
        Test test = testDao.findById(id);
        testDao.closeCurrentSession();
        return test;
    }
    
    public void delete(int id){
        testDao.openCurrentSessionWithTransaction();
        Test test = testDao.findById(id);
        System.out.println("TEST "+test );
        testDao.delete(test);
        testDao.closeCurrentSessionwithTransaction();
    }
    
    public List<Test> findAll(){
        testDao.openCurrentSession();
        List<Test> tests = testDao.findAll();
        testDao.closeCurrentSession();
        return tests;
    }
    
    public void deleteAll(){
        testDao.openCurrentSessionWithTransaction();
        testDao.deleteAll();
        testDao.closeCurrentSessionwithTransaction();
    }
    
    public TestDao userDao(){
        return testDao;
    }
    
    
}
