/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.main.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.adimadim.db.entity.Announcement;
import org.adimadim.service.AnnouncementService;
import org.adimadim.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value="discussionBean")
public class DiscussionBean implements Serializable {

    @Inject private AnnouncementService announcementService;
    private List<Announcement> announcementList = new ArrayList<Announcement>();

    public DiscussionBean() {
    }
    
    @PostConstruct
    private void init(){
        retrieveAllAnnouncements();
    }

    private void retrieveAllAnnouncements(){
        try {
            announcementList = announcementService.retrieveAllAnnouncements();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

}
