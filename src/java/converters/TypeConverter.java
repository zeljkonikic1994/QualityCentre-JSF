/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import controller.Controller;
import entities.Type;
import java.lang.annotation.Annotation;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 *
 * @author zeljk
 */
@Named
@RequestScoped
@FacesConverter(value = "typeConverter")
public class TypeConverter implements Converter {

    @Inject
    Controller cont;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        cont = new Controller();
        return cont.getTypeByName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Type) {
            Type t = (Type) value;
            return t.toString();
        }
        return "";
    }

}
