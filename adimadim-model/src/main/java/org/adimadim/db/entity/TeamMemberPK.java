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
 * @author Mehmet Adem ŞENGÜL
 */
@Embeddable
public class TeamMemberPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "team_id", nullable = false)
    private int teamId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "race_id", nullable = false)
    private int raceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_id", nullable = false)
    private int accountId;

    public TeamMemberPK() {
    }

    public TeamMemberPK(int teamId, int raceId, int accountId) {
        this.teamId = teamId;
        this.raceId = raceId;
        this.accountId = accountId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) teamId;
        hash += (int) raceId;
        hash += (int) accountId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamMemberPK)) {
            return false;
        }
        TeamMemberPK other = (TeamMemberPK) object;
        if (this.teamId != other.teamId) {
            return false;
        }
        if (this.raceId != other.raceId) {
            return false;
        }
        if (this.accountId != other.accountId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.TeamMemberPK[ teamId=" + teamId + ", raceId=" + raceId + ", accountId=" + accountId + " ]";
    }
    
}
