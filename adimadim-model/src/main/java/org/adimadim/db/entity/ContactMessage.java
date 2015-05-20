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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "contact_message", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactMessage.findAll", query = "SELECT c FROM ContactMessage c"),
    @NamedQuery(name = "ContactMessage.findByMessageId", query = "SELECT c FROM ContactMessage c WHERE c.messageId = :messageId"),
    @NamedQuery(name = "ContactMessage.findByTitle", query = "SELECT c FROM ContactMessage c WHERE c.title = :title"),
    @NamedQuery(name = "ContactMessage.findByMessage", query = "SELECT c FROM ContactMessage c WHERE c.message = :message"),
    @NamedQuery(name = "ContactMessage.findByContactInfo", query = "SELECT c FROM ContactMessage c WHERE c.contactInfo = :contactInfo"),
    @NamedQuery(name = "ContactMessage.findByCreateDate", query = "SELECT c FROM ContactMessage c WHERE c.createDate = :createDate"),
    @NamedQuery(name = "ContactMessage.findByUnread", query = "SELECT c FROM ContactMessage c WHERE c.unread = :unread")})
public class ContactMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "message_id", nullable = false)
    private Integer messageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(nullable = false, length = 1000)
    private String message;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "contact_info", nullable = false, length = 50)
    private String contactInfo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String unread;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne
    private Account account;

    public ContactMessage() {
    }

    public ContactMessage(Integer messageId) {
        this.messageId = messageId;
    }

    public ContactMessage(Integer messageId, String title, String message, String contactInfo, Date createDate, String unread) {
        this.messageId = messageId;
        this.title = title;
        this.message = message;
        this.contactInfo = contactInfo;
        this.createDate = createDate;
        this.unread = unread;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
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
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactMessage)) {
            return false;
        }
        ContactMessage other = (ContactMessage) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.db.entity.ContactMessage[ messageId=" + messageId + " ]";
    }
    
}
