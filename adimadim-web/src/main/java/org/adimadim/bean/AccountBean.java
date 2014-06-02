/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.AccountProperty;
import org.adimadim.service.exception.AccountException;
import org.adimadim.service.AccountService;
import org.adimadim.util.ChestNumberUtil;
import org.adimadim.util.EmailUtil;
import org.adimadim.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value = "accountBean")
public class AccountBean implements Serializable {

    @Inject
    private AccountService accountService;
    private Account profileAccount = new Account();
    private Map accountPropertyMap = new HashMap();
    private Account account = new Account();

    public AccountBean() {
    }

    public void changeProfileAccount(Account profileAccount) {
        this.profileAccount = profileAccount;
        try {
            List<AccountProperty> accountPropertyList = accountService.findAccountPropertiesByAccountId(profileAccount.getAccountId());
            listToPropertyMap(accountPropertyList);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public boolean isUserSignedIn() {
        return account.getAccountId() != null;
    }

    public boolean isManagerUser() {
        if (isUserSignedIn() == false) {
            return false;
        }
        if (account.getManager() == null) {
            return false;
        }
        return account.getManager().equals("E");
    }

    public void startSignInOperation() {
        try {
            account = accountService.signIn(account);
            if(account.getActive().equals("H")){
                throw new AccountException("Hesabınız henüz aktifleştirilmediği için göğüs numarası alamazsınız.");
            }
            changeProfileAccount(account);
            sendChestNumber(account);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ChestNumberServlet");
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage("Hata", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void updateAccount() {
        try {
            accountService.saveAccount(account);
            account = accountService.findAccount(account.getAccountId());
            FacesMessageUtil.createFacesMessage("Hesap bilgileriniz değiştirildi", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void updatePassword() {
        try {
            if (!account.getPassword().equals(account.getRePassword())) {
                throw new AccountException("İki şifre birbirinden farklı olamaz.");
            }
            accountService.saveAccount(account);
            account = accountService.findAccount(account.getAccountId());
            FacesMessageUtil.createFacesMessage("Şifreniz değiştirildi", null, FacesMessage.SEVERITY_INFO);
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void sendChestNumber(Account account) throws DocumentException, BadElementException, IOException, MessagingException {
        Integer chestNumber = account.getChestNumber();
        String name = (account.getName() + " " + account.getSurname());
        String receiver = account.getEmail();
        String subject = "Gogus Numarasi";
        String content = "Gogus numaraniz PDF dosyasi olarak ektedir.";
        String fileName = "GogusNo.pdf";
        String fileFormat = "application/pdf";
        ByteArrayOutputStream file = ChestNumberUtil.createChestNumberDocument(chestNumber, name);
        EmailUtil.sendMailWithAttachment(EmailUtil.SENDER_INFO, receiver, subject, content, fileName, fileFormat, file.toByteArray());
    }
    
    public void saveReloadAccountAndProperties() {
        try {
            accountService.saveAccountProperties(propertyMapToList());
            accountService.saveAccount(profileAccount);
            account = accountService.findAccount(account.getAccountId());
            changeProfileAccount(account);
            FacesMessageUtil.createFacesMessage("Değişilik kayıt edildi.", null, FacesMessage.SEVERITY_INFO);
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
            if (tempAccount.getEmail() == null) {
                throw new AccountException("Henüz şifre oluşturmamışsınız.");
            }
            String name = (tempAccount.getName() + " " + tempAccount.getSurname());
            String receiver = account.getEmail();
            String subject = "Sifre hatirlatma";
            String content = "Sayin " + name + ", sifreniz : " + tempAccount.getPassword();
            EmailUtil.sendMail(EmailUtil.SENDER_INFO, receiver, subject, content);
            FacesMessageUtil.createFacesMessage("Bilgi", "Şifreniz e-mail adresinize gönderildi", FacesMessage.SEVERITY_INFO);
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage("Hata", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Hata", "Beklenmedik bir hata oluştu", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void startSignOutOperation() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.jsf");
        } catch (IOException ex) {
            Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listToPropertyMap(List<AccountProperty> accountPropertyList) {
        accountPropertyMap.clear();
        for (AccountProperty accountProperty : accountPropertyList) {
            accountPropertyMap.put((long) accountProperty.getPropertyId(), accountProperty.getPropertyValue());
        }
    }

    private List<AccountProperty> propertyMapToList() {
        List<AccountProperty> accountPropertyList = new ArrayList<>();
        Iterator iterator = accountPropertyMap.entrySet().iterator();
        while (iterator.hasNext()) {
            AccountProperty accountProperty = new AccountProperty();
            Map.Entry entry = (Map.Entry) iterator.next();
            accountProperty.setAccount(account);
            accountProperty.setPropertyId(Integer.parseInt(entry.getKey().toString()));
            accountProperty.setPropertyValue(entry.getValue().toString());
            accountPropertyList.add(accountProperty);
        }
        return accountPropertyList;
    }

    public void changeLanguage(String language, String country) {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language, country));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getProfileAccount() {
        return profileAccount;
    }

    public void setProfileAccount(Account profileAccount) {
        this.profileAccount = profileAccount;
    }

    public Map getAccountPropertyMap() {
        return accountPropertyMap;
    }

    public void setAccountPropertyMap(Map accountPropertyMap) {
        this.accountPropertyMap = accountPropertyMap;
    }

}
