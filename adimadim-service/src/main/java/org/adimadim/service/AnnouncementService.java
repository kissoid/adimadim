/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.adimadim.db.entity.Announcement;
import org.adimadim.facade.AnnouncementFacade;

/**
 *
 * @author Adem
 */
@Stateless
public class AnnouncementService {

    @Inject
    private AnnouncementFacade announcementFacade;

    public List<Announcement> retrieveAllAnnouncements() throws Exception {
        return announcementFacade.findAllByNamedQuery("Announcement.findAllByAnnounceIdOrderDesc", null);
    }
    
}
