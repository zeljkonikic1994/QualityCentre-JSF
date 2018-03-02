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
@Named(value = "mbLogin")
@RequestScoped
public class MBLogIn {

    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        username = "admin";
        password = "Admin123#";
        
        if (!checkIfAllFieldsAreEntered()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must enter all fields.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "";
        }

        User user = getUserWithSpecifiedUserName(username);

        if (user == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User with that username does not exists", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "";
        } else {
            if (checkPassword(user.getPassword(), password)) {
                if (checkIfUserIsApproved(user)) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", user);
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "home.xhtml?faces-redirect=true");
//                    try {
//                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", user);
//
////                        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "home.xhtml?faces-redirect=true");
//                        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/home.xhtml");
//                        FacesContext.getCurrentInstance().responseComplete();
//                    } catch (IOException ex) {
//                        Logger.getLogger(MBLogIn.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    return "";
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is still not approved.", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return "";
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong password.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "";
            }

        }
    }

    private boolean checkIfAllFieldsAreEntered() {
        if (username == null || username.equals("") || password == null || password.equals("")) {
            return false;
        } else {
            return true;
        }
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

    private boolean checkIfUserIsApproved(User user) {
        if (user.getRequestApproved() == 1) {
            return true;
        } else {
            return false;
        }
    }

}
