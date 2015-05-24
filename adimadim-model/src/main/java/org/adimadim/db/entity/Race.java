/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.ObjectTypeConverter;

/**
 *
 * @author Ergo
 */
@Entity
@Table(catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Race.findAll", query = "SELECT r FROM Race r"),
    @NamedQuery(name = "Race.findByRaceId", query = "SELECT r FROM Race r WHERE r.raceId = :raceId"),
    @NamedQuery(name = "Race.findByRaceName", query = "SELECT r FROM Race r WHERE r.raceName = :raceName"),
    @NamedQuery(name = "Race.findByRaceDate", query = "SELECT r FROM Race r WHERE r.raceDate = :raceDate"),
    @NamedQuery(name = "Race.findByActive", query = "SELECT r FROM Race r WHERE r.active = :active"),
    @NamedQuery(name = "Race.findAllActiveOrderByIdDesc", query = "SELECT new org.adimadim.db.entity.Race(r.raceId,r.raceName,r.raceDate,r.active) FROM Race r where r.active='E' order by r.raceId desc"),
    @NamedQuery(name = "Race.findAllOrderByIdDesc", query = "SELECT new org.adimadim.db.entity.Race(r.raceId,r.raceName,r.raceDate,r.active) FROM Race r order by r.raceId desc")
})
@ObjectTypeConverter(name = "BooleanToShort", objectType = Boolean.class, dataType = Short.class, conversionValues = {
    @ConversionValue(objectValue = "True", dataValue = "1"),
    @ConversionValue(objectValue = "False", dataValue = "0")
})
public class Race implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "race_id", nullable = false)
    private Integer raceId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "race_name", nullable = false, length = 50)
    private String raceName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "race_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date raceDate;
    @Convert("BooleanToShort")
    @Basic(optional = false)
    @Column(name = "is_team_race", nullable = false)
    private Boolean isTeamRace;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String active;
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "race")
    private List<RaceScore> raceScoreList;

    public Race() {
    }

    public Race(Integer raceId) {
        this.raceId = raceId;
    }

    public Race(Integer raceId, String raceName, Date raceDate, String active) {
        this.raceId = raceId;
        this.raceName = raceName;
        this.raceDate = raceDate;
        this.active = active;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public Date getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(Date raceDate) {
        this.raceDate = raceDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @XmlTransient
    public List<RaceScore> getRaceScoreList() {
        return raceScoreList;
    }

    public void setRaceScoreList(List<RaceScore> raceScoreList) {
        this.raceScoreList = raceScoreList;
    }

    public Boolean getIsTeamRace() {
        return isTeamRace;
    }

    public void setIsTeamRace(Boolean isTeamRace) {
        this.isTeamRace = isTeamRace;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (raceId != null ? raceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Race)) {
            return false;
        }
        Race other = (Race) object;
        return (this.raceId != null || other.raceId == null) && (this.raceId == null || this.raceId.equals(other.raceId));
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.Race[ raceId=" + raceId + " ]";
    }
    
}
