/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.AccountProperty;
import org.adimadim.db.entity.AccountPropertyPK;
import org.adimadim.service.exception.AccountException;
import org.adimadim.service.AccountService;
import org.adimadim.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value="accountBean")
public class AccountBean implements Serializable{
    @Inject private AccountService accountService;
    private Account profileAccount = new Account();
    private Map accountPropertyMap = new HashMap();
    private Account account = new Account();
    
    public AccountBean() {
    }

    public void changeProfileAccount(Account profileAccount){
        this.profileAccount = profileAccount;
        try{
            List<AccountProperty> accountPropertyList = accountService.findAccountPropertiesByAccountId(profileAccount.getAccountId());
            listToPropertyMap(accountPropertyList);
        }catch(Exception ex){
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
            changeProfileAccount(account);
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/insession/main/mainPage.jsf");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ChestNumberServlet");
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);    
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu", null, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void updateAccount(){
        try {
            accountService.saveAccount(account);
            account = accountService.findAccount(account.getAccountId());
            FacesMessageUtil.createFacesMessage("Hesap bilgileriniz değiştirildi", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu", null, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void updatePassword(){
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
    
    public void saveReloadAccountAndProperties(){
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
    
    public void startSignOutOperation() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.jsf");
        } catch (IOException ex) {
            Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listToPropertyMap(List<AccountProperty> accountPropertyList){
        accountPropertyMap.clear();
        for(AccountProperty accountProperty: accountPropertyList){
            accountPropertyMap.put((long)accountProperty.getAccountPropertyPK().getPropertyId() , accountProperty.getPropertyValue());
        }
    }
    
    private List<AccountProperty> propertyMapToList() {
        List<AccountProperty> accountPropertyList = new ArrayList<AccountProperty>();
        Iterator iterator = accountPropertyMap.entrySet().iterator();
        while (iterator.hasNext()) {
            AccountProperty accountProperty = new AccountProperty();
            accountProperty.setAccountPropertyPK(new AccountPropertyPK());
            Map.Entry entry = (Map.Entry) iterator.next();
            accountProperty.getAccountPropertyPK().setAccountId(account.getAccountId());
            accountProperty.getAccountPropertyPK().setPropertyId(Integer.parseInt(entry.getKey().toString()));
            accountProperty.setPropertyValue(entry.getValue().toString());
            accountPropertyList.add(accountProperty);
        }
        return accountPropertyList;
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
