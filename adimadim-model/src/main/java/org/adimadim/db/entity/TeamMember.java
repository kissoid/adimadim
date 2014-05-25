/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "team_member", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamMember.findAll", query = "SELECT t FROM TeamMember t"),
    @NamedQuery(name = "TeamMember.findByTeamId", query = "SELECT t FROM TeamMember t WHERE t.teamMemberPK.teamId = :teamId"),
    @NamedQuery(name = "TeamMember.findByRaceId", query = "SELECT t FROM TeamMember t WHERE t.teamMemberPK.raceId = :raceId"),
    @NamedQuery(name = "TeamMember.findByAccountId", query = "SELECT t FROM TeamMember t WHERE t.teamMemberPK.accountId = :accountId")})
public class TeamMember implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TeamMemberPK teamMemberPK;

    public TeamMember() {
    }

    public TeamMember(TeamMemberPK teamMemberPK) {
        this.teamMemberPK = teamMemberPK;
    }

    public TeamMember(int teamId, int raceId, String accountId) {
        this.teamMemberPK = new TeamMemberPK(teamId, raceId, accountId);
    }

    public TeamMemberPK getTeamMemberPK() {
        return teamMemberPK;
    }

    public void setTeamMemberPK(TeamMemberPK teamMemberPK) {
        this.teamMemberPK = teamMemberPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamMemberPK != null ? teamMemberPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamMember)) {
            return false;
        }
        TeamMember other = (TeamMember) object;
        if ((this.teamMemberPK == null && other.teamMemberPK != null) || (this.teamMemberPK != null && !this.teamMemberPK.equals(other.teamMemberPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.TeamMember[ teamMemberPK=" + teamMemberPK + " ]";
    }
    
}
