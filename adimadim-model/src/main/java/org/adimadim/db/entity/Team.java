/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findByTeamId", query = "SELECT t FROM Team t WHERE t.teamPK.teamId = :teamId"),
    @NamedQuery(name = "Team.findByRaceId", query = "SELECT t FROM Team t WHERE t.teamPK.raceId = :raceId"),
    @NamedQuery(name = "Team.findByTeamName", query = "SELECT t FROM Team t WHERE t.teamName = :teamName")})
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TeamPK teamPK;
    @Size(max = 50)
    @Column(name = "team_name", length = 50)
    private String teamName;

    public Team() {
    }

    public Team(TeamPK teamPK) {
        this.teamPK = teamPK;
    }

    public Team(int teamId, int raceId) {
        this.teamPK = new TeamPK(teamId, raceId);
    }

    public TeamPK getTeamPK() {
        return teamPK;
    }

    public void setTeamPK(TeamPK teamPK) {
        this.teamPK = teamPK;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamPK != null ? teamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamPK == null && other.teamPK != null) || (this.teamPK != null && !this.teamPK.equals(other.teamPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.Team[ teamPK=" + teamPK + " ]";
    }
    
}
