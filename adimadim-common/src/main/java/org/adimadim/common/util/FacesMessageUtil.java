/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.common.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Adem
 */
public class FacesMessageUtil {
 
    public static void createFacesMessage(String message, String detail, FacesMessage.Severity severity) {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary(message);
        if (detail != null) {
            facesMessage.setDetail(detail);
        }
        facesMessage.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    public static void clearFacesMessages() {
        FacesContext.getCurrentInstance().getMessageList().clear();
    }
    
}
