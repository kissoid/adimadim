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
import org.adimadim.db.dto.BestEvolutionDto;
import org.adimadim.service.RacerReportService;
import org.adimadim.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value="bestEvolutionBean")
public class BestEvolutionBean implements Serializable {

    @Inject
    private RacerReportService racerReportService;
    private List<BestEvolutionDto> bestEvolutionList = new ArrayList<BestEvolutionDto>();
    private Date startDate;
    private Date endDate;

    public BestEvolutionBean() {
    }

    @PostConstruct
    private void init() {
    }

    public void retrieveBestEvolutionList() {
        try {
            if (startDate == null) {
                throw new Exception("Lütfen başlangıç tarihini giriniz.");
            }
            if (endDate == null) {
                throw new Exception("Lütfen bitiş tarihini giriniz.");
            }
            bestEvolutionList = racerReportService.getBestEvolutionList(startDate, endDate);
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

    public List<BestEvolutionDto> getBestEvolutionList() {
        return bestEvolutionList;
    }

    public void setBestEvolutionList(List<BestEvolutionDto> bestEvolutionList) {
        this.bestEvolutionList = bestEvolutionList;
    }
     
}
