/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name = "account_property", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountProperty.findAll", query = "SELECT a FROM AccountProperty a"),
    @NamedQuery(name = "AccountProperty.findByAccountId", query = "SELECT a FROM AccountProperty a WHERE a.accountPropertyPK.accountId = :accountId"),
    @NamedQuery(name = "AccountProperty.findByPropertyId", query = "SELECT a FROM AccountProperty a WHERE a.accountPropertyPK.propertyId = :propertyId"),
    @NamedQuery(name = "AccountProperty.findByPropertyValue", query = "SELECT a FROM AccountProperty a WHERE a.propertyValue = :propertyValue")})
public class AccountProperty implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AccountPropertyPK accountPropertyPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "property_value", nullable = false, length = 255)
    private String propertyValue;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;

    public AccountProperty() {
    }

    public AccountProperty(AccountPropertyPK accountPropertyPK) {
        this.accountPropertyPK = accountPropertyPK;
    }

    public AccountProperty(AccountPropertyPK accountPropertyPK, String propertyValue) {
        this.accountPropertyPK = accountPropertyPK;
        this.propertyValue = propertyValue;
    }

    public AccountProperty(int accountId, int propertyId) {
        this.accountPropertyPK = new AccountPropertyPK(accountId, propertyId);
    }

    public AccountPropertyPK getAccountPropertyPK() {
        return accountPropertyPK;
    }

    public void setAccountPropertyPK(AccountPropertyPK accountPropertyPK) {
        this.accountPropertyPK = accountPropertyPK;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountPropertyPK != null ? accountPropertyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountProperty)) {
            return false;
        }
        AccountProperty other = (AccountProperty) object;
        if ((this.accountPropertyPK == null && other.accountPropertyPK != null) || (this.accountPropertyPK != null && !this.accountPropertyPK.equals(other.accountPropertyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.AccountProperty[ accountPropertyPK=" + accountPropertyPK + " ]";
    }
    
}
