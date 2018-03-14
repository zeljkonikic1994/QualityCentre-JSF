/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zeljk
 */
@Named(value = "mbLogOut")
@RequestScoped
public class MBLogOut {

    /**
     * Creates a new instance of MBLogOut
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            session.removeAttribute("currentUser");
            context.getExternalContext().invalidateSession();
        }
        return "index.xhtml?faces-redirect=true";
    }

}
