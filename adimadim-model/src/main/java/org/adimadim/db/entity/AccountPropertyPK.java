/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Adem
 */
@Embeddable
public class AccountPropertyPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_id", nullable = false)
    private int accountId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "property_id", nullable = false)
    private int propertyId;

    public AccountPropertyPK() {
    }

    public AccountPropertyPK(int accountId, int propertyId) {
        this.accountId = accountId;
        this.propertyId = propertyId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) accountId;
        hash += (int) propertyId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountPropertyPK)) {
            return false;
        }
        AccountPropertyPK other = (AccountPropertyPK) object;
        if (this.accountId != other.accountId) {
            return false;
        }
        if (this.propertyId != other.propertyId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.AccountPropertyPK[ accountId=" + accountId + ", propertyId=" + propertyId + " ]";
    }
    
}
