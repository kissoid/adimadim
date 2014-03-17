/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.validator;

/**
 *
 * @author Adem
 */
public abstract class AbstractValidator {
    
    protected static boolean isStringEmpty(String str){
        if(str == null){
            return true;
        }
        if(str.trim().equals("")){
            return true;
        }
        return false;
    }
    
}
