/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.validator;

import org.adimadim.db.entity.Account;
import org.adimadim.service.exception.AccountException;



/**
 *
 * @author Adem
 */
public class AccountValidator extends AbstractValidator{

    public synchronized static void validateAccountForSignIn(Account account) throws AccountException {
        if (isStringEmpty(account.getEmail())) {
            throw new AccountException("Email boş olamaz.");
        }
        if (isStringEmpty(account.getPassword())) {
            throw new AccountException("Şifre boş olamaz.");
        }
    }

    public synchronized static void validateAccountForSignUp(Account account) throws AccountException {
        if (account.getEmail().equals("")) {
            throw new AccountException("Email boş olamaz.");
        }
        if (account.getName().equals("")) {
            throw new AccountException("İsim boş olamaz.");
        }
        if (account.getSurname().equals("")) {
            throw new AccountException("Soyisim boş olamaz.");
        }
    }

}
