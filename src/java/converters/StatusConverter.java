/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import controller.Controller;
import entities.CompletionStatus;
import entities.Type;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ZXNIKIC
 */
@Named
@RequestScoped
@FacesConverter(value = "statusConverter")
public class StatusConverter implements Converter{
    @Inject
    Controller cont;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        cont = new Controller();
        return cont.getStatusByName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof CompletionStatus) {
            CompletionStatus cs = (CompletionStatus) value;
            return cs.getName();
        }
        return "";
    }
}
