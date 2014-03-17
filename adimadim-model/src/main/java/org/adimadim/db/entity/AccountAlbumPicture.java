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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name = "account_album_picture", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountAlbumPicture.findAll", query = "SELECT a FROM AccountAlbumPicture a"),
    @NamedQuery(name = "AccountAlbumPicture.findByPictureId", query = "SELECT a FROM AccountAlbumPicture a WHERE a.pictureId = :pictureId"),
    @NamedQuery(name = "AccountAlbumPicture.findByPictureName", query = "SELECT a FROM AccountAlbumPicture a WHERE a.pictureName = :pictureName")})
public class AccountAlbumPicture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "picture_id", nullable = false)
    private Integer pictureId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "picture_name", nullable = false, length = 50)
    private String pictureName;
    @JoinColumn(name = "album_id", referencedColumnName = "album_id", nullable = false)
    @ManyToOne(optional = false)
    private AccountAlbum album;

    public AccountAlbumPicture() {
    }

    public AccountAlbumPicture(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public AccountAlbumPicture(Integer pictureId, String pictureName) {
        this.pictureId = pictureId;
        this.pictureName = pictureName;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public AccountAlbum getAlbum() {
        return album;
    }

    public void setAlbum(AccountAlbum album) {
        this.album = album;
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
        if (!(object instanceof AccountAlbumPicture)) {
            return false;
        }
        AccountAlbumPicture other = (AccountAlbumPicture) object;
        if ((this.pictureId == null && other.pictureId != null) || (this.pictureId != null && !this.pictureId.equals(other.pictureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.AccountAlbumPicture[ pictureId=" + pictureId + " ]";
    }
    
}
