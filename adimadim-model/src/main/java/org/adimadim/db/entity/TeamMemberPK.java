/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Adem
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
    @Size(min = 1, max = 50)
    @Column(name = "account_id", nullable = false, length = 50)
    private String accountId;

    public TeamMemberPK() {
    }

    public TeamMemberPK(int teamId, int raceId, String accountId) {
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) teamId;
        hash += (int) raceId;
        hash += (accountId != null ? accountId.hashCode() : 0);
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
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.TeamMemberPK[ teamId=" + teamId + ", raceId=" + raceId + ", accountId=" + accountId + " ]";
    }
    
}
