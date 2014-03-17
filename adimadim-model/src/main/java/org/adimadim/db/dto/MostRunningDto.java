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
public class MostRunningDto implements Serializable{
    
    private Integer accountId;
    private String name;
    private String surname;
    private String gender;
    private Integer raceCount;
    
    public MostRunningDto(){
        
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

    public Integer getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(Integer raceCount) {
        this.raceCount = raceCount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    
}
