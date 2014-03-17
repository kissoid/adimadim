/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.adimadim.db.entity.Account;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value="testBean")
public class TestBean implements Serializable{
    
    private LazyDataModel<Account> lazyModel;
    
    public TestBean() {
        lazyModel = new LazyAccountDataModel();
    }

    public LazyDataModel<Account> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Account> lazyModel) {
        this.lazyModel = lazyModel;
    }
    

    
}
