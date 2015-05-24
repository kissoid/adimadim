/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.ObjectTypeConverter;

/**
 *
 * @author Mehmet Adem ŞENGÜL
 */
@Entity
@Table(name = "team_member", catalog = "adimadim", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"team_id", "account_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamMember.findAll", query = "SELECT t FROM TeamMember t"),
    @NamedQuery(name = "TeamMember.findByTeamMemberId", query = "SELECT t FROM TeamMember t WHERE t.teamMemberId = :teamMemberId"),
    @NamedQuery(name = "TeamMember.findByIsApproved", query = "SELECT t FROM TeamMember t WHERE t.isApproved = :isApproved")
})
@ObjectTypeConverter(name = "BooleanToShort", objectType = Boolean.class, dataType = Short.class, conversionValues = {
    @ConversionValue(objectValue = "True", dataValue = "1"),
    @ConversionValue(objectValue = "False", dataValue = "0")
})
public class TeamMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "team_member_id", nullable = false)
    private Integer teamMemberId;
    @Convert("BooleanToShort")
    @Basic(optional = false)
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;
    @JoinColumn(name = "team_id", referencedColumnName = "team_id", nullable = false)
    @ManyToOne(optional = false)
    private Team team;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @ManyToOne(optional = false)
    private Account account;

    public TeamMember() {
    }

    public TeamMember(Integer teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public TeamMember(Integer teamMemberId, Boolean isApproved) {
        this.teamMemberId = teamMemberId;
        this.isApproved = isApproved;
    }

    public Integer getTeamMemberId() {
        return teamMemberId;
    }

    public void setTeamMemberId(Integer teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
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
        hash += (teamMemberId != null ? teamMemberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamMember)) {
            return false;
        }
        TeamMember other = (TeamMember) object;
        if ((this.teamMemberId == null && other.teamMemberId != null) || (this.teamMemberId != null && !this.teamMemberId.equals(other.teamMemberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamMember[ teamMemberId=" + teamMemberId + " ]";
    }

}
