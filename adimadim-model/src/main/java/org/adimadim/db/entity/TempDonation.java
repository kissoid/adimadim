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
@Table(name = "temp_donation", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TempDonation.findAll", query = "SELECT t FROM TempDonation t"),
    @NamedQuery(name = "TempDonation.findByDonationId", query = "SELECT t FROM TempDonation t WHERE t.donationId = :donationId"),
    @NamedQuery(name = "TempDonation.findByAccountId", query = "SELECT t FROM TempDonation t WHERE t.accountId = :accountId"),
    @NamedQuery(name = "TempDonation.findByRunnerName", query = "SELECT t FROM TempDonation t WHERE t.runnerName = :runnerName"),
    @NamedQuery(name = "TempDonation.findByBenefactor", query = "SELECT t FROM TempDonation t WHERE t.benefactor = :benefactor"),
    @NamedQuery(name = "TempDonation.findByDonationDate", query = "SELECT t FROM TempDonation t WHERE t.donationDate = :donationDate"),
    @NamedQuery(name = "TempDonation.findByDonationAmount", query = "SELECT t FROM TempDonation t WHERE t.donationAmount = :donationAmount"),
    @NamedQuery(name = "TempDonation.findByRunnerCodeName", query = "SELECT t FROM TempDonation t WHERE t.runnerCodeName = :runnerCodeName"),
    @NamedQuery(name = "TempDonation.findByBankAccountNumber", query = "SELECT t FROM TempDonation t WHERE t.bankAccountNumber = :bankAccountNumber"),
    @NamedQuery(name = "TempDonation.findByUnknownValue", query = "SELECT t FROM TempDonation t WHERE t.unknownValue = :unknownValue"),
    @NamedQuery(name = "TempDonation.findByActivityYear", query = "SELECT t FROM TempDonation t WHERE t.activityYear = :activityYear"),
    @NamedQuery(name = "TempDonation.findByActivityTextYear", query = "SELECT t FROM TempDonation t WHERE t.activityTextYear = :activityTextYear"),
    @NamedQuery(name = "TempDonation.findByActivityName", query = "SELECT t FROM TempDonation t WHERE t.activityName = :activityName"),
    @NamedQuery(name = "TempDonation.findByAssociation", query = "SELECT t FROM TempDonation t WHERE t.association = :association")})
public class TempDonation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "donation_id", nullable = false)
    private Integer donationId;
    @Column(name = "account_id")
    private Integer accountId;
    @Size(max = 50)
    @Column(name = "runner_name", length = 50)
    private String runnerName;
    @Size(max = 50)
    @Column(length = 50)
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
    @Column(name = "runner_code_name", length = 50)
    private String runnerCodeName;
    @Size(max = 50)
    @Column(name = "bank_account_number", length = 50)
    private String bankAccountNumber;
    @Size(max = 50)
    @Column(name = "unknown_value", length = 50)
    private String unknownValue;
    @Column(name = "activity_year")
    private Integer activityYear;
    @Size(max = 15)
    @Column(name = "activity_text_year", length = 15)
    private String activityTextYear;
    @Size(max = 25)
    @Column(name = "activity_name", length = 25)
    private String activityName;
    @Size(max = 25)
    @Column(length = 25)
    private String association;

    public TempDonation() {
    }

    public TempDonation(Integer donationId) {
        this.donationId = donationId;
    }

    public TempDonation(Integer donationId, Date donationDate) {
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

    public String getRunnerCodeName() {
        return runnerCodeName;
    }

    public void setRunnerCodeName(String runnerCodeName) {
        this.runnerCodeName = runnerCodeName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getUnknownValue() {
        return unknownValue;
    }

    public void setUnknownValue(String unknownValue) {
        this.unknownValue = unknownValue;
    }

    public Integer getActivityYear() {
        return activityYear;
    }

    public void setActivityYear(Integer activityYear) {
        this.activityYear = activityYear;
    }

    public String getActivityTextYear() {
        return activityTextYear;
    }

    public void setActivityTextYear(String activityTextYear) {
        this.activityTextYear = activityTextYear;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
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
        if (!(object instanceof TempDonation)) {
            return false;
        }
        TempDonation other = (TempDonation) object;
        if ((this.donationId == null && other.donationId != null) || (this.donationId != null && !this.donationId.equals(other.donationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.TempDonation[ donationId=" + donationId + " ]";
    }
    
}
