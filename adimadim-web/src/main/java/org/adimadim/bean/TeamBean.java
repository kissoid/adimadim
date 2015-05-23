/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

/**
 *
 * @author Adem
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.Race;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamMember;
import org.adimadim.db.entity.TeamType;
import org.adimadim.service.AccountService;
import org.adimadim.service.RaceService;
import org.adimadim.service.TeamService;
import org.adimadim.util.FacesMessageUtil;
import org.primefaces.model.DualListModel;

@Data
@EqualsAndHashCode(callSuper = false)
@SessionScoped
@Named(value = "teamBean")
public class TeamBean implements Serializable {

    private String searchText;
    private Team selectedTeam;
    private Account selectedAccount;
    private List<Team> teamList;
    private List<TeamType> teamTypeList;
    private DualListModel<Account> teamMemberList;
    @Inject
    private TeamService teamService;
    @Inject
    private AccountService accountService;
    @Inject
    private RaceService raceService;
    @Inject
    private AccountBean accountBean;

    public TeamBean() {
        selectedAccount = new Account();
        selectedTeam = new Team();
        selectedTeam.setTeamType(new TeamType());
        selectedTeam.setTeamMemberList(new ArrayList<TeamMember>());
        teamMemberList = new DualListModel<Account>(new ArrayList<Account>(), new ArrayList<Account>());
    }

    @PostConstruct
    private void init() {
        try {
            if (accountBean.isUserSignedIn()) {
                addSelectedAccount(accountBean.getAccount());
            }
            Race race = raceService.retrieveRace(1);
            selectedTeam.setRace(race);
            teamList = teamService.findTeamsByRaceId(1);
            teamTypeList = teamService.findTeamTypes();
        } catch (Exception ex) {
            Logger.getLogger(TeamBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchAccount() {
        try {
            if (searchText.trim().equals("")) {
                return;
            }
            List<Integer> idList = createIdListFromSelectedAccounts();
            List<Account> accountList = accountService.findAccountRangeNotInListByName(searchText, idList);
            teamMemberList.setSource(accountList);
        } catch (Exception ex) {
            Logger.getLogger(TeamBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Integer> createIdListFromSelectedAccounts() {
        List<Integer> idList = new ArrayList<Integer>();
        for (Account account : teamMemberList.getTarget()) {
            if (!idList.contains(account.getAccountId())) {
                idList.add(account.getAccountId());
            }
        }
        return idList;
    }

    public void addSelectedAccount(Account account) {
        try {
            if (teamMemberList.getTarget().size() == 7) {
                FacesMessageUtil.createFacesMessage("Bilgi", "En fazla 7 kişi seçebilirsiniz", FacesMessage.SEVERITY_WARN);
                return;
            }
            
            TeamMember teamMember = teamService.findTeamMemberByAccountIdRaceId(account.getAccountId(), selectedTeam.getRace().getRaceId());
            if (teamMember != null) {
                if (selectedTeam.getTeamId() == null) {
                    FacesMessageUtil.createFacesMessage("Bilgi", "Seçtiğiniz kişi başka bir takımda yarışmaktadır.", FacesMessage.SEVERITY_WARN);
                    return;
                }
                if (selectedTeam.getTeamId() != null && !teamMember.getTeam().getTeamId().equals(selectedTeam.getTeamId())) {
                    FacesMessageUtil.createFacesMessage("Bilgi", "Seçtiğiniz kişi başka bir takımda yarışmaktadır.", FacesMessage.SEVERITY_WARN);
                    return;
                }
            }
            
            teamMemberList.getTarget().add(account);
            teamMemberList.getSource().remove(account);
        } catch (Exception exception) {
            FacesMessageUtil.createFacesMessage("Bilgi", "Kişi takıma eklenirken hata oluştu", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void removeSelectedAccount(Account account) {
        if (account.getAccountId().equals(accountBean.getAccount().getAccountId())) {
            FacesMessageUtil.createFacesMessage("Bilgi", "Takım kurucusu listeden çıkarılamaz", FacesMessage.SEVERITY_WARN);
            return;
        }
        teamMemberList.getSource().add(account);
        teamMemberList.getTarget().remove(account);
    }

    public void editSelectedTeam(Team team) {
        try {
            selectedTeam = team;
            FacesContext.getCurrentInstance().getExternalContext().redirect("/dagi/team-create.jsf");
        } catch (IOException ex) {
            Logger.getLogger(TeamBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveTeam() {
        try {
            if (selectedTeam.getAccount() == null) {
                Account account = accountService.findAccount(664);
                selectedTeam.setAccount(account);
            }
            List<TeamMember> newTeamMemberList = createTeamMemberFromSelectedList();
            selectedTeam = combineMembers(selectedTeam, newTeamMemberList);

            selectedTeam = teamService.saveTeam(selectedTeam);
            FacesMessageUtil.createFacesMessage("Bilgi", "Takım kayıt edildi", FacesMessage.SEVERITY_INFO);
            teamList = teamService.findTeamsByRaceId(1);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Bilgi", "Takım kayıt edilirken hata oluştu", FacesMessage.SEVERITY_ERROR);
        }

    }

    private List<TeamMember> createTeamMemberFromSelectedList() throws Exception {
        List<TeamMember> tempMemberList = new ArrayList<TeamMember>();
        for (Account account : teamMemberList.getTarget()) {
            TeamMember teamMember = new TeamMember();
            teamMember.setAccount(account);
            teamMember.setTeam(selectedTeam);
            teamMember.setIsApproved(false);
            tempMemberList.add(teamMember);
        }
        return tempMemberList;
    }

    private Team combineMembers(Team team, List<TeamMember> tempMemberList) {
        for (TeamMember newTeamMember : tempMemberList) {
            if (!findMemberInList(newTeamMember, team.getTeamMemberList())) {
                team.getTeamMemberList().add(newTeamMember);
            }
        }

        Iterator<TeamMember> iterator = team.getTeamMemberList().iterator();
        while (iterator.hasNext()) {
            TeamMember existsTeamMember = iterator.next();
            if (!findMemberInList(existsTeamMember, tempMemberList)) {
                iterator.remove();
            }
        }
        return team;
    }

    private Boolean findMemberInList(TeamMember teamMember, List<TeamMember> tempMemberList) {
        for (TeamMember tempTeamMember : tempMemberList) {
            if (teamMember.getAccount().getAccountId() == null) {
                return false;
            }
            if (tempTeamMember.getAccount().getAccountId() == null) {
                return false;
            }
            if (tempTeamMember.getAccount().getAccountId().equals(teamMember.getAccount().getAccountId())) {
                return true;
            }
        }
        return false;
    }

}
