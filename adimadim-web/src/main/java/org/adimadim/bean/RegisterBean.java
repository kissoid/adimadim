/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import org.adimadim.bean.temp.AccountUpdateBean;
import org.adimadim.bean.validator.RegisterBeanValidator;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.AccountAlbum;
import org.adimadim.db.entity.AccountProperty;
import org.adimadim.service.AccountService;
import org.adimadim.service.exception.AccountException;
import org.adimadim.util.ConvertionUtil;
import org.adimadim.util.EmailUtil;
import org.adimadim.util.FacesMessageUtil;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Adem
 */
@Named(value = "registerBean")
@SessionScoped
public class RegisterBean implements Serializable {

    @Inject
    private AccountService accountService;
    @Inject
    private AccountBean accountBean;
    private boolean riskAccepted = false;
    private boolean raceRulesAccepted = false;
    private Account account = new Account();
    private Map accountProperties = new HashMap();
    private boolean olderThanEighteen = true;
    private List<AccountAlbum> accountAlbumList = new ArrayList<>();
    @Inject
    private AccountUpdateBean accountUpdateBean;

    public RegisterBean() {
        AccountAlbum accountAlbum = new AccountAlbum();
        accountAlbum.setAlbumName("Profile Resimleri");
        accountAlbum.setAlbumDate(new Date());
        accountAlbum.setProfileAlbum("E");
        accountAlbumList.add(accountAlbum);
    }

    private void listToPropertyMap(Account account) {
        accountProperties.clear();
        if (account.getAccountPropertyList() == null) {
            return;
        }
        for (AccountProperty accountProperty : account.getAccountPropertyList()) {
            accountProperties.put(accountProperty.getPropertyId(), accountProperty.getPropertyValue());
        }
    }

