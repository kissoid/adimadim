/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.adimadim.db.entity.Account;
import org.adimadim.service.AccountService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyAccountDataModel extends LazyDataModel<Account> {

    @Inject private AccountService accountService;
    List<Account> data;
    
    public LazyAccountDataModel() {
        data = accountService.getAccountRange(0, 10);
    }

 @Override  
    public Account getRowData(String rowKey) { 
        Account account = null;
        try {
            account = accountService.findAccount(Integer.valueOf(rowKey));
        } catch (Exception ex) {
            Logger.getLogger(LazyAccountDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }  
  
    @Override  
    public Object getRowKey(Account account) {  
        return account.getAccountId();  
    }
    
    @Override
    public List<Account> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        data = accountService.getAccountRange(first, first + pageSize);
        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            try {
                String filterProperty = it.next();
                String filterValue = filters.get(filterProperty);
                String fieldValue = String.valueOf(Account.class.getField(filterProperty).get(new Account()));

                System.out.println(filterValue + " : " + fieldValue);

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(LazyAccountDataModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(LazyAccountDataModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(LazyAccountDataModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(LazyAccountDataModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

}
