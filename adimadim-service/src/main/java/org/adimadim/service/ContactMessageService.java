/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.adimadim.db.entity.ContactMessage;
import org.adimadim.facade.ContactMessageFacade;
import org.adimadim.service.exception.ContactMessageException;

/**
 *
 * @author Adem
 */
@Stateless
public class ContactMessageService {
    
    @Inject 
    private ContactMessageFacade contactMessageFacade;
    
    public ContactMessageService(){
        
    }
    
    public void saveMessage(ContactMessage message) throws ContactMessageException, Exception{
        contactMessageFacade.save(message);
        //new MessageCreated().start();
    }
    
    public List<ContactMessage> getUnreadMessages() throws Exception{
        Map map = new HashMap();
        map.put("unread", "E");
        return contactMessageFacade.findRangeByNamedQuery(new int[]{0,50}, "ContactMessage.findUnreadMessages", map);
    }
    
    public List<ContactMessage> getReadMessages() throws Exception{
        Map map = new HashMap();
        map.put("unread", "H");
        return contactMessageFacade.findRangeByNamedQuery(new int[]{0,50}, "ContactMessage.findReadMessages", map);
    }
    
    public void markMessageAsRead(Integer messageId) throws Exception{
        ContactMessage message = contactMessageFacade.find(messageId);
        message.setUnread("H");
        contactMessageFacade.update(message);
    }
    
}
