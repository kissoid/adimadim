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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Ergo
 */
@Entity
@Table(name = "account_property", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountProperty.findAll", query = "SELECT a FROM AccountProperty a"),
    @NamedQuery(name = "AccountProperty.findByPropertyId", query = "SELECT a FROM AccountProperty a WHERE a.propertyId = :propertyId"),
    @NamedQuery(name = "AccountProperty.findByPropertyValue", query = "SELECT a FROM AccountProperty a WHERE a.propertyValue = :propertyValue"),
    @NamedQuery(name = "AccountProperty.findByAccountPropertyId", query = "SELECT a FROM AccountProperty a WHERE a.accountPropertyId = :accountPropertyId"),
    @NamedQuery(name = "AccountProperty.findByAccountId", query = "SELECT a FROM AccountProperty a WHERE a.account.accountId = :accountId")
})
public class AccountProperty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "account_property_id", nullable = false)
    private Integer accountPropertyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "property_id", nullable = false)
    private int propertyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "property_value", nullable = false, length = 255)
    private String propertyValue;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @ManyToOne(optional = false)
    private Account account;

    public AccountProperty() {
    }

    public AccountProperty(Integer accountPropertyId) {
        this.accountPropertyId = accountPropertyId;
    }

    public AccountProperty(Integer accountPropertyId, int propertyId, String propertyValue) {
        this.accountPropertyId = accountPropertyId;
        this.propertyId = propertyId;
        this.propertyValue = propertyValue;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Integer getAccountPropertyId() {
        return accountPropertyId;
    }

    public void setAccountPropertyId(Integer accountPropertyId) {
        this.accountPropertyId = accountPropertyId;
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
        hash += (accountPropertyId != null ? accountPropertyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountProperty)) {
            return false;
        }
        AccountProperty other = (AccountProperty) object;
        if ((this.accountPropertyId == null && other.accountPropertyId != null) || (this.accountPropertyId != null && !this.accountPropertyId.equals(other.accountPropertyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.AccountProperty[ accountPropertyId=" + accountPropertyId + " ]";
    }
    
}
