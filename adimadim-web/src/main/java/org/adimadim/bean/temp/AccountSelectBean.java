/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.temp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.adimadim.db.entity.Account;
import org.adimadim.service.RacerService;
import org.adimadim.common.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value="accountSelectBean")
public class AccountSelectBean implements Serializable {

    @Inject
    private RacerService racerService;
    @Inject
    private AccountUpdateBean accountUpdateBean;
    private Account selectedRacer = new Account();
    private List<Account> racerList = new ArrayList<Account>();
    private Date startDate;
    private Date endDate;

    public AccountSelectBean() {
    }

    @PostConstruct
    private void init() {
        retriveAllAccounts();
    }

    public void retriveAllAccounts() {
        try {
            racerList = racerService.retrieveAllRacersHasNoPassword();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void updateAccount(Account account) throws Exception {
        accountUpdateBean.setAccount(account);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/outsession/temp/accountUpdate.jsf");
    }

    public List<Account> getRacerList() {
        return racerList;
    }

    public void setRacerList(List<Account> racerList) {
        this.racerList = racerList;
    }

    public Account getSelectedRacer() {
        return selectedRacer;
    }

    public void setSelectedRacer(Account selectedRacer) {
        this.selectedRacer = selectedRacer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
     
}
