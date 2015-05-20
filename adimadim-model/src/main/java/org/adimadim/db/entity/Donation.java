/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Donation.findAll", query = "SELECT d FROM Donation d"),
    @NamedQuery(name = "Donation.findByDonationId", query = "SELECT d FROM Donation d WHERE d.donationId = :donationId"),
    @NamedQuery(name = "Donation.findByAccountId", query = "SELECT d FROM Donation d WHERE d.accountId = :accountId"),
    @NamedQuery(name = "Donation.findByRunnerName", query = "SELECT d FROM Donation d WHERE d.runnerName = :runnerName"),
    @NamedQuery(name = "Donation.findByBenefactor", query = "SELECT d FROM Donation d WHERE d.benefactor = :benefactor"),
    @NamedQuery(name = "Donation.findByDonationDate", query = "SELECT d FROM Donation d WHERE d.donationDate = :donationDate"),
    @NamedQuery(name = "Donation.findByDonationAmount", query = "SELECT d FROM Donation d WHERE d.donationAmount = :donationAmount"),
    @NamedQuery(name = "Donation.findByBankAccount", query = "SELECT d FROM Donation d WHERE d.bankAccount = :bankAccount"),
    @NamedQuery(name = "Donation.findByComment", query = "SELECT d FROM Donation d WHERE d.comment = :comment"),
    @NamedQuery(name = "Donation.findByActivityYear", query = "SELECT d FROM Donation d WHERE d.activityYear = :activityYear"),
    @NamedQuery(name = "Donation.findByActivityId", query = "SELECT d FROM Donation d WHERE d.activityId = :activityId"),
    @NamedQuery(name = "Donation.findByActivityName", query = "SELECT d FROM Donation d WHERE d.activityName = :activityName"),
    @NamedQuery(name = "Donation.findByAssociationId", query = "SELECT d FROM Donation d WHERE d.associationId = :associationId"),
    @NamedQuery(name = "Donation.findByAssociation", query = "SELECT d FROM Donation d WHERE d.association = :association"),
    @NamedQuery(name = "Donation.findByRunnerCode", query = "SELECT d FROM Donation d WHERE d.runnerCode = :runnerCode")})
public class Donation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "donation_id", nullable = false)
    private Integer donationId;
    @Column(name = "account_id")
    private Integer accountId;
    @Size(max = 100)
    @Column(name = "runner_name", length = 100)
    private String runnerName;
    @Size(max = 100)
    @Column(length = 100)
    private String benefactor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "donation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date donationDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "donation_amount", precision = 9, scale = 2)
    private BigDecimal donationAmount;
    @Size(max = 50)
    @Column(name = "bank_account", length = 50)
    private String bankAccount;
    @Size(max = 100)
    @Column(length = 100)
    private String comment;
    @Column(name = "activity_year")
    private Integer activityYear;
    @Column(name = "activity_id")
    private Integer activityId;
    @Size(max = 25)
    @Column(name = "activity_name", length = 25)
    private String activityName;
    @Column(name = "association_id")
    private Integer associationId;
    @Size(max = 25)
    @Column(length = 25)
    private String association;
    @Size(max = 50)
    @Column(name = "runner_code", length = 50)
    private String runnerCode;

    public Donation() {
    }

    public Donation(Integer donationId) {
        this.donationId = donationId;
    }

    public Donation(Integer donationId, Date donationDate) {
        this.donationId = donationId;
        this.donationDate = donationDate;
    }

    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public String getBenefactor() {
        return benefactor;
    }

    public void setBenefactor(String benefactor) {
        this.benefactor = benefactor;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getActivityYear() {
        return activityYear;
    }

    public void setActivityYear(Integer activityYear) {
        this.activityYear = activityYear;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Integer associationId) {
        this.associationId = associationId;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getRunnerCode() {
        return runnerCode;
    }

    public void setRunnerCode(String runnerCode) {
        this.runnerCode = runnerCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (donationId != null ? donationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Donation)) {
            return false;
        }
        Donation other = (Donation) object;
        if ((this.donationId == null && other.donationId != null) || (this.donationId != null && !this.donationId.equals(other.donationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.Donation[ donationId=" + donationId + " ]";
    }
    
}
