/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.validator;

import java.util.ResourceBundle;
import org.adimadim.db.entity.Account;
import org.adimadim.service.exception.AccountException;

/**
 *
 * @author Adem
 */
public class RegisterBeanValidator extends AbstractValidator {

    public synchronized static void validateAccountForSignIn(Account account) throws AccountException {
        if (isStringEmpty(account.getEmail())) {
            throw new AccountException("Email boş olamaz.");
        }
        if (isStringEmpty(account.getPassword())) {
            throw new AccountException("Şifre boş olamaz.");
        }
    }

    public synchronized static void validateAccountForSignUp(Account account) throws AccountException {
        if (isStringEmpty(account.getEmail())) {
            throw new AccountException("E-mail boş olamaz.");
        }
        if (!account.getEmail().equals(account.getReEmail())) {
            throw new AccountException("Girdiğiniz e-mail adresleri birbirinden farklı.");
        }
        if (isStringEmpty(account.getUserName())) {
            throw new AccountException("Kullanıcı adı boş olamaz.");
        }
        if(!account.getUserName().equals(account.getReUserName())){
            throw new AccountException("Kullanıcı adı ve kullanıcı adı tekrar birbirinden farklı olamaz.");
        }
        if (isStringEmpty(account.getPassword())) {
            throw new AccountException("Şifre boş olamaz.");
        }
        if (!account.getPassword().equals(account.getRePassword())) {
            throw new AccountException("Girdiğiniz şifreler birbirinden farklı.");
        }
        if (account.getTempBirthDate() == null) {
            throw new AccountException(ResourceBundle.getBundle("org.adimadim.bean/i18n/messages").getString("registerBean.birthDateMessage"));
        }
        if (isStringEmpty(account.getName())) {
            throw new AccountException("İsim boş olamaz.");
        }
        if (isStringEmpty(account.getSurname())) {
            throw new AccountException("Soyisim boş olamaz.");
        }
    }
}
