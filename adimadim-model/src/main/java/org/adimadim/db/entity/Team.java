/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mehmet Adem ŞENGÜL
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
    @Column(name = "team_name", length = 50)
    private String teamName;
    @JoinColumn(name = "team_type_id", referencedColumnName = "team_type_id", nullable = false)
    @ManyToOne(optional = false)
    private TeamType teamType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private List<TeamMember> teamMemberList;

    public Team() {
    }

    public Team(TeamPK teamPK) {
        this.teamPK = teamPK;
    }

    public Team(Integer teamId, Integer raceId) {
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

    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }

    @XmlTransient
    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
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
        return "Team[ teamPK=" + teamPK + " ]";
    }
    
}
