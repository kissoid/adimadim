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
import org.adimadim.thread.TeamInvitationThread;
import org.adimadim.util.FacesMessageUtil;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;

@Data
@EqualsAndHashCode(callSuper = false)
@SessionScoped
@Named(value = "teamBean")
public class TeamBean implements Serializable {

    private String name;
    private String surname;
    private Race selectedRace;
    private Team selectedTeam;
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

    }

    @PostConstruct
    private void init() {
        try {

            teamMemberList = new DualListModel<Account>(new ArrayList<Account>(), new ArrayList<Account>());
            selectedRace = raceService.findNextTeamRace();
            if (selectedRace == null) {
                FacesMessageUtil.createFacesMessage("Uyarı", "Ayarlanmış bir takım yarışı yok.", FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/");
                return;
            }
            selectedTeam = createEmptyTeam();
            teamList = teamService.findTeamsByRaceId(selectedRace.getRaceId());
            teamTypeList = teamService.findTeamTypes();
        } catch (Exception ex) {
            Logger.getLogger(TeamBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Team createEmptyTeam() {
        Team tempTeam = new Team();
        tempTeam.setTeamType(new TeamType());
        tempTeam.setTeamMemberList(new ArrayList<TeamMember>());
        tempTeam.setRace(selectedRace);
        return tempTeam;
    }

    public void searchAccount() {
        try {
            if (name != null && surname != null && name.trim().equals("") && surname.trim().equals("")) {
                return;
            }
            List<Integer> idList = createIdListFromSelectedAccounts();
            List<Account> accountList = accountService.findAccountRangeNotInListByName(name, surname, idList);
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
                    FacesMessageUtil.createFacesMessage("Bilgi", "Seçtiğiniz kişi başka bir takımda yeralıyor. Listeden başka bir koşucu seçin.", FacesMessage.SEVERITY_WARN);
                    return;
                }
                if (selectedTeam.getTeamId() != null && !teamMember.getTeam().getTeamId().equals(selectedTeam.getTeamId())) {
                    FacesMessageUtil.createFacesMessage("Bilgi", "Seçtiğiniz kişi başka bir takımda yeralıyor. Listeden başka bir koşucu seçin.", FacesMessage.SEVERITY_WARN);
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
        selectedTeam = team;
        prepareTeamCreateSectionAndGo();
    }

    public void createNewTeam() {
        try {
            selectedTeam = createEmptyTeam();
            Team tempTeam = teamService.findTeamCreator(selectedRace.getRaceId(), accountBean.getAccount().getAccountId());
            if (tempTeam != null) {
                FacesMessageUtil.createFacesMessage("Bilgi", "Bir kişi aynı yarışta sadece bir takım kurabilir.", FacesMessage.SEVERITY_WARN);
                return;
            }
            prepareTeamCreateSectionAndGo();
        } catch (Exception exception) {
        }
    }

    private void prepareTeamCreateSectionAndGo() {
        try {
            name = "";
            surname = "";
            teamMemberList.getSource().clear();
            teamMemberList.getTarget().clear();
            for (TeamMember teamMember : selectedTeam.getTeamMemberList()) {
                teamMemberList.getTarget().add(teamMember.getAccount());
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/insession/dagi/team-create.jsf");
        } catch (IOException ex) {
            Logger.getLogger(TeamBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveTeam() {
        try {
            if (selectedTeam.getAccount() == null) {
                selectedTeam.setAccount(accountBean.getAccount());
            }
            List<TeamMember> newTeamMemberList = createTeamMemberFromSelectedList();
            selectedTeam = combineMembers(selectedTeam, newTeamMemberList);

            selectedTeam = teamService.saveTeam(selectedTeam);
            FacesMessageUtil.createFacesMessage("Bilgi", "Takım kayıt edildi", FacesMessage.SEVERITY_INFO);
            teamList = teamService.findTeamsByRaceId(selectedRace.getRaceId());
            new TeamInvitationThread(selectedTeam, accountBean.getAccount()).start();
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
            teamMember.setIsApproved(accountBean.getAccount().getAccountId().equals(account.getAccountId()));
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

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("teamInfo") && event.getNewStep().equals("teamMembers")) {
            if (selectedTeam.getTeamId() == null && selectedTeam.getTeamMemberList().isEmpty()) {
                if (!teamMemberList.getTarget().contains(accountBean.getAccount())) {
                    teamMemberList.getTarget().add(accountBean.getAccount());
                }
            }
        }
        if (event.getOldStep().equals("teamMembers") && event.getNewStep().equals("teamSave")) {
            if (teamMemberList.getTarget().size() < 7) {
                FacesMessageUtil.createFacesMessage("Bilgi", "Takım kayıdınızın sonuçlanması için 7 kişi gerekiyor.", FacesMessage.SEVERITY_WARN);
            }
        }
        return event.getNewStep();
    }

}
