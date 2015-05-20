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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(catalog = "adimadim", schema = "")
@XmlRootElement
public class TeamType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "team_type_id", nullable = false)
    private Short teamTypeId;
    @Size(max = 50)
    @Column(name = "description", length = 50)
    private String description;

    public TeamType() {
    }

    public Short getTeamTypeId() {
        return teamTypeId;
    }

    public void setTeamTypeId(Short teamTypeId) {
        this.teamTypeId = teamTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.teamTypeId);
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
        final TeamType other = (TeamType) obj;
        if (!Objects.equals(this.teamTypeId, other.teamTypeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamType{" + "teamTypeId=" + teamTypeId + '}';
    }

}
