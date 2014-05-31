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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "account_album", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountAlbum.findAll", query = "SELECT a FROM AccountAlbum a"),
    @NamedQuery(name = "AccountAlbum.findByAlbumId", query = "SELECT a FROM AccountAlbum a WHERE a.albumId = :albumId"),
    @NamedQuery(name = "AccountAlbum.findByAlbumName", query = "SELECT a FROM AccountAlbum a WHERE a.albumName = :albumName"),
    @NamedQuery(name = "AccountAlbum.findByAlbumDate", query = "SELECT a FROM AccountAlbum a WHERE a.albumDate = :albumDate"),
    @NamedQuery(name = "AccountAlbum.findByProfileAlbum", query = "SELECT a FROM AccountAlbum a WHERE a.profileAlbum = :profileAlbum")})
public class AccountAlbum implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "profile_album", nullable = false, length = 1)
    private String profileAlbum;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @ManyToOne(optional = false)
    private Account account;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "album")
    private List<AccountAlbumPicture> accountAlbumPictureList;

    public AccountAlbum() {
    }

    public AccountAlbum(Integer albumId) {
        this.albumId = albumId;
    }

    public AccountAlbum(Integer albumId, String albumName, String profileAlbum) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.profileAlbum = profileAlbum;
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

    public String getProfileAlbum() {
        return profileAlbum;
    }

    public void setProfileAlbum(String profileAlbum) {
        this.profileAlbum = profileAlbum;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @XmlTransient
    public List<AccountAlbumPicture> getAccountAlbumPictureList() {
        return accountAlbumPictureList;
    }

    public void setAccountAlbumPictureList(List<AccountAlbumPicture> accountAlbumPictureList) {
        this.accountAlbumPictureList = accountAlbumPictureList;
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
        if (!(object instanceof AccountAlbum)) {
            return false;
        }
        AccountAlbum other = (AccountAlbum) object;
        if ((this.albumId == null && other.albumId != null) || (this.albumId != null && !this.albumId.equals(other.albumId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.AccountAlbum[ albumId=" + albumId + " ]";
    }
    
}
