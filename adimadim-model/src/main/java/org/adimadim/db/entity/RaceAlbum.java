/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name="race_album", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RaceAlbum.findAll", query = "SELECT a FROM RaceAlbum a"),
    @NamedQuery(name = "RaceAlbum.findAllByAlbumIdOrderDesc", query = "SELECT a FROM RaceAlbum a ORDER BY a.albumId DESC"),
    @NamedQuery(name = "RaceAlbum.findByAlbumId", query = "SELECT a FROM RaceAlbum a WHERE a.albumId = :albumId"),
    @NamedQuery(name = "RaceAlbum.findByAlbumName", query = "SELECT a FROM RaceAlbum a WHERE a.albumName = :albumName"),
    @NamedQuery(name = "RaceAlbum.findByAlbumDate", query = "SELECT a FROM RaceAlbum a WHERE a.albumDate = :albumDate")})
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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id", insertable = false, updatable = false, nullable = false)
    private List<RaceAlbumPicture> raceAlbumPictureList;
    
    public RaceAlbum() {
    }

    @PrePersist
    private void prePersistMethod(){
        if(albumDate == null){
            albumDate = new Date();
        }
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

    public List<RaceAlbumPicture> getRaceAlbumPictureList() {
        return raceAlbumPictureList;
    }

    public void setRaceAlbumPictureList(List<RaceAlbumPicture> raceAlbumPictureList) {
        this.raceAlbumPictureList = raceAlbumPictureList;
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
        return "org.adimadim.db.entity.Album[ albumId=" + albumId + " ]";
    }
    
}
