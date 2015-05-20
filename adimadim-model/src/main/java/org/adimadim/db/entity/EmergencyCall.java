/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "emergency_call", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmergencyCall.findAll", query = "SELECT e FROM EmergencyCall e"),
    @NamedQuery(name = "EmergencyCall.findByAccountId", query = "SELECT e FROM EmergencyCall e WHERE e.accountId = :accountId"),
    @NamedQuery(name = "EmergencyCall.findByName", query = "SELECT e FROM EmergencyCall e WHERE e.name = :name"),
    @NamedQuery(name = "EmergencyCall.findBySurname", query = "SELECT e FROM EmergencyCall e WHERE e.surname = :surname"),
    @NamedQuery(name = "EmergencyCall.findByPhoneCode", query = "SELECT e FROM EmergencyCall e WHERE e.phoneCode = :phoneCode"),
    @NamedQuery(name = "EmergencyCall.findByPhoneNumber", query = "SELECT e FROM EmergencyCall e WHERE e.phoneNumber = :phoneNumber")})
public class EmergencyCall implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_id", nullable = false)
    private Integer accountId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false, length = 25)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false, length = 25)
    private String surname;
    @Size(max = 10)
    @Column(name = "phone_code", length = 10)
    private String phoneCode;
    @Size(max = 10)
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    public EmergencyCall() {
    }

    public EmergencyCall(Integer accountId) {
        this.accountId = accountId;
    }

    public EmergencyCall(Integer accountId, String name, String surname) {
        this.accountId = accountId;
        this.name = name;
        this.surname = surname;
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

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmergencyCall)) {
            return false;
        }
        EmergencyCall other = (EmergencyCall) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.EmergencyCall[ accountId=" + accountId + " ]";
    }
    
}
