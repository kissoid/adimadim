/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "race_album_picture", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RaceAlbumPicture.findAll", query = "SELECT a FROM RaceAlbumPicture a"),
    @NamedQuery(name = "RaceAlbumPicture.findByPictureId", query = "SELECT a FROM RaceAlbumPicture a WHERE a.pictureId = :pictureId"),
    @NamedQuery(name = "RaceAlbumPicture.findByAlbumId", query = "SELECT a FROM RaceAlbumPicture a WHERE a.albumId = :albumId"),
    @NamedQuery(name = "RaceAlbumPicture.findByPictureName", query = "SELECT a FROM RaceAlbumPicture a WHERE a.pictureName = :pictureName")})
public class RaceAlbumPicture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "picture_id", nullable = false)
    private Integer pictureId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "album_id", nullable = false)
    private int albumId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "picture_name", nullable = false, length = 50)
    private String pictureName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", insertable = false, updatable = false)
    private RaceAlbum raceAlbum;
    
    public RaceAlbumPicture() {
    }

    public RaceAlbumPicture(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public RaceAlbumPicture(Integer pictureId, int albumId, String pictureName) {
        this.pictureId = pictureId;
        this.albumId = albumId;
        this.pictureName = pictureName;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public RaceAlbum getRaceAlbum() {
        return raceAlbum;
    }

    public void setRaceAlbum(RaceAlbum raceAlbum) {
        this.raceAlbum = raceAlbum;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pictureId != null ? pictureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RaceAlbumPicture)) {
            return false;
        }
        RaceAlbumPicture other = (RaceAlbumPicture) object;
        if ((this.pictureId == null && other.pictureId != null) || (this.pictureId != null && !this.pictureId.equals(other.pictureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.AlbumPicture[ pictureId=" + pictureId + " ]";
    }
    
}
