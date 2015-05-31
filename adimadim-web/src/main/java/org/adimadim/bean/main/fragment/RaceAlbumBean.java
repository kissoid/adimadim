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
import org.adimadim.db.entity.RaceAlbum;
import org.adimadim.db.entity.RaceAlbumPicture;
import org.adimadim.service.RacePictureService;
import org.adimadim.common.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value = "raceAlbumBean")
public class RaceAlbumBean implements Serializable {

    @Inject
    private RacePictureService racePictureService;
    private List<RaceAlbum> raceAlbumList = new ArrayList<RaceAlbum>();
    private RaceAlbum selectedAlbum = new RaceAlbum();
    private List<RaceAlbumPicture> raceAlbumPictureList = new ArrayList<RaceAlbumPicture>();

    public RaceAlbumBean() {
    }

    @PostConstruct
    private void init() {
        retrieveAllRaceAlbums();
    }

    public void retrieveAllRaceAlbums() {
        try {
            raceAlbumList = racePictureService.retrieveAllRaceAlbums();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retrieveSelectedRaceAlbumPictures() {
        if (selectedAlbum == null) {
            return;
        }
        try {
            raceAlbumPictureList = racePictureService.getRaceAlbumPictureByAlbumId(selectedAlbum.getAlbumId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public List<RaceAlbum> getRaceAlbumList() {
        return raceAlbumList;
    }

    public void setRaceAlbumList(List<RaceAlbum> raceAlbumList) {
        this.raceAlbumList = raceAlbumList;
    }

    public RaceAlbum getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setSelectedAlbum(RaceAlbum selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }

    public List<RaceAlbumPicture> getRaceAlbumPictureList() {
        return raceAlbumPictureList;
    }

    public void setRaceAlbumPictureList(List<RaceAlbumPicture> raceAlbumPictureList) {
        this.raceAlbumPictureList = raceAlbumPictureList;
    }
    
    
}
