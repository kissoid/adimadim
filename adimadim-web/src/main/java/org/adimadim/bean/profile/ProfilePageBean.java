/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.adimadim.bean.AccountBean;
import org.adimadim.db.entity.RaceScore;
import org.adimadim.service.RacerService;
import org.adimadim.util.FacesMessageUtil;
import org.adimadim.util.ConvertionUtil;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Adem
 */
@ConversationScoped
@Named(value="profilePageBean")
public class ProfilePageBean implements Serializable{
   
    @Inject private RacerService racerService;
    @Inject private AccountBean accountBean;
    private List<RaceScore> adimadimRaceScoreList = new ArrayList<RaceScore>();
    private CartesianChartModel adimadimRaceChartModel = new CartesianChartModel();
    
    public ProfilePageBean(){
        
    }

    @PostConstruct
    private void initMethod(){
        try {
            adimadimRaceScoreList = racerService.getRacerScoresByAccountId(accountBean.getProfileAccount().getAccountId());
            adimadimRaceChartModel = createCartesianChartModel(adimadimRaceScoreList);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private CartesianChartModel createCartesianChartModel(List<RaceScore> raceScoreList) {
        CartesianChartModel cartesianChartModel = new CartesianChartModel();
        LineChartSeries series = new LineChartSeries();
        series.setLabel(accountBean.getProfileAccount().getName() + " " + accountBean.getProfileAccount().getSurname());
        for (RaceScore raceScore : raceScoreList) {
            String date = ConvertionUtil.dateToString(raceScore.getRace().getRaceDate(), "dd.MM.yyyy");
            Double duration = ConvertionUtil.timeToMinute(raceScore.getDuration());
            series.set(date, duration);
        }
        cartesianChartModel.addSeries(series);
        return cartesianChartModel;
    }
    
    public boolean isProfileOwner(){
        return accountBean.getAccount().getAccountId() == accountBean.getProfileAccount().getAccountId();
    }
    
    public List<RaceScore> getAdimadimRaceScoreList() {
        return adimadimRaceScoreList;
    }

    public void setAdimadimRaceScoreList(List<RaceScore> adimadimRaceScoreList) {
        this.adimadimRaceScoreList = adimadimRaceScoreList;
    }

    public CartesianChartModel getAdimadimRaceChartModel() {
        return adimadimRaceChartModel;
    }

    public void setAdimadimRaceChartModel(CartesianChartModel adimadimRaceChartModel) {
        this.adimadimRaceChartModel = adimadimRaceChartModel;
    }
    
}
