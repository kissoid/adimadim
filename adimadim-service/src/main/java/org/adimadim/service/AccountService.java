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

    public List<Account> retrieveAllAccounts() throws Exception{
        return accountFacade.findAll();
    }
    
    public Account retrieveAccount(Integer accountId) throws Exception {
        return accountFacade.find(accountId);
    }

    public Account retrieveAccountBySecretKey(String secretKey) throws Exception {
        Map params = new HashMap();
        params.put("secretKey", secretKey);
        List<Account> accountList = accountFacade.findAllByNamedQuery("Account.findBySecretKey", params);
        if (accountList.isEmpty()) {
            return null;
        } else {
            return accountList.get(0);
        }
    }

    public Account retrieveAccountByEmail(String email) throws Exception {
        Map params = new HashMap();
        params.put("email", email);
        List<Account> accountList = accountFacade.findAllByNamedQuery("Account.findByEmail", params);
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
        map.put("searchText", account.getEmail());
        List<Account> existsAccounts = accountFacade.findAllByNamedQuery("Account.findByEmailOrUserName", map);
        if (existsAccounts.isEmpty()) {
            String notFoundMessage = "Sistemimizde böyle bir kayıt bulunamadı. Lütfen bilgilerinizi kontrol ediniz veya aşağıdaki linkten kayıt oluşturun.";
            notFoundMessage += "<a href='http://www.aakosu.org/dagi/join.jsf' class='c7-link'>Kayıt ol</a>";;
            throw new AccountException(notFoundMessage);
        }
        Account tempAccount = existsAccounts.get(0);
        if(tempAccount.getPassword() == null || tempAccount.getPassword().trim().equals("")){
            String notUpdatedAccountMessage = "Şifre tanımlamadiginiz anlasilmistir. Lütfen <a href='http://www.aakosu.org/dagi/join.jsf' class='c7-link'>Kayıt ol</a> ";
            notUpdatedAccountMessage += "sayfasına giderek kayit bilgilerinizi güncelleyiniz ve sifrenizi belirleyiniz.<br/>";
            throw new AccountException(notUpdatedAccountMessage);
        }
        if (!tempAccount.getPassword().equals(account.getPassword())) {
            throw new AccountException("Yanlış şifre girdiniz");
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
        return accountPropertyFacade.findAllByNamedQuery("AccountProperty.findByAccountId", map);
    }

    public void saveAccountProperties(List<AccountProperty> accountPropertyList) throws Exception {
        for (AccountProperty accountProperty : accountPropertyList) {
            accountPropertyFacade.edit(accountProperty);
        }
    }

    public List<Account> retrieveAccountRange(int from, int to) {
        return accountFacade.findRange(new int[]{from, to});
    }
    
    public List<Account> retrieveAccountRangeStartByAccountId(Integer startAccountId, int count) {
        String jpql = "select a from Account a where a.accountId > :startAccountId order by a.accountId";
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("startAccountId", startAccountId);
        return accountFacade.findRangeByQuery(new int[]{0, count}, jpql , map);
    }
}
