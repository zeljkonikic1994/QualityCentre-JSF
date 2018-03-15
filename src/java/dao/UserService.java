/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.User;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class UserService {
    private static UserDao userDao;
    
    public UserService(){
        userDao = new UserDao();
    }
    
    public void save(User entity){
        userDao.openCurrentSessionWithTransaction();
        userDao.save(entity);
        userDao.closeCurrentSessionwithTransaction();
    }
    
    public void update(User entity){
        userDao.openCurrentSessionWithTransaction();
        userDao.update(entity);
        userDao.closeCurrentSessionwithTransaction();
    }
    
    public User findById(String id){
        userDao.openCurrentSession();
        User user = userDao.findById(id);
        userDao.closeCurrentSession();
        return user;
    }
    
    public void delete(String id){
        userDao.openCurrentSessionWithTransaction();
        User user = userDao.findById(id);
        userDao.delete(user);
        userDao.closeCurrentSessionwithTransaction();
    }
    
    public List<User> findAll(){
        userDao.openCurrentSession();
        List<User> users = userDao.findAll();
        userDao.closeCurrentSession();
        return users;
    }
    
    public void deleteAll(){
        userDao.openCurrentSessionWithTransaction();
        userDao.deleteAll();
        userDao.closeCurrentSessionwithTransaction();
    }
    
    public UserDao userDao(){
        return userDao;
    }
    
    public void saveOrUpdate(User entity){
        userDao.openCurrentSessionWithTransaction();
        userDao.saveOrUpdate(entity);
        userDao.closeCurrentSessionwithTransaction();
    }
}
