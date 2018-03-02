/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author ZNIKIC
 */
@Named(value = "mbRedirect")
@RequestScoped
public class MBRedirect {

    /**
     * Creates a new instance of MBRedirect
     */
    public MBRedirect() {
    }

    public String forwardToLoginIfNotLoggedIn(ComponentSystemEvent cse) {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("currentUser")
                && !viewId.startsWith("/index")
                && !viewId.startsWith("/registrationPage")
                && !viewId.startsWith("/changePasswordPage")) {
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
                    FacesContext.getCurrentInstance(),
                    null,
                    "/index?faces-redirect=true");
        }
        return "";
    }

}
