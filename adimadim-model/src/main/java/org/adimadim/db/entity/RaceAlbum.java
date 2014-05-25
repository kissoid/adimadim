/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "race_album", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RaceAlbum.findAll", query = "SELECT r FROM RaceAlbum r"),
    @NamedQuery(name = "RaceAlbum.findByAlbumId", query = "SELECT r FROM RaceAlbum r WHERE r.albumId = :albumId"),
    @NamedQuery(name = "RaceAlbum.findByAlbumName", query = "SELECT r FROM RaceAlbum r WHERE r.albumName = :albumName"),
    @NamedQuery(name = "RaceAlbum.findByAlbumDate", query = "SELECT r FROM RaceAlbum r WHERE r.albumDate = :albumDate")})
public class RaceAlbum implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "album_id", nullable = false)
    private Integer albumId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "album_name", nullable = false, length = 50)
    private String albumName;
    @Column(name = "album_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date albumDate;

    public RaceAlbum() {
    }

    public RaceAlbum(Integer albumId) {
        this.albumId = albumId;
    }

    public RaceAlbum(Integer albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Date getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(Date albumDate) {
        this.albumDate = albumDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (albumId != null ? albumId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RaceAlbum)) {
            return false;
        }
        RaceAlbum other = (RaceAlbum) object;
        if ((this.albumId == null && other.albumId != null) || (this.albumId != null && !this.albumId.equals(other.albumId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.RaceAlbum[ albumId=" + albumId + " ]";
    }
    
}
