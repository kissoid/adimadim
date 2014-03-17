/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.adimadim.db.entity.ContactMessage;
import org.adimadim.service.exception.ContactMessageException;
import org.adimadim.service.ContactMessageService;
import org.adimadim.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@Named(value="contactUsBean")
@ConversationScoped
public class ContactUsBean implements Serializable{
    
    @Inject 
    private ContactMessageService contactMessageService;
    private ContactMessage contactMessage = new ContactMessage();
    @Inject
    private AccountBean accountBean;
    
    
    public ContactUsBean(){
        
    }

    public ContactMessage getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(ContactMessage contactMessage) {
        this.contactMessage = contactMessage;
    }

    public void saveMessage(){
        try {
            contactMessage.setAccountId(accountBean.getAccount().getAccountId());
            contactMessage.setCreateDate(new Date());
            contactMessage.setUnread("E");
            contactMessageService.saveMessage(contactMessage);
            contactMessage = new ContactMessage();
            FacesMessageUtil.createFacesMessage("Mesajınız kaydedilmiştir. En kısa zamanda dönüş yapılacaktır.", null, FacesMessage.SEVERITY_INFO);
        } catch (ContactMessageException ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }
    
}
