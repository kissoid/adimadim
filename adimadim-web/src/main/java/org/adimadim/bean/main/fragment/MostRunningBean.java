/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.main.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.adimadim.db.dto.MostRunningDto;
import org.adimadim.service.RacerReportService;
import org.adimadim.common.util.ConvertionUtil;
import org.adimadim.common.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value="mostRunningBean")
public class MostRunningBean implements Serializable {

    @Inject
    private RacerReportService racerReportService;
    private List<MostRunningDto> mostRunningList;
    private List<MostRunningDto> mostRunningFilteredList;
    private Date startDate;
    private Date endDate;

    public MostRunningBean() {
        mostRunningList = new ArrayList<MostRunningDto>();
        mostRunningFilteredList = new ArrayList<MostRunningDto>();
    }

    @PostConstruct
    private void init() {

    }

    public void retrieveMostRunningList() {
        try {
            if (startDate == null) {
                throw new Exception("Lütfen başlangıç tarihini giriniz.");
            }
            if (endDate == null) {
                throw new Exception("Lütfen bitiş tarihini giriniz.");
            }
            mostRunningList = racerReportService.getMostRunningList(startDate, endDate);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<MostRunningDto> getMostRunningList() {
        return mostRunningList;
    }

    public void setMostRunningList(List<MostRunningDto> mostRunningList) {
        this.mostRunningList = mostRunningList;
    }

    public List<MostRunningDto> getMostRunningFilteredList() {
        return mostRunningFilteredList;
    }

    public void setMostRunningFilteredList(List<MostRunningDto> mostRunningFilteredList) {
        this.mostRunningFilteredList = mostRunningFilteredList;
    }
     

}
