/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.SpecificStep;
import entities.SpecificStepPK;
import entities.Test;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class SpecificStepService {
    private static SpecificStepDao specificStepDao;

    public SpecificStepService() {
        this.specificStepDao = new SpecificStepDao();
    }
    
    public void save(SpecificStep entity){
        specificStepDao.openCurrentSessionWithTransaction();
        specificStepDao.save(entity);
        specificStepDao.closeCurrentSessionwithTransaction();
    }
    
    public void update(SpecificStep entity){
        specificStepDao.openCurrentSessionWithTransaction();
        specificStepDao.update(entity);
        specificStepDao.closeCurrentSessionwithTransaction();
    }
    
    public SpecificStep findById(SpecificStepPK id){
        specificStepDao.openCurrentSession();
        SpecificStep step = specificStepDao.findById(id);
        specificStepDao.closeCurrentSession();
        return step;
    }
    
    public void delete(SpecificStepPK id){
        specificStepDao.openCurrentSessionWithTransaction();
        SpecificStep step = specificStepDao.findById(id);
        specificStepDao.delete(step);
        specificStepDao.closeCurrentSessionwithTransaction();
    }
    
    public List<SpecificStep> findAll(){
        specificStepDao.openCurrentSession();
        List<SpecificStep> steps = specificStepDao.findAll();
        specificStepDao.closeCurrentSession();
        return steps;
    }
    
    public void deleteAll(){
        specificStepDao.openCurrentSessionWithTransaction();
        specificStepDao.deleteAll();
        specificStepDao.closeCurrentSessionwithTransaction();
    }
    
    public SpecificStepDao userDao(){
        return specificStepDao;
    }
    
     public void saveOrUpdate(SpecificStep entity){
        specificStepDao.openCurrentSessionWithTransaction();
        specificStepDao.saveOrUpdate(entity);
        specificStepDao.closeCurrentSessionwithTransaction();
    }

    public void delete(int stepId, int testSetId) {
        specificStepDao.openCurrentSessionWithTransaction();
        SpecificStep step = specificStepDao.findById(new SpecificStepPK(stepId, testSetId));
        System.out.println("Specific step: "+step);
        specificStepDao.delete(step);
        specificStepDao.closeCurrentSessionwithTransaction();
    }

    public void deleteTemp(int stepId, int testId) {
        specificStepDao.openCurrentSessionWithTransaction();
        String sql = "DELETE FROM specificstep where id='" + stepId + "' and test_set_id='" + testId + "'";
        specificStepDao.getCurrentSession().createSQLQuery(sql).executeUpdate();
        specificStepDao.closeCurrentSessionwithTransaction();

    }
    
    
}
