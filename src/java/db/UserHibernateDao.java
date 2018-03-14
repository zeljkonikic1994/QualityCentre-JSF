/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.User;
import java.util.List;

/**
 *
 * @author ZXNIKIC
 */
public class UserHibernateDao extends AbstractDao<User, Integer> implements UserDao{

    @Override
    public List<User> findByFirstName(String firstName) {
        //add implementation
        return null;
    }
    
}
