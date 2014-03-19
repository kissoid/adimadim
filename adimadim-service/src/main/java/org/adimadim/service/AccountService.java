/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
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
@Stateless
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
        return accountFacade.findByNamedQuery("Account.findBySecretKey", params, null);
    }
    
    public Account findAccountByEmail(String email) throws Exception {
        Map params = new HashMap();
        params.put("email", email);
        return accountFacade.findByNamedQuery("Account.findByEmail", params, null);
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

    public Integer signUp(Account account) throws AccountException, Exception {
        Map map = new HashMap();
        map.put("email", account.getEmail());
        List<Account> existsAccounts = accountFacade.findAllByNamedQuery("Account.findByEmail", map, null);
        if (existsAccounts.isEmpty()) {
            account.setAccountId(getNextAccountId());
            accountFacade.create(account);
            return account.getAccountId();
        } else {
            throw new AccountException("Bu email daha önceden kayıt ettirilmiş.");
        }
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
    
    public Integer getNextAccountId() throws Exception {
        String jpqlString = "select max(a.accountId) from Account a";
        Integer accountId = (Integer) accountFacade.findByQuery(jpqlString, LockModeType.PESSIMISTIC_WRITE);
        accountId = (accountId == null ? 0 : accountId) + 1;
        return accountId;
    }
    
    public List<AccountProperty> findAccountPropertiesByAccountId(Integer accountId) throws Exception{
        Map map = new HashMap();
        map.put("accountId", accountId);
        return accountPropertyFacade.findAllByNamedQuery("AccountProperty.findByAccountId", map, null);
    }
    
    public void saveAccountProperties(List<AccountProperty> accountPropertyList) throws Exception{
        for(AccountProperty accountProperty: accountPropertyList){
            accountPropertyFacade.edit(accountProperty);
        }
    }
    
    public List<Account> getAccountRange(int from, int to){
        return accountFacade.findRange(new int[]{from, to});
    }
}
