/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.dto;

import java.io.Serializable;

/**
 *
 * @author Adem
 */
public class BestEvolutionDto implements Serializable{
    
    private Integer accountId;
    private String name;
    private String surname;
    private Integer sign;
    private String firstDuration;
    private String lastDuration;
    private String evolution;
    private String gender;
    
    public BestEvolutionDto(){
        
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getFirstDuration() {
        return firstDuration;
    }

    public void setFirstDuration(String firstDuration) {
        this.firstDuration = firstDuration;
    }

    public String getLastDuration() {
        return lastDuration;
    }

    public void setLastDuration(String lastDuration) {
        this.lastDuration = lastDuration;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
}
