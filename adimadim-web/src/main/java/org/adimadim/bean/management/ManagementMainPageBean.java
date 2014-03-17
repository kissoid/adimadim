/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.management;

/**
 *
 * @author Adem
 */
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named(value = "managementMainPageBean")
public class ManagementMainPageBean implements Serializable {

    private String activeFragment = "";
    
    public ManagementMainPageBean(){
        
    }

    public void changeActiveFragment(String activeFragment){
        this.activeFragment = activeFragment;
    }
    
    public String getActiveFragment() {
        return activeFragment;
    }

    public void setActiveFragment(String activeFragment) {
        this.activeFragment = activeFragment;
    }

}
