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
public interface UserDao extends Dao<User, Integer>{
    
    public List<User> findByFirstName(String firstName);
}
