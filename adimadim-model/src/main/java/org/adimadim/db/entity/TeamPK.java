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
public class TeamPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "team_id", nullable = false)
    private int teamId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "race_id", nullable = false)
    private int raceId;

    public TeamPK() {
    }

    public TeamPK(int teamId, int raceId) {
        this.teamId = teamId;
        this.raceId = raceId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) teamId;
        hash += (int) raceId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamPK)) {
            return false;
        }
        TeamPK other = (TeamPK) object;
        if (this.teamId != other.teamId) {
            return false;
        }
        if (this.raceId != other.raceId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.TeamPK[ teamId=" + teamId + ", raceId=" + raceId + " ]";
    }
    
}
