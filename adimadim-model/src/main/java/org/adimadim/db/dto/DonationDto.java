/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DonationDto implements Serializable {
    
    private Integer accountId;
    private String name;
    private String surname;
    private Integer benefactorCount;
    private Double donationAmount;

    public DonationDto(){
        
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

    public Integer getBenefactorCount() {
        return benefactorCount;
    }

    public void setBenefactorCount(Integer benefactorCount) {
        this.benefactorCount = benefactorCount;
    }

    public Double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
    }
    
}
