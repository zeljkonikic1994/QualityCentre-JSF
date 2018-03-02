/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import db.HibernateUtil;
import entities.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author zeljk
 */
@Named(value = "mbChangePassword")
@RequestScoped
public class MBChangePassword {

    String username, password, newPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String changePassword() {
        if (checkIfAllFieldsAreEntered()) {
            User user = getUserWithSpecifiedUserName(username);
            if (user == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User with that user name does not exists.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "";
            } else {
                if (checkPassword(user.getPassword(), password)) {
                    changePasswordInDB(user);
                    try {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password succesfully changed.", null);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                    } catch (IOException ex) {
                        Logger.getLogger(MBChangePassword.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FacesContext.getCurrentInstance().responseComplete();
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong password.", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return "";
                }
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must enter all fields.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "";
        }
        return "";
    }

    private boolean checkIfAllFieldsAreEntered() {
        if (username == null || username.equals("") || password == null || password.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * Creates a new instance of MBChangePassword
     */
    public MBChangePassword() {
    }

    private User getUserWithSpecifiedUserName(String username) {
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

    private boolean checkPassword(String userPassword, String enteredPassword) {
        if (userPassword.equals(enteredPassword)) {
            return true;
        } else {
            return false;
        }
    }

    private void changePasswordInDB(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Criteria query = session.createCriteria(User.class);
        user.setPassword(newPassword);

        session.update(user);
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

}
