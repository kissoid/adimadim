/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Adem
 */
@Named(value="keepSessionBean")
@RequestScoped
public class KeepSessionBean implements Serializable{
    
    public KeepSessionBean(){
        
    }
    
    public void keepSession(){
        
    }
    
}
