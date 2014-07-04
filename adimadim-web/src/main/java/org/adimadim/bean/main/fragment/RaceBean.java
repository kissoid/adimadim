/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.main.fragment;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.adimadim.db.entity.Race;
import org.adimadim.db.entity.RaceScore;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamMember;
import org.adimadim.db.entity.TeamPK;
import org.adimadim.service.RaceService;
import org.adimadim.util.FacesMessageUtil;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value = "raceBean")
public class RaceBean implements Serializable {

    @Inject
    private RaceService raceService;
    private Race selectedRace;
    private Race newRace = new Race();
    private List<Race> raceList;
    private List<RaceScore> raceScoreList;
    private Team selectedTeam;
    private Team newTeam;
    private List<Team> teamList;
    private List<RaceScore> teamScoreList;
    private List<TeamMember> teamMemberList;

    public RaceBean() {
    }

    @PostConstruct
    private void init() {
        retrieveAllRaces();
    }

    public void retrieveAllRaces() {
        try {
            raceList = raceService.retrieveAllRaces();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retriveRaceScoreByRaceId() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir yarış seçiniz.");
            }
            raceScoreList = raceService.retrieveRaceScoreByRaceId(selectedRace.getRaceId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void retriveTeamsByRaceId() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir yarış seçiniz.");
            }
            teamList = raceService.retrieveTeamsByRaceId(selectedRace.getRaceId());
            teamScoreList = new ArrayList<RaceScore>();
            selectedTeam = null;
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retrieveTeamsAndGoToTeamPage() {
        try {
            retriveTeamsByRaceId();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/team.jsf");
        } catch (IOException ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }


    public void retriveTeamScoreByRaceIdAndTeamId() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir yarış seçiniz.");
            }
            if (selectedTeam == null) {
                throw new Exception("Lütfen bir takım seçiniz.");
            }
            teamScoreList = raceService.retrieveRaceScoreByRaceIdAndTeamId(selectedRace.getRaceId(), selectedTeam.getTeamPK().getTeamId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retriveTeamMembersByRaceIdAndTeamId() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir yarış seçiniz.");
            }
            if (selectedTeam == null) {
                throw new Exception("Lütfen bir takım seçiniz.");
            }
            teamScoreList = raceService.retrieveTeamMembersByRaceIdAndTeamId(selectedRace.getRaceId(), selectedTeam.getTeamPK().getTeamId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void createRace() {
        try {
            raceService.createRace(newRace);
            retrieveAllRaces();
            newRace = new Race();
            FacesMessageUtil.createFacesMessage("Yarış oluşturuldu.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void updateRace() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen güncellemek istediğiniz yarışı seçiniz.");
            }
            raceService.updateRace(selectedRace);
            retrieveAllRaces();
            FacesMessageUtil.createFacesMessage("Yarış güncellendi.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteRace() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen silmek istediğiniz yarışı seçiniz.");
            }
            raceService.deleteRace(selectedRace);
            retrieveAllRaces();
            FacesMessageUtil.createFacesMessage("Yarış silindi.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void createTeam() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir yarış seçiniz");
            }
            newTeam.setTeamPK(new TeamPK());
            newTeam.getTeamPK().setRaceId(selectedRace.getRaceId());
            raceService.createTeam(newTeam);
            retriveTeamsByRaceId();
            FacesMessageUtil.createFacesMessage("Yarış oluşturuldu.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void updateTeam() {
        try {
            if (selectedTeam == null) {
                throw new Exception("Lütfen güncellemek istediğiniz takımı seçiniz.");
            }
            raceService.updateTeam(selectedTeam);
            retriveTeamsByRaceId();
            FacesMessageUtil.createFacesMessage("Takım güncellendi.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteTeam() {
        try {
            if (selectedTeam == null) {
                throw new Exception("Lütfen silmek istediğiniz takımı seçiniz.");
            }
            raceService.deleteTeam(selectedTeam);
            retriveTeamsByRaceId();
            FacesMessageUtil.createFacesMessage("Takım silindi.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public List<Race> getRaceList() {
        return raceList;
    }

    public void setRaceList(List<Race> raceList) {
        this.raceList = raceList;
    }

    public Race getSelectedRace() {
        return selectedRace;
    }

    public void setSelectedRace(Race selectedRace) {
        this.selectedRace = selectedRace;
    }

    public List<RaceScore> getRaceScoreList() {
        return raceScoreList;
    }

    public void setRaceScoreList(List<RaceScore> raceScoreList) {
        this.raceScoreList = raceScoreList;
    }

    public Race getNewRace() {
        return newRace;
    }

    public void setNewRace(Race newRace) {
        this.newRace = newRace;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public List<RaceScore> getTeamScoreList() {
        return teamScoreList;
    }

    public void setTeamScoreList(List<RaceScore> teamScoreList) {
        this.teamScoreList = teamScoreList;
    }

    public Team getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public Team getNewTeam() {
        return newTeam;
    }

    public void setNewTeam(Team newTeam) {
        this.newTeam = newTeam;
    }

    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }
}
