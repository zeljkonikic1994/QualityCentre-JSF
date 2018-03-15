/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Step;
import entities.StepPK;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class StepService {

    private static StepDao stepDao;

    public StepService() {
        stepDao = new StepDao();
    }

    public void save(Step entity) {
        stepDao.openCurrentSessionWithTransaction();
        stepDao.save(entity);
        stepDao.closeCurrentSessionwithTransaction();
    }

    public void update(Step entity) {
        stepDao.openCurrentSessionWithTransaction();
        stepDao.update(entity);
        stepDao.closeCurrentSessionwithTransaction();
    }

    public Step findById(StepPK id) {
        stepDao.openCurrentSession();
        Step step = stepDao.findById(id);
        stepDao.closeCurrentSession();
        return step;
    }

    public void delete(StepPK id) {
        stepDao.openCurrentSessionWithTransaction();
        Step step = stepDao.findById(id);
        System.out.println("step " + step);
        stepDao.delete(step);
        stepDao.closeCurrentSessionwithTransaction();
    }

    public void delete(int stepId, int testId) {
        stepDao.openCurrentSessionWithTransaction();
        Step step = stepDao.findById(new StepPK(stepId, testId));
        System.out.println("step " + step);
        stepDao.delete(step);
        stepDao.closeCurrentSessionwithTransaction();
    }

    public List<Step> findAll() {
        stepDao.openCurrentSession();
        List<Step> users = stepDao.findAll();
        stepDao.closeCurrentSession();
        return users;
    }

    public void deleteAll() {
        stepDao.openCurrentSessionWithTransaction();
        stepDao.deleteAll();
        stepDao.closeCurrentSessionwithTransaction();
    }

    public StepDao userDao() {
        return stepDao;
    }

    public void saveOrUpdate(Step step) {
        stepDao.openCurrentSessionWithTransaction();
        stepDao.saveOrUpdate(step);
        stepDao.closeCurrentSessionwithTransaction();
    }

}
