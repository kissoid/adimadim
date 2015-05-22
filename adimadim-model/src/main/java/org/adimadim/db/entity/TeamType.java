/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "team_type", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamType.findAll", query = "SELECT t FROM TeamType t"),
    @NamedQuery(name = "TeamType.findByTeamTypeId", query = "SELECT t FROM TeamType t WHERE t.teamTypeId = :teamTypeId"),
    @NamedQuery(name = "TeamType.findByDescription", query = "SELECT t FROM TeamType t WHERE t.description = :description")})
public class TeamType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "team_type_id", nullable = false)
    private Integer teamTypeId;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teamType")
    private List<Team> teamList;

    public TeamType() {
    }

    public TeamType(Integer teamTypeId) {
        this.teamTypeId = teamTypeId;
    }

    public TeamType(Integer teamTypeId, String description) {
        this.teamTypeId = teamTypeId;
        this.description = description;
    }

    public Integer getTeamTypeId() {
        return teamTypeId;
    }

    public void setTeamTypeId(Integer teamTypeId) {
        this.teamTypeId = teamTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamTypeId != null ? teamTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamType)) {
            return false;
        }
        TeamType other = (TeamType) object;
        if ((this.teamTypeId == null && other.teamTypeId != null) || (this.teamTypeId != null && !this.teamTypeId.equals(other.teamTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.otodialog.mavenproject1.TeamType[ teamTypeId=" + teamTypeId + " ]";
    }
    
}
