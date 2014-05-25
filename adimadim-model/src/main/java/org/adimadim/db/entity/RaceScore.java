/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "race_score", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RaceScore.findAll", query = "SELECT r FROM RaceScore r"),
    @NamedQuery(name = "RaceScore.findByRaceScoreId", query = "SELECT r FROM RaceScore r WHERE r.raceScoreId = :raceScoreId"),
    @NamedQuery(name = "RaceScore.findByDuration", query = "SELECT r FROM RaceScore r WHERE r.duration = :duration"),
    @NamedQuery(name = "RaceScore.findByTeamId", query = "SELECT r FROM RaceScore r WHERE r.teamId = :teamId")})
public class RaceScore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "race_score_id", nullable = false)
    private Integer raceScoreId;
    @Temporal(TemporalType.TIME)
    private Date duration;
    @Column(name = "team_id")
    private Integer teamId;
    @JoinColumn(name = "race_id", referencedColumnName = "race_id", nullable = false)
    @ManyToOne(optional = false)
    private Race raceId;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @ManyToOne(optional = false)
    private Account account;

    public RaceScore() {
    }

    public RaceScore(Integer raceScoreId) {
        this.raceScoreId = raceScoreId;
    }

    public Integer getRaceScoreId() {
        return raceScoreId;
    }

    public void setRaceScoreId(Integer raceScoreId) {
        this.raceScoreId = raceScoreId;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Race getRaceId() {
        return raceId;
    }

    public void setRaceId(Race raceId) {
        this.raceId = raceId;
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
        hash += (raceScoreId != null ? raceScoreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RaceScore)) {
            return false;
        }
        RaceScore other = (RaceScore) object;
        if ((this.raceScoreId == null && other.raceScoreId != null) || (this.raceScoreId != null && !this.raceScoreId.equals(other.raceScoreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.RaceScore[ raceScoreId=" + raceScoreId + " ]";
    }
    
}
