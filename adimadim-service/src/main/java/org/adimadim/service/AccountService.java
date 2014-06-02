/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.AccountProperty;
import org.adimadim.facade.AccountFacade;
import org.adimadim.facade.AccountPropertyFacade;
import org.adimadim.service.exception.AccountException;

/**
 *
 * @author Adem
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless(name = "accountService")
public class AccountService {

    @Inject
    private AccountFacade accountFacade;
    @Inject
    private AccountPropertyFacade accountPropertyFacade;

    public AccountService() {
    }

    public Account findAccount(Integer accountId) throws Exception {
        return accountFacade.find(accountId);
    }

    public Account findAccountBySecretKey(String secretKey) throws Exception {
        Map params = new HashMap();
        params.put("secretKey", secretKey);
        List<Account> accountList = accountFacade.findAllByNamedQuery("Account.findBySecretKey", params, null);
        if (accountList.isEmpty()) {
            return null;
        } else {
            return accountList.get(0);
        }
    }

    public Account findAccountByEmail(String email) throws Exception {
        Map params = new HashMap();
        params.put("email", email);
        List<Account> accountList = accountFacade.findAllByNamedQuery("Account.findByEmail", params, null);
        if (accountList.isEmpty()) {
            return null;
        } else {
            return accountList.get(0);
        }
    }

    public void saveAccount(Account account) throws Exception {
        if (account.getAccountId() == null) {
            accountFacade.create(account);
        } else {
            accountFacade.edit(account);
        }
    }

    public Account signIn(Account account) throws AccountException, Exception {
        Map map = new HashMap();
        map.put("email", account.getEmail());
        List<Account> existsAccounts = accountFacade.findAllByNamedQuery("Account.findByEmail", map, null);
        if (existsAccounts.isEmpty()) {
            throw new AccountException("Kullanıcı bulunamadı.");
        }
        if (!existsAccounts.get(0).getPassword().equals(account.getPassword())) {
            throw new AccountException("Şifre yanlış");
        }
        return existsAccounts.get(0);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Integer signUp(Account account) throws AccountException, Exception {
        Map map = new HashMap();
        map.put("email", account.getEmail());
        if (account.getChestNumber() == null) {
            account.setChestNumber(getNextChestNumber());
        }
        //account.setAccountId(getNextAccountId());
        accountFacade.edit(account);
        return account.getAccountId();

    }

    public Integer updateAccount(Account account) throws AccountException, Exception {
        accountFacade.edit(account);
        return account.getAccountId();

    }

    public Integer getNextChestNumber() throws Exception {
        String jpqlString = "select max(a.chestNumber) from Account a";
        Integer chestNumber = (Integer) accountFacade.findByQuery(jpqlString, LockModeType.PESSIMISTIC_WRITE);
        chestNumber = (chestNumber == null ? 0 : chestNumber) + 1;
        return chestNumber;
    }

    /*
     public Integer getNextAccountId() throws Exception {
     String jpqlString = "select max(a.accountId) from Account a";
     Integer accountId = (Integer) accountFacade.findByQuery(jpqlString, LockModeType.PESSIMISTIC_WRITE);
     accountId = (accountId == null ? 0 : accountId) + 1;
     return accountId;
     }*/
    public List<AccountProperty> findAccountPropertiesByAccountId(Integer accountId) throws Exception {
        Map map = new HashMap();
        map.put("accountId", accountId);
        return accountPropertyFacade.findAllByNamedQuery("AccountProperty.findByAccountId", map, null);
    }

    public void saveAccountProperties(List<AccountProperty> accountPropertyList) throws Exception {
        for (AccountProperty accountProperty : accountPropertyList) {
            accountPropertyFacade.edit(accountProperty);
        }
    }

    public List<Account> getAccountRange(int from, int to) {
        return accountFacade.findRange(new int[]{from, to});
    }
}
