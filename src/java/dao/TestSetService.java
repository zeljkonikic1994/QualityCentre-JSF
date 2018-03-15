/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Test;
import entities.TestSet;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TestSetService {
    private static TestSetDao testSetDao;

    public TestSetService() {
        this.testSetDao = new TestSetDao();
    }
    
     public void save(TestSet entity){
        testSetDao.openCurrentSessionWithTransaction();
        testSetDao.save(entity);
        testSetDao.closeCurrentSessionwithTransaction();
    }
    
    public void update(TestSet entity){
        testSetDao.openCurrentSessionWithTransaction();
        testSetDao.update(entity);
        testSetDao.closeCurrentSessionwithTransaction();
    }
    
    public TestSet findById(int id){
        testSetDao.openCurrentSession();
        TestSet testSet = testSetDao.findById(id);
        testSetDao.closeCurrentSession();
        return testSet;
    }
    
    public void delete(int id){
        testSetDao.openCurrentSessionWithTransaction();
        TestSet testSet = testSetDao.findById(id);
        testSetDao.delete(testSet);
        testSetDao.closeCurrentSessionwithTransaction();
    }
    
    public List<TestSet> findAll(){
        testSetDao.openCurrentSession();
        List<TestSet> testSets = testSetDao.findAll();
        testSetDao.closeCurrentSession();
        return testSets;
    }
    
    public void deleteAll(){
        testSetDao.openCurrentSessionWithTransaction();
        testSetDao.deleteAll();
        testSetDao.closeCurrentSessionwithTransaction();
    }
    
    public TestSetDao userDao(){
        return testSetDao;
    }
    
    public void saveOrUpdate(TestSet entity){
        testSetDao.openCurrentSessionWithTransaction();
        testSetDao.saveOrUpdate(entity);
        testSetDao.closeCurrentSessionwithTransaction();
    }
    
}
