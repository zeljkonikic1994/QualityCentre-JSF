/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import controller.Controller;
import entities.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
/**
 *
 * @author zeljk
 */
@Named(value = "mbChangePassword")
@RequestScoped
public class MBChangePassword {

    @Inject
    Controller controller;

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
            User user = controller.getUserWithSpecifiedUserName(username);

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

    private boolean checkPassword(String userPassword, String enteredPassword) {
        if (userPassword.equals(enteredPassword)) {
            return true;
        } else {
            return false;
        }
    }

    private void changePasswordInDB(User user) {
        user.setPassword(newPassword);
        controller.updateUser(user);
    }

}
