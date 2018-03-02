/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import controller.Controller;
import db.HibernateUtil;
import entities.Type;
import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author zeljk
 */
@Named(value = "mbAdmin")
@ViewScoped
public class MBAdmin implements Serializable {

    @Inject
    Controller cont;

    List<User> unregistered;
    List<User> registered;
    List<Type> types;

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<User> getUnregistered() {
        return unregistered;
    }

    public void setUnregistered(List<User> unregistered) {
        this.unregistered = unregistered;
    }

    public List<User> getRegistered() {
        return registered;
    }

    public void setRegistered(List<User> registered) {
        this.registered = registered;
    }

    /**
     * Creates a new instance of MBAdmin
     */
    @PostConstruct
    public void initPage() {
        cont = new Controller();
        
        types = cont.loadTypes();
        loadUsers();
    }

    public void approve(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        user.setRequestApproved(1);
        session.update(user);
        session.getTransaction().commit();
        session.flush();
        session.close();

        loadUsers();

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User " + user.getUserName() + " is approved.", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void reject(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(user);
        session.getTransaction().commit();

        session.flush();
        session.close();

        loadUsers();

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User " + user.getUserName() + " is rejected", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void delete(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(user);
        session.getTransaction().commit();
        session.flush();
        session.close();

        loadUsers();

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User " + user.getUserName() + " is deleted", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    private void separateUsers(List<User> users) {
        unregistered = new ArrayList<>();
        registered = new ArrayList<>();
        for (User user : users) {
            if (user.getRequestApproved() == 0) {
                unregistered.add(user);
            } else {
                registered.add(user);
            }
        }
    }

    private void loadUsers() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(User.class);
        List<User> users = (List<User>) query.list();
        session.getTransaction().commit();

        separateUsers(users);

        session.flush();
        session.close();
    }

    public void onRowEdit(RowEditEvent event) {
        User u = (User) event.getObject();
        updateUser(u);
        FacesMessage msg = new FacesMessage("User Edited", (u.getUserName()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((User) event.getObject()).getUserName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void updateUser(User u) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(u);
        session.getTransaction().commit();
        session.flush();
        session.close();

        loadUsers();
    }

}
