/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Mehmet Adem ŞENGÜL
 */
@Embeddable
public class TeamMemberPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "team_id", nullable = false)
    private Integer teamId;
    @Basic(optional = false)
    @Column(name = "race_id", nullable = false)
    private Integer raceId;
    @Basic(optional = false)
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    public TeamMemberPK() {
    }

    public TeamMemberPK(Integer teamId, Integer raceId, Integer accountId) {
        this.teamId = teamId;
        this.raceId = raceId;
        this.accountId = accountId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.teamId);
        hash = 97 * hash + Objects.hashCode(this.raceId);
        hash = 97 * hash + Objects.hashCode(this.accountId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TeamMemberPK other = (TeamMemberPK) obj;
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        if (!Objects.equals(this.raceId, other.raceId)) {
            return false;
        }
        if (!Objects.equals(this.accountId, other.accountId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamMemberPK{" + "teamId=" + teamId + ", raceId=" + raceId + ", accountId=" + accountId + '}';
    }


    
}
