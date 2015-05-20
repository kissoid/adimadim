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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ergo
 */
@Embeddable
public class TeamPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "team_id", nullable = false)
    private Integer teamId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "race_id", nullable = false)
    private Integer raceId;

    public TeamPK() {
    }

    public TeamPK(Integer teamId, Integer raceId) {
        this.teamId = teamId;
        this.raceId = raceId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.teamId);
        hash = 37 * hash + Objects.hashCode(this.raceId);
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
        final TeamPK other = (TeamPK) obj;
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        if (!Objects.equals(this.raceId, other.raceId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamPK{" + "teamId=" + teamId + ", raceId=" + raceId + '}';
    }


    
}
