/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name="team", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findAllByRaceId", query = "SELECT t FROM Team t WHERE t.teamPK.raceId = :raceId"),
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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "race_id", referencedColumnName = "race_id", insertable = false, updatable = false),
        @JoinColumn(name = "team_id", referencedColumnName = "team_id", insertable = false, updatable = false)
    })
    private List<TeamMember> teamMemberList;
    
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

    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.teamPK);
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
        final Team other = (Team) obj;
        if (!Objects.equals(this.teamPK, other.teamPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Team{" + "teamPK=" + teamPK + '}';
    }


    
}
