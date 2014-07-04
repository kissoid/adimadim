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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ergo
 */
@Entity
@Table(catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "Account.findByName", query = "SELECT a FROM Account a WHERE a.name = :name"),
    @NamedQuery(name = "Account.findBySurname", query = "SELECT a FROM Account a WHERE a.surname = :surname"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByGender", query = "SELECT a FROM Account a WHERE a.gender = :gender"),
    @NamedQuery(name = "Account.findByBirthDate", query = "SELECT a FROM Account a WHERE a.birthDate = :birthDate"),
    @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM Account a WHERE a.active = :active"),
    @NamedQuery(name = "Account.findByCreateDate", query = "SELECT a FROM Account a WHERE a.createDate = :createDate"),
    @NamedQuery(name = "Account.findByManager", query = "SELECT a FROM Account a WHERE a.manager = :manager"),
    @NamedQuery(name = "Account.findByAdimadim", query = "SELECT a FROM Account a WHERE a.adimadim = :adimadim"),
    @NamedQuery(name = "Account.findByAdimadimRun", query = "SELECT a FROM Account a WHERE a.adimadimRun = :adimadimRun"),
    @NamedQuery(name = "Account.findByPhoneNumber", query = "SELECT a FROM Account a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Account.findByPicture", query = "SELECT a FROM Account a WHERE a.picture = :picture"),
    @NamedQuery(name = "Account.findByChestNumber", query = "SELECT a FROM Account a WHERE a.chestNumber = :chestNumber"),
    @NamedQuery(name = "Account.findBySecretKey", query = "SELECT a FROM Account a WHERE a.secretKey = :secretKey"),
    @NamedQuery(name = "Account.findByEmailOrUserName", query = "SELECT a FROM Account a WHERE a.email = :searchText or a.userName = :searchText"),
})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Integer accountId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false, length = 25)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false, length = 25)
    private String surname;
    @Size(max = 50)
    @Column(length = 50)
    private String email;
    @Size(max = 25)
    @Column(length = 25)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String manager;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String adimadim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "adimadim_run", nullable = false, length = 1)
    private String adimadimRun;
    @Size(max = 15)
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Size(max = 30)
    @Column(length = 30)
    private String picture;
    @Column(name = "chest_number")
    private Integer chestNumber;
    @Size(max = 50)
    @Column(name = "user_name", length = 50)
    private String userName;
    @Size(max = 50)
    @Column(name = "secret_key", length = 50)
    private String secretKey;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<RaceScore> raceScoreList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<AccountAlbum> accountAlbumList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<AccountProperty> accountPropertyList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private AccountParent accountParent;
    @OneToMany(mappedBy = "account")
    private List<ContactMessage> contactMessageList;
    @Transient
    private String rePassword;
    @Transient
    private String tempBirthDate;
    @Transient
    private String reEmail;
    @Transient
    private String reUserName;

    public Account() {
    }

    public Account(Integer accountId) {
        this.accountId = accountId;
    }

    @PrePersist
    @PreUpdate
    private void prePersistMethod() {
        if (accountParent != null) {
            accountParent.setAccount(this);
        }
        if (accountPropertyList != null) {
            for (AccountProperty accountProperty : accountPropertyList) {
                accountProperty.setAccount(this);
            }
        }
        if (accountAlbumList != null) {
            for (AccountAlbum accountAlbum : accountAlbumList) {
                accountAlbum.setAccount(this);
            }
        }
        if (contactMessageList != null) {
            for (ContactMessage contactMessage : contactMessageList) {
                contactMessage.setAccount(this);
            }
        }
        if (manager == null || manager.equals("")) {
            setManager("H");
        }
        if (active == null || active.equals("")) {
            setActive("E");
        }
        if (adimadim == null || adimadim.equals("")) {
            setAdimadim("H");
        }
        if (adimadimRun == null || adimadimRun.equals("")) {
            setAdimadimRun("E");
        }
        if (createDate == null) {
            setCreateDate(new Date());
        }
        if (picture == null) {
            setPicture("default.png");
        }
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAdimadim() {
        return adimadim;
    }

    public void setAdimadim(String adimadim) {
        this.adimadim = adimadim;
    }

    public String getAdimadimRun() {
        return adimadimRun;
    }

    public void setAdimadimRun(String adimadimRun) {
        this.adimadimRun = adimadimRun;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getChestNumber() {
        return chestNumber;
    }

    public void setChestNumber(Integer chestNumber) {
        this.chestNumber = chestNumber;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @XmlTransient
    public List<RaceScore> getRaceScoreList() {
        return raceScoreList;
    }

    public void setRaceScoreList(List<RaceScore> raceScoreList) {
        this.raceScoreList = raceScoreList;
    }

    @XmlTransient
    public List<AccountAlbum> getAccountAlbumList() {
        return accountAlbumList;
    }

    public void setAccountAlbumList(List<AccountAlbum> accountAlbumList) {
        this.accountAlbumList = accountAlbumList;
    }

    @XmlTransient
    public List<AccountProperty> getAccountPropertyList() {
        return accountPropertyList;
    }

    public void setAccountPropertyList(List<AccountProperty> accountPropertyList) {
        this.accountPropertyList = accountPropertyList;
    }

    public AccountParent getAccountParent() {
        return accountParent;
    }

    public void setAccountParent(AccountParent accountParent) {
        this.accountParent = accountParent;
    }

    @XmlTransient
    public List<ContactMessage> getContactMessageList() {
        return contactMessageList;
    }

    public void setContactMessageList(List<ContactMessage> contactMessageList) {
        this.contactMessageList = contactMessageList;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getTempBirthDate() {
        return tempBirthDate;
    }

    public void setTempBirthDate(String tempBirthDate) {
        this.tempBirthDate = tempBirthDate;
    }

    public String getReEmail() {
        return reEmail;
    }

    public void setReEmail(String reEmail) {
        this.reEmail = reEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReUserName() {
        return reUserName;
    }

    public void setReUserName(String reUserName) {
        this.reUserName = reUserName;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.Account[ accountId=" + accountId + " ]";
    }

}
