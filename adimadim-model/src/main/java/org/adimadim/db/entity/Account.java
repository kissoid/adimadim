/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name="account", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findAllByIdOrder", query = "SELECT a FROM Account a ORDER BY a.accountId"),
    @NamedQuery(name = "Account.findAllHasNoPasswordByIdOrder", query = "SELECT a FROM Account a where a.password is NULL ORDER BY a.accountId"),
    @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "Account.findByName", query = "SELECT a FROM Account a WHERE a.name = :name"),
    @NamedQuery(name = "Account.findBySurname", query = "SELECT a FROM Account a WHERE a.surname = :surname"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByGender", query = "SELECT a FROM Account a WHERE a.gender = :gender"),
    @NamedQuery(name = "Account.findByBirthDate", query = "SELECT a FROM Account a WHERE a.birthDate = :birthDate"),
    @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM Account a WHERE a.active = :active"),
    @NamedQuery(name = "Account.findByCreateDate", query = "SELECT a FROM Account a WHERE a.createDate = :createDate"),
    @NamedQuery(name = "Account.findBySecretKey", query = "SELECT a FROM Account a WHERE a.secretKey = :secretKey")
})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(length = 50)
    private String email;
    @Transient
    private String reEmail;
    @Size(max = 25)
    @Column(length = 25)
    private String password;
    @Transient
    private String rePassword;
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
    @Transient
    private String tempBirthDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String active;
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
    @Column(name="adimadim_run", nullable = false, length = 1)
    private String adimadimRun;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 10)
    @Column(name = "phone_code", length = 10)
    private String phoneCode;
    @Size(max = 10)
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
    @Basic(optional = false)
    @Size(max = 30)
    @Column(name = "picture", length = 30)
    private String picture;
    @Column(name = "chest_number", nullable = false)
    private Integer chestNumber;
    @Size(max = 10)
    @Column(name = "secret_key", length = 10)
    private String secretKey;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<RaceScore> raceScoreList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<TeamMember> teamMemberList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<AccountProperty> accountPropertyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<AccountAlbum> accountAlbumList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Donation> donationList;
    
    public Account() {
    }

    public Account(Integer accountId) {
        this.accountId = accountId;
    }

    @PrePersist
    private void prePersistMethod(){
        prePersistAndPreUpdateMethod();
    }

    @PreUpdate
    private void preUpdateMethod(){
        prePersistAndPreUpdateMethod();
    }
    
    private void prePersistAndPreUpdateMethod(){
        setAdimadimRun("E");
        setActive("E");
        setManager("H");
        setCreateDate(new Date());
        for(AccountProperty accountProperty: accountPropertyList){
            accountProperty.getAccountPropertyPK().setAccountId(accountId);
            accountProperty.setAccount(this);
        }
        for(AccountAlbum accountAlbum: accountAlbumList){
            accountAlbum.setAccount(this);
        }
        for(Donation donation: donationList){
            donation.setAccount(this);
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

    public String getReEmail() {
        return reEmail;
    }

    public void setReEmail(String reEmail) {
        this.reEmail = reEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
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
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getChestNumber() {
        return chestNumber;
    }

    public void setChestNumber(Integer chestNumber) {
        this.chestNumber = chestNumber;
    }

    public List<RaceScore> getRaceScoreList() {
        return raceScoreList;
    }

    public void setRaceScoreList(List<RaceScore> raceScoreList) {
        this.raceScoreList = raceScoreList;
    }

    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<AccountProperty> getAccountPropertyList() {
        return accountPropertyList;
    }

    public void setAccountPropertyList(List<AccountProperty> accountPropertyList) {
        this.accountPropertyList = accountPropertyList;
    }

    public String getTempBirthDate() {
        return tempBirthDate;
    }

    public void setTempBirthDate(String tempBirthDate) {
        this.tempBirthDate = tempBirthDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<AccountAlbum> getAccountAlbumList() {
        return accountAlbumList;
    }

    public void setAccountAlbumList(List<AccountAlbum> accountAlbumList) {
        this.accountAlbumList = accountAlbumList;
    }

    public List<Donation> getDonationList() {
        return donationList;
    }

    public void setDonationList(List<Donation> donationList) {
        this.donationList = donationList;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        return (this.accountId != null || other.accountId == null) && (this.accountId == null || this.accountId.equals(other.accountId));
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.Account[ accountId=" + accountId + " ]";
    }
    
}
