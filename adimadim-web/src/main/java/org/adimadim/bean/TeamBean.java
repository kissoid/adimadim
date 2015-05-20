/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

/**
 *
 * @author Adem
 */
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamType;
import org.adimadim.service.TeamService;



@SessionScoped
@Named(value="teamBean")
public class TeamBean implements Serializable{

    private Team selectedTeam;
    private Account selectedAccount;
    private List<Team> teamList;
    private List<TeamType> teamTypeList;
    @Inject
    private TeamService teamService;
    
    public TeamBean() {
        selectedAccount = new Account();
        selectedTeam = new Team();
        selectedTeam.setTeamType(new TeamType());
    }
    
    @PostConstruct
    private void init(){
        try {
            teamList = teamService.retrieveTeamsByRaceId(1);
            teamTypeList = null;//team type listesi getirmeli
        } catch (Exception ex) {
            Logger.getLogger(TeamBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Team getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public List<TeamType> getTeamTypeList() {
        return teamTypeList;
    }

    public void setTeamTypeList(List<TeamType> teamTypeList) {
        this.teamTypeList = teamTypeList;
    }

    
    
}
