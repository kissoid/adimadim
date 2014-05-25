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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "old_donation", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OldDonation.findAll", query = "SELECT o FROM OldDonation o"),
    @NamedQuery(name = "OldDonation.findByDonationId", query = "SELECT o FROM OldDonation o WHERE o.donationId = :donationId"),
    @NamedQuery(name = "OldDonation.findByDonatorName", query = "SELECT o FROM OldDonation o WHERE o.donatorName = :donatorName"),
    @NamedQuery(name = "OldDonation.findByRaceDate", query = "SELECT o FROM OldDonation o WHERE o.raceDate = :raceDate"),
    @NamedQuery(name = "OldDonation.findByAmount", query = "SELECT o FROM OldDonation o WHERE o.amount = :amount"),
    @NamedQuery(name = "OldDonation.findByRunnerName", query = "SELECT o FROM OldDonation o WHERE o.runnerName = :runnerName"),
    @NamedQuery(name = "OldDonation.findByRunnerCode", query = "SELECT o FROM OldDonation o WHERE o.runnerCode = :runnerCode"),
    @NamedQuery(name = "OldDonation.findByAccountNumber", query = "SELECT o FROM OldDonation o WHERE o.accountNumber = :accountNumber"),
    @NamedQuery(name = "OldDonation.findByActivityYear", query = "SELECT o FROM OldDonation o WHERE o.activityYear = :activityYear"),
    @NamedQuery(name = "OldDonation.findByActivityId", query = "SELECT o FROM OldDonation o WHERE o.activityId = :activityId"),
    @NamedQuery(name = "OldDonation.findByActivityName", query = "SELECT o FROM OldDonation o WHERE o.activityName = :activityName"),
    @NamedQuery(name = "OldDonation.findByAssociationId", query = "SELECT o FROM OldDonation o WHERE o.associationId = :associationId"),
    @NamedQuery(name = "OldDonation.findByAssociationName", query = "SELECT o FROM OldDonation o WHERE o.associationName = :associationName")})
public class OldDonation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "donation_id", nullable = false)
    private Integer donationId;
    @Size(max = 255)
    @Column(name = "donator_name", length = 255)
    private String donatorName;
    @Size(max = 255)
    @Column(name = "race_date", length = 255)
    private String raceDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 10, scale = 2)
    private Double amount;
    @Size(max = 255)
    @Column(name = "runner_name", length = 255)
    private String runnerName;
    @Size(max = 255)
    @Column(name = "runner_code", length = 255)
    private String runnerCode;
    @Size(max = 100)
    @Column(name = "account_number", length = 100)
    private String accountNumber;
    @Column(name = "activity_year")
    private Integer activityYear;
    @Column(name = "activity_id")
    private Integer activityId;
    @Size(max = 255)
    @Column(name = "activity_name", length = 255)
    private String activityName;
    @Column(name = "association_id")
    private Integer associationId;
    @Size(max = 255)
    @Column(name = "association_name", length = 255)
    private String associationName;

    public OldDonation() {
    }

    public OldDonation(Integer donationId) {
        this.donationId = donationId;
    }

    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }

    public String getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(String raceDate) {
        this.raceDate = raceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public String getRunnerCode() {
        return runnerCode;
    }

    public void setRunnerCode(String runnerCode) {
        this.runnerCode = runnerCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
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
        if (!(object instanceof OldDonation)) {
            return false;
        }
        OldDonation other = (OldDonation) object;
        if ((this.donationId == null && other.donationId != null) || (this.donationId != null && !this.donationId.equals(other.donationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.OldDonation[ donationId=" + donationId + " ]";
    }
    
}
