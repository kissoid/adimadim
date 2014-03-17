/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.validator;

import org.adimadim.db.entity.ContactMessage;
import org.adimadim.service.exception.ContactMessageException;
import org.adimadim.service.exception.RaceException;

/**
 *
 * @author Adem
 */
public class ContactMessageValidator extends AbstractValidator{
    
    public synchronized static void validateContactForCreation(ContactMessage message) throws RaceException, ContactMessageException {
        if(isStringEmpty(message.getTitle())){
            throw new ContactMessageException("Mesaj başlığı boş olamaz.");
        }
        if(isStringEmpty(message.getMessage())){
            throw new ContactMessageException("Mesaj konusu boş olamaz.");
        }
    }
    
}