    public void checkEmail(AjaxBehaviorEvent event) {
        try {
            if (account.getEmail() == null || account.getEmail().trim().equals("")) {
                return;
            }
            if (!account.getEmail().equals(account.getReEmail())) {
                FacesMessageUtil.createFacesMessage("Uyarı", "Girilen mailler birbirinden farklı", FacesMessage.SEVERITY_ERROR);
                return;
            }
            Account tempAccount = accountService.findAccountByEmail(account.getEmail());
            if (tempAccount == null) {
                return;
            }

            if (tempAccount.getPassword() == null || tempAccount.getPassword().trim().equals("")) {
                tempAccount.setReEmail(account.getReEmail());
                account = tempAccount;
                listToPropertyMap(account);
                if (account.getBirthDate() != null) {
                    account.setTempBirthDate(ConvertionUtil.dateToString(account.getBirthDate(), "dd.MM.yyyy"));
                }
                RequestContext.getCurrentInstance().update("mainForm");
                RequestContext.getCurrentInstance().execute("completeRegistrationDialog.show();");
            } else {
                RequestContext.getCurrentInstance().update("mainForm");
                RequestContext.getCurrentInstance().execute("alreadyRegisteredDialog.show();");
                account = new Account();
            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isOlderThanEighteen() {
        try {
            if (account.getTempBirthDate() == null) {
                olderThanEighteen = true;
                return olderThanEighteen;
            }
            long age = ConvertionUtil.yearDifference(ConvertionUtil.stringToDate(account.getTempBirthDate(), "dd.MM.yyyy"), new Date());
            olderThanEighteen = (age >= 18);
        } catch (ParseException ex) {
            FacesMessageUtil.createFacesMessage("Lütfen girdiğiniz doğum tarihini kontrol ediniz", null, FacesMessage.SEVERITY_ERROR);
        }
        return olderThanEighteen;
    }

    public void startSignUpOperation() {
        boolean registerCompleted = false;
        try {
            if (riskAccepted == false) {
                throw new AccountException(ResourceBundle.getBundle("org.adimadim.bean/i18n/messages").getString("registerBean.raceRulesAcceptMessage"));
            }
            if (raceRulesAccepted == false) {
                throw new AccountException(ResourceBundle.getBundle("org.adimadim.bean/i18n/messages").getString("registerBean.riskAcceptMessage"));
            }
            RegisterBeanValidator.validateAccountForSignUp(account);
            Account tempAccount = accountService.findAccountByEmail(account.getEmail());
            if (tempAccount != null && account.getAccountId() == null) {
                String message = ResourceBundle.getBundle("org.adimadim.bean/i18n/messages").getString("registerBean.emailAlreadyExists");
                throw new AccountException(message);
            }
            prepareAccount();
            accountService.signUp(account);
            registerCompleted = true;
            //accountBean.setAccount(account);
            sendActivationLink(account);
            FacesMessageUtil.createFacesMessage("Bilgi", "Kayıt işleminiz tamamlanmıştır", FacesMessage.SEVERITY_INFO);
            FacesMessageUtil.createFacesMessage("Bilgi", "Aktivasyon linkiniz mail adresinize gönderilmiştir.", FacesMessage.SEVERITY_INFO);
            FacesMessageUtil.createFacesMessage("Bilgi", "Lütfen spam klasörünü de kontrol etmeyi unutmayınız.", FacesMessage.SEVERITY_INFO);
            account = new Account();
            //sendChestNumber(account);
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/ChestNumberServlet");
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            String message = ResourceBundle.getBundle("org.adimadim.bean/i18n/messages").getString("registerBean.accountCouldNotCreateMessage");
            if (registerCompleted) {
                message = ResourceBundle.getBundle("org.adimadim.bean/i18n/messages").getString("registerBean.mailCouldNotSentMessage");
            }
            FacesMessageUtil.createFacesMessage(message, null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void prepareAccount() throws Exception {
        account.setSecretKey(UUID.randomUUID().toString());
        account.setName(ConvertionUtil.firstCharUpperCase(account.getName()));
        account.setSurname(ConvertionUtil.firstCharUpperCase(account.getSurname()));
        account.setBirthDate(ConvertionUtil.stringToDate(account.getTempBirthDate(), ResourceBundle.getBundle("org.adimadim.bean/i18n/messages").getString("registerBean.dateFormat")));
        account.setAccountPropertyList(propertyMapToList());
        if (account.getAccountAlbumList() == null) {
            account.setAccountAlbumList(accountAlbumList);
        }
    }

    private void sendActivationLink(Account account) throws MessagingException {
        String receiver = account.getEmail();
        String subject = "Adimadim kosu aktivasyon linki";
        String link = "http://www.aakosu.org/ActivationServlet?key=" + account.getSecretKey();
        String content = "Kaydınızı aktive etmek için lütfen aşağıdaki linke tklayınız<br/>";
        content += "<a href='" + link + "' >" + link + "</a>";
        EmailUtil.sendMail(EmailUtil.SENDER_INFO, receiver, subject, content);
    }

    private List<AccountProperty> propertyMapToList() {
        List<AccountProperty> accountPropertyList = new ArrayList<AccountProperty>();
        Iterator iterator = accountProperties.entrySet().iterator();
        while (iterator.hasNext()) {
            AccountProperty accountProperty = new AccountProperty();
            Map.Entry entry = (Map.Entry) iterator.next();
            accountProperty.setPropertyId(Integer.parseInt(entry.getKey().toString()));
            accountProperty.setPropertyValue(entry.getValue().toString());
            accountPropertyList.add(accountProperty);
        }
        return accountPropertyList;
    }

    public List<AccountAlbum> getAccountAlbumList() {
        return accountAlbumList;
    }

    public void setAccountAlbumList(List<AccountAlbum> accountAlbumList) {
        this.accountAlbumList = accountAlbumList;
    }

    public void setOlderThanEighteen(boolean olderThanEighteen) {
        this.olderThanEighteen = olderThanEighteen;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isRiskAccepted() {
        return riskAccepted;
    }

    public void setRiskAccepted(boolean riskAccepted) {
        this.riskAccepted = riskAccepted;
    }

    public Map getAccountProperties() {
        return accountProperties;
    }

    public void setAccountProperties(Map accountProperties) {
        this.accountProperties = accountProperties;
    }

    public boolean isRaceRulesAccepted() {
        return raceRulesAccepted;
    }

    public void setRaceRulesAccepted(boolean raceRulesAccepted) {
        this.raceRulesAccepted = raceRulesAccepted;
    }

}
