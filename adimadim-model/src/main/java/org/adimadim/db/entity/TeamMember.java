/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name = "team_member", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamMember.findAll", query = "SELECT t FROM TeamMember t"),
    @NamedQuery(name = "TeamMember.findByTeamId", query = "SELECT t FROM TeamMember t WHERE t.teamMemberPK.teamId = :teamId"),
    @NamedQuery(name = "TeamMember.findByRaceId", query = "SELECT t FROM TeamMember t WHERE t.teamMemberPK.raceId = :raceId"),
    @NamedQuery(name = "TeamMember.findByAccountId", query = "SELECT t FROM TeamMember t WHERE t.teamMemberPK.accountId = :accountId"),
    @NamedQuery(name = "TeamMember.findByRaceIdAndTeamId", query = "SELECT t FROM Team t WHERE t.teamPK.raceId = :raceId and t.teamPK.teamId = :teamId")
})
public class TeamMember implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TeamMemberPK teamMemberPK;
    @ManyToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "race_id", referencedColumnName = "race_id", insertable = false, updatable = false),
        @JoinColumn(name = "team_id", referencedColumnName = "team_id", insertable = false, updatable = false)
    })
    private Team team;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private Account account;
    
    public TeamMember() {
    }

    public TeamMember(TeamMemberPK teamMemberPK) {
        this.teamMemberPK = teamMemberPK;
    }

    public TeamMember(Integer teamId, Integer raceId, Integer accountId) {
        this.teamMemberPK = new TeamMemberPK(teamId, raceId, accountId);
    }

    public TeamMemberPK getTeamMemberPK() {
        return teamMemberPK;
    }

    public void setTeamMemberPK(TeamMemberPK teamMemberPK) {
        this.teamMemberPK = teamMemberPK;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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
