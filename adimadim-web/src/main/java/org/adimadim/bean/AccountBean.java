/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.AccountProperty;
import org.adimadim.service.AccountService;
import org.adimadim.service.exception.AccountException;
import org.adimadim.common.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@Data
@EqualsAndHashCode(callSuper = false)
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

}
