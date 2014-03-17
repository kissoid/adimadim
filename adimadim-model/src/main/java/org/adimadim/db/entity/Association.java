/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
 * @author Adem
 */
@Entity
@Table(catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Association.findAll", query = "SELECT a FROM Association a"),
    @NamedQuery(name = "Association.findByAssociationId", query = "SELECT a FROM Association a WHERE a.associationId = :associationId"),
    @NamedQuery(name = "Association.findByAssociationName", query = "SELECT a FROM Association a WHERE a.associationName = :associationName")})
public class Association implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "association_id", nullable = false)
    private Integer associationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "association_name", nullable = false, length = 25)
    private String associationName;

    public Association() {
    }

    public Association(Integer associationId) {
        this.associationId = associationId;
    }

    public Association(Integer associationId, String associationName) {
        this.associationId = associationId;
        this.associationName = associationName;
    }

    public Integer getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Integer associationId) {
        this.associationId = associationId;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (associationId != null ? associationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Association)) {
            return false;
        }
        Association other = (Association) object;
        if ((this.associationId == null && other.associationId != null) || (this.associationId != null && !this.associationId.equals(other.associationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.Association[ associationId=" + associationId + " ]";
    }
    
}
