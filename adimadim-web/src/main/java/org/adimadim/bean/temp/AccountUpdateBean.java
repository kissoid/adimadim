/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.temp;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.servlet.http.HttpServletRequest;
import org.adimadim.bean.AccountBean;
import org.adimadim.bean.validator.RegisterBeanValidator;
import org.adimadim.common.util.BipNumberUtil;
import org.adimadim.common.util.ConvertionUtil;
import org.adimadim.common.util.FacesMessageUtil;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.AccountProperty;
import org.adimadim.service.AccountService;
import org.adimadim.service.exception.AccountException;

/**
 *
 * @author Adem
 */
@Named(value = "accountUpdateBean")
@SessionScoped
public class AccountUpdateBean implements Serializable {

    @Inject
    private AccountService accountService;
    @Inject
    private AccountBean accountBean;
    private boolean riskAccepted = false;
    private Account account = new Account();
    private Map accountProperties = new HashMap();
    private boolean olderThanEighteen = true;
    private String birthDate;

    public AccountUpdateBean() {
    }


    public void readParamaters(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(request.getParameter("key") != null){
            try {
                String secretKey = request.getParameter("key");
                account = accountService.findAccountBySecretKey(secretKey);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                birthDate = sdf.format(account.getBirthDate());
            } catch (Exception ex) {
                Logger.getLogger(AccountUpdateBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean isOlderThanEighteen() {
        try {
            if (birthDate == null) {
                olderThanEighteen = true;
                return olderThanEighteen;
            }
            long age = ConvertionUtil.yearDifference(ConvertionUtil.stringToDate(birthDate, "dd.MM.yyyy"), new Date());
            olderThanEighteen = (age >= 18);
        } catch (ParseException ex) {
            FacesMessageUtil.createFacesMessage("Lütfen girdiğiniz doğum tarihini kontrol ediniz", null, FacesMessage.SEVERITY_ERROR);
        }
        return olderThanEighteen;
    }

    public void startSignUpOperation() {
        try {
            if (riskAccepted == false) {
                throw new AccountException("Risk Kabul Beyannamesini okuduğunuzu lütfen seçerek belirtiniz.");
            }
            if(birthDate == null){
                throw new AccountException("Lütfen doğum tarihinizi giriniz.");
            }
            RegisterBeanValidator.validateAccountForSignUp(account);
            account.setName(ConvertionUtil.firstCharUpperCase(account.getName()));
            account.setSurname(ConvertionUtil.firstCharUpperCase(account.getSurname()));
            account.setBirthDate(ConvertionUtil.stringToDate(birthDate, "dd.MM.yyyy"));
            account.setAccountPropertyList(propertiesToList());
            if(account.getChestNumber() == null){
                account.setChestNumber(accountService.getNextChestNumber());
            }
            accountService.updateAccount(account);
            accountBean.setAccount(account);
            BipNumberUtil.sendBipNumber(account);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/insession/BipNumberServlet");
        } catch (AccountException ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private List<AccountProperty> propertiesToList() {
        List<AccountProperty> accountPropertyList = new ArrayList<>();
        Iterator iterator = accountProperties.entrySet().iterator();
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

    private void listToProperties(List<AccountProperty> accountPropertyList){
        accountProperties.clear();
        for(AccountProperty accountProperty: accountPropertyList){
            accountProperties.put(String.valueOf(accountProperty.getPropertyId()), accountProperty.getPropertyValue());
        }
    }
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        this.birthDate = ConvertionUtil.dateToString(account.getBirthDate(), "dd.MM.yyyy");
        account.setReEmail(account.getEmail());
        listToProperties(account.getAccountPropertyList());
    }

    public boolean isRiskAccepted() {
        return riskAccepted;
    }

    public void setRiskAccepted(boolean riskAccepted) {
        this.riskAccepted = riskAccepted;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Map getAccountProperties() {
        return accountProperties;
    }

    public void setAccountProperties(Map accountProperties) {
        this.accountProperties = accountProperties;
    }

    public void setOlderThanEighteen(boolean olderThanEighteen) {
        this.olderThanEighteen = olderThanEighteen;
    }
}
