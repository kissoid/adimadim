/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.main;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value = "mainPageBean")
public class MainPageBean implements Serializable {  

    private String activeFragment = "announcements";
    
    public MainPageBean() {

    }
    
    public void changeActiveFragment(String fragmentName){
        activeFragment = fragmentName;
    }
    
    public String getActiveFragment() {
        return activeFragment;
    }

    public void setActiveFragment(String activeFragment) {
        this.activeFragment = activeFragment;
    }

}
