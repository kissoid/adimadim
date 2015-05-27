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

    public List<Account> findAllAccounts() throws Exception {
        return accountFacade.findAll();
    }

    public Account findAccount(Integer accountId) throws Exception {
        return accountFacade.find(accountId);
    }

    public Account findAccountBySecretKey(String secretKey) throws Exception {
        Map params = new HashMap();
        params.put("secretKey", secretKey);
        List<Account> accountList = accountFacade.findListByNamedQuery("Account.findBySecretKey", params);
        if (accountList.isEmpty()) {
            return null;
        } else {
            return accountList.get(0);
        }
    }

    public Account findAccountByEmail(String email) throws Exception {
        Map params = new HashMap();
        params.put("email", email);
        List<Account> accountList = accountFacade.findListByNamedQuery("Account.findByEmail", params);
        if (accountList.isEmpty()) {
            return null;
        } else {
            return accountList.get(0);
        }
    }

    public void saveAccount(Account account) throws Exception {
        if (account.getAccountId() == null) {
            accountFacade.save(account);
        } else {
            accountFacade.update(account);
        }
    }

    public Account login(Account account) throws AccountException, Exception {
        Map map = new HashMap();
        map.put("searchText", account.getEmail());
        List<Account> existsAccounts = accountFacade.findListByNamedQuery("Account.findByEmailOrUserName", map);
        if (existsAccounts.isEmpty()) {
            String notFoundMessage = "Sistemimizde böyle bir kayıt bulunamadı. Lütfen bilgilerinizi kontrol ediniz veya aşağıdaki linkten kayıt oluşturun.";
            notFoundMessage += "<a href='http://www.aakosu.org/outsession/dagi/join.jsf' class='c7-link'>Kayıt ol</a>";;
            throw new AccountException(notFoundMessage);
        }
        Account tempAccount = existsAccounts.get(0);
        if (tempAccount.getPassword() == null || tempAccount.getPassword().trim().equals("")) {
            String notUpdatedAccountMessage = "Şifre tanımlamadiginiz anlasilmistir. Lütfen <a href='http://www.aakosu.org/outsession/dagi/join.jsf' class='c7-link'>Kayıt ol</a> ";
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
        accountFacade.update(account);
        return account.getAccountId();

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Integer updateAccount(Account account) throws AccountException, Exception {
        accountFacade.update(account);
        return account.getAccountId();

    }

    public Integer getNextChestNumber() throws Exception {
        String jpqlString = "select max(a.chestNumber) from Account a";
        Integer chestNumber = (Integer) accountFacade.findValueByQuery(jpqlString, LockModeType.PESSIMISTIC_WRITE);
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
        return accountPropertyFacade.findListByNamedQuery("AccountProperty.findByAccountId", map);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveAccountProperties(List<AccountProperty> accountPropertyList) throws Exception {
        for (AccountProperty accountProperty : accountPropertyList) {
            accountPropertyFacade.update(accountProperty);
        }
    }

    public List<Account> findAccountRange(int from, int to) {
        return accountFacade.findRange(new int[]{from, to});
    }

    public List<Account> findAccountRangeStartByAccountId(Integer startAccountId, int count) {
        String jpql = "select a from Account a where a.accountId > :startAccountId order by a.accountId";
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("startAccountId", startAccountId);
        return accountFacade.findRangeByQuery(new int[]{0, count}, jpql, map);
    }

    public List<Account> findAccountRangeNotInListByName(String name, String surname, List<Integer> accountIdList) throws Exception {
        String jpql = "select distinct a from Account a where 1=1 ";
        if (name != null && !name.trim().equals("")) {
            jpql += " and a.name like :name ";
        }
        if (surname != null && !surname.trim().equals("")) {
            jpql += " and a.surname like :surname ";
        }
        if (!accountIdList.isEmpty()) {
            jpql += " and a.accountId not in :accountIdList ";
        }
        jpql += " order by a.name,a.surname";
        Map<String, Object> params = new HashMap<String, Object>();
        if (name != null && !name.trim().equals("")) {
            params.put("name", "%" + name + "%");
        }
        if (surname != null && !surname.trim().equals("")) {
            params.put("surname", "%" + surname + "%");
        }
        if (!accountIdList.isEmpty()) {
            params.put("accountIdList", accountIdList);
        }
        return accountFacade.findRangeByQuery(new int[]{0, 100}, jpql, params);
    }

}
