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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "account_parent", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountParent.findAll", query = "SELECT a FROM AccountParent a"),
    @NamedQuery(name = "AccountParent.findByAccountId", query = "SELECT a FROM AccountParent a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "AccountParent.findByParentName", query = "SELECT a FROM AccountParent a WHERE a.parentName = :parentName")})
public class AccountParent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_id", nullable = false)
    private Integer accountId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "parent_name", nullable = false, length = 50)
    private String parentName;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Account account;

    public AccountParent() {
    }

    public AccountParent(Integer accountId) {
        this.accountId = accountId;
    }

    public AccountParent(Integer accountId, String parentName) {
        this.accountId = accountId;
        this.parentName = parentName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountParent)) {
            return false;
        }
        AccountParent other = (AccountParent) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.AccountParent[ accountId=" + accountId + " ]";
    }
    
}
