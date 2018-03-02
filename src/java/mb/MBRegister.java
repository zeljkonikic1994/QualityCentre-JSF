/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import db.HibernateUtil;
import entities.Type;
import entities.User;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
@Named(value = "mbRegister")
@RequestScoped
public class MBRegister {

    String name, lastName, username, password, gender, mail, confirmedPassword;
    Type type;
    List<Type> types;
    
    @PostConstruct
    public void init(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(Type.class);
        query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        types = (List<Type>) query.list();
        session.getTransaction().commit();

        session.flush();
        session.close();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    

    public String register() {
        if (!checkIfAllFieldsAreEntered()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must enter all fields.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "";
        }

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Criteria query = session.createCriteria(User.class);
        User user = (User) query.add(Restrictions.eq("userName", username)).uniqueResult();
        session.getTransaction().commit();

        if (user != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is taken.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "";
        } else {
            if (checkIfPasswordsMatch()) {
                session.beginTransaction();
                addNewUser(session);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User added.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "";
            }
        }

    }

    private boolean checkIfAllFieldsAreEntered() {
        if (username == null || username.equals("") || password == null || password.equals("")
                || name == null || name.equals("") || lastName == null || lastName.equals("")
                || gender == null || gender.equals("") || mail == null
                || mail.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkIfPasswordsMatch() {
        if (password.equals(confirmedPassword)) {
            return true;
        } else {
            return false;
        }
    }
    private void addNewUser(Session session) {
        User newUser = new User();
        newUser.setGender(gender.charAt(0)+"");
        newUser.setLastName(lastName);
        newUser.setMail(mail);
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setRequestApproved(0);
        newUser.setType(type);
        newUser.setUserName(username);

        session.persist(newUser);
        session.getTransaction().commit();

        session.flush();
        session.close();
    }

}
