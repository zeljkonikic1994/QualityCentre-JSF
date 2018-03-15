/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.UserDao;
import entities.Type;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class TypeService {

    private static TypeDao typeDao;

    public TypeService() {
        typeDao = new TypeDao();
    }

    public void save(Type entity) {
        typeDao.openCurrentSessionWithTransaction();
        typeDao.save(entity);
        typeDao.closeCurrentSessionwithTransaction();
    }

    public void update(Type entity) {
        typeDao.openCurrentSessionWithTransaction();
        typeDao.update(entity);
        typeDao.closeCurrentSessionwithTransaction();
    }

    public Type findByName(String name) {
        typeDao.openCurrentSession();
        Type type = typeDao.findByName(name);
        typeDao.closeCurrentSession();
        return type;
    }

    public Type findById(int id) {
        typeDao.openCurrentSession();
        Type type = typeDao.findById(id);
        typeDao.closeCurrentSession();
        return type;
    }

    public void delete(int id) {
        typeDao.openCurrentSessionWithTransaction();
        Type type = typeDao.findById(id);
        typeDao.delete(type);
        typeDao.closeCurrentSessionwithTransaction();
    }

    public List<Type> findAll() {
        typeDao.openCurrentSession();
        List<Type> types = typeDao.findAll();
        typeDao.closeCurrentSession();
        return types;
    }

    public void deleteAll() {
        typeDao.openCurrentSessionWithTransaction();
        typeDao.deleteAll();
        typeDao.closeCurrentSessionwithTransaction();
    }

    public TypeDao typeDao() {
        return typeDao;
    }

}
