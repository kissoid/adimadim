/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.adimadim.db.entity.Account;
import org.adimadim.service.AccountService;
import org.adimadim.service.exception.AccountException;
import org.adimadim.util.EmailUtil;
import org.adimadim.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SessionScoped
@Named(value = "loginBean")
public class LoginBean implements Serializable {

    private String targetURI;
    @Inject
    private AccountService accountService;
    @Inject
    private AccountBean accountBean;
    private Account account = new Account();

    public LoginBean() {
    }

    public void startLogInOperation() {
        try {
            account = accountService.login(account);
            accountBean.setAccount(account);
            if(account.getActive().equals("H")){
                throw new AccountException("Hesabınız henüz aktifleştirilmediği için göğüs numarası alamazsınız.");
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect(targetURI);
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage("Hata", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void sendPassword() {
        try {
            Account tempAccount = accountService.findAccountByEmail(account.getEmail());
            if (tempAccount == null) {
                throw new AccountException("Email adresi bulunamadı.");
            }
            String name = (tempAccount.getName() + " " + tempAccount.getSurname());
            String content;
            if (tempAccount.getPassword() != null && !tempAccount.getPassword().trim().equals("")) {
                content = "Merhaba Sayin " + name + "<br/>Sifreniz : " + tempAccount.getPassword();
            } else {
                content = "Merhaba Sayin " + name + "<br/>Şifre tanımlamadığınız anlaşılmıştır. Asagidaki linki tiklayarak kayit bilgilerinizi güncelleyin ve sifrenizi belirleyiniz.<br/>";
                content += "<a href='http://www.aakosu.org/outsession/dagi/join.jsf'>http://www.aakosu.org/outsession/dagi/join.jsf</a>";
            }
            String receiver = account.getEmail();
            String subject = "Sifre hatirlatma";
            EmailUtil.sendMail(EmailUtil.SENDER_INFO, receiver, subject, content);
            FacesMessageUtil.createFacesMessage("Bilgi", "Şifreniz e-mail adresinize gönderildi", FacesMessage.SEVERITY_INFO);
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage("Hata", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Hata", "Beklenmedik bir hata oluştu", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void startLogOutOperation() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.jsf");
        } catch (IOException ex) {
            Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
