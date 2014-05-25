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
import org.adimadim.db.dto.MostRunningDto;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.RaceScore;
import org.adimadim.service.RacerService;
import org.adimadim.service.RacerReportService;
import org.adimadim.util.ConvertionUtil;
import org.adimadim.util.FacesMessageUtil;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value="racerBean")
public class RacerBean implements Serializable {

    @Inject
    private RacerService racerService;
    @Inject
    private RacerReportService racerReportService;
    private Account selectedRacer = new Account();
    private List<Account> racerList = new ArrayList<Account>();
    private List<RaceScore> racerScoreList = new ArrayList<RaceScore>();
    private List<MostRunningDto> mostRunningList = new ArrayList<MostRunningDto>();
    private List<BestEvolutionDto> bestEvolutionList = new ArrayList<BestEvolutionDto>();
    private BestEvolutionDto selectedBestEvolution = new BestEvolutionDto();
    private CartesianChartModel linearModel = new CartesianChartModel();
    private Date startDate;
    private Date endDate;

    public RacerBean() {
    }

    @PostConstruct
    private void init() {
        retriveAllRacers();
    }

    public void retriveAllRacers() {
        try {
            racerList = racerService.retrieveAllRacers();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retrieveRacerStatistics() {
        try {
            if (selectedRacer == null) {
                throw new Exception("Lütfen bir kişi seçiniz.");
            }
            retriveRacerScoresAndGrapsById(selectedRacer.getAccountId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retrieveRacerEvolution() {
        try {
            if (selectedBestEvolution == null) {
                throw new Exception("Lütfen bir kişi seçiniz.");
            }
            retriveRacerScoresAndGrapsById(selectedBestEvolution.getAccountId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void retriveRacerScoresAndGrapsById(Integer accountId) throws Exception {
        racerScoreList = racerService.getRacerScoresByAccountId(accountId);
        createCartesianChartModel();
    }

    private void createCartesianChartModel() {
        linearModel = new CartesianChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel(selectedRacer.getName() + " " + selectedRacer.getSurname());
        for (RaceScore raceScore : racerScoreList) {
            //String date = ConvertionUtil.dateToString(raceScore.getRace().getRaceDate(), "dd.MM.yyyy");
            Double duration = ConvertionUtil.timeToMinute(raceScore.getDuration());
            //series1.set(date, duration);
        }
        linearModel.addSeries(series1);
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

    public List<Account> getRacerList() {
        return racerList;
    }

    public void setRacerList(List<Account> racerList) {
        this.racerList = racerList;
    }

    public Account getSelectedRacer() {
        return selectedRacer;
    }

    public void setSelectedRacer(Account selectedRacer) {
        this.selectedRacer = selectedRacer;
    }

    public List<RaceScore> getRacerScoreList() {
        return racerScoreList;
    }

    public void setRacerScoreList(List<RaceScore> racerScoreList) {
        this.racerScoreList = racerScoreList;
    }

    public CartesianChartModel getLinearModel() {
        return linearModel;
    }

    public void setLinearModel(CartesianChartModel linearModel) {
        this.linearModel = linearModel;
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

    public List<BestEvolutionDto> getBestEvolutionList() {
        return bestEvolutionList;
    }

    public void setBestEvolutionList(List<BestEvolutionDto> bestEvolutionList) {
        this.bestEvolutionList = bestEvolutionList;
    }

    public BestEvolutionDto getSelectedBestEvolution() {
        return selectedBestEvolution;
    }

    public void setSelectedBestEvolution(BestEvolutionDto selectedBestEvolution) {
        this.selectedBestEvolution = selectedBestEvolution;
    }
      
}
