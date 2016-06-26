/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.main.fragment;

import com.ergo.insyst.common.util.DateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.adimadim.common.util.FacesMessageUtil;
import org.adimadim.db.dto.TeamScoreDto;
import org.adimadim.db.entity.Race;
import org.adimadim.db.entity.RaceScore;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamMember;
import org.adimadim.service.RaceService;

/**
 *
 * @author Adem
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SessionScoped
@Named(value = "raceBean")
public class RaceBean implements Serializable {

    @Inject
    private RaceService raceService;
    private Race selectedRace;
    private Race newRace = new Race();
    private List<Race> raceList;
    private List<RaceScore> raceScoreList;
    private List<RaceScore> raceScoreWomenList;
    private List<RaceScore> raceScoreMenList;
    private Team selectedTeam;
    private Team newTeam;
    private List<TeamScoreDto> teamScoreDtoList;
    private List<RaceScore> teamScoreList;
    private List<TeamMember> teamMemberList;

    public RaceBean() {
    }

    @PostConstruct
    private void init() {
        teamScoreDtoList = new ArrayList<TeamScoreDto>();
        raceScoreWomenList = new ArrayList<RaceScore>();
        raceScoreMenList = new ArrayList<RaceScore>();
        retrieveAllRaces();
    }

    public void retrieveAllRaces() {
        try {
            raceList = raceService.retrieveAllActiveRaces();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retriveSelectedRaceScores() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir koşu seçiniz.");
            }
            raceScoreList = raceService.retrieveRaceScoreByRaceId(selectedRace.getRaceId());
            raceScoreWomenList.clear();
            raceScoreMenList.clear();
            for (RaceScore score : raceScoreList) {
                if (score.getAccount().getGender().equals("E")) {
                    raceScoreMenList.add(score);
                }
                if (score.getAccount().getGender().equals("K")) {
                    raceScoreWomenList.add(score);
                }
            }
            retriveTeamsByRaceId();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void retriveTeamsByRaceId() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir koşu seçiniz.");
            }
            List<Team> teamList = raceService.retrieveTeamsByRaceId(selectedRace.getRaceId());
            teamScoreDtoList.clear();
            List<TeamScoreDto> deficientTeamList = new ArrayList<TeamScoreDto>();
            for (Team team : teamList) {
                TeamScoreDto teamScoreDto = new TeamScoreDto();
                teamScoreDto.setTeamId(team.getTeamId());
                teamScoreDto.setTeamName(team.getTeamName());
                teamScoreDto.setCategory(team.getTeamType().getDescription());
                List<RaceScore> tempRaceScoreList = raceService.findTeamResultList(team);
                teamScoreDto.setRaceScoreList(tempRaceScoreList);
                teamScoreDto = calculateTeamDuration(teamScoreDto);
                if (teamScoreDto.getRaceScoreList().size() == 7) {
                    teamScoreDtoList.add(teamScoreDto);
                } else {
                    deficientTeamList.add(teamScoreDto);
                }
            }
            Collections.sort(teamScoreDtoList, new TeamComparator());
            Collections.sort(deficientTeamList, new TeamComparator());
            teamScoreDtoList.addAll(deficientTeamList);
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
                throw new Exception("Lütfen bir koşu seçiniz.");
            }
            if (selectedTeam == null) {
                throw new Exception("Lütfen bir takım seçiniz.");
            }
            teamScoreList = raceService.retrieveRaceScoreByRaceIdAndTeamId(selectedRace.getRaceId(), selectedTeam.getTeamId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retriveTeamMembersByRaceIdAndTeamId() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen bir koşu seçiniz.");
            }
            if (selectedTeam == null) {
                throw new Exception("Lütfen bir takım seçiniz.");
            }
            teamScoreList = raceService.retrieveTeamMembersByRaceIdAndTeamId(selectedRace.getRaceId(), selectedTeam.getTeamId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void createRace() {
        try {
            raceService.createRace(newRace);
            retrieveAllRaces();
            newRace = new Race();
            FacesMessageUtil.createFacesMessage("Koşu oluşturuldu.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void updateRace() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen güncellemek istediğiniz koşuyu seçiniz.");
            }
            raceService.updateRace(selectedRace);
            retrieveAllRaces();
            FacesMessageUtil.createFacesMessage("Koşu güncellendi.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteRace() {
        try {
            if (selectedRace == null) {
                throw new Exception("Lütfen silmek istediğiniz koşuyu seçiniz.");
            }
            raceService.deleteRace(selectedRace);
            retrieveAllRaces();
            FacesMessageUtil.createFacesMessage("Koşu silindi.", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public TeamScoreDto calculateTeamDuration(TeamScoreDto teamScoreDto) {
        try {
            long duration = 0;
            for (RaceScore raceScore : teamScoreDto.getRaceScoreList()) {
                String time = DateUtil.dateToString(raceScore.getDuration(), "hh:mm:ss");
                String[] arr = time.split(":");
                duration += Integer.parseInt(arr[2]);
                duration += 60 * Integer.parseInt(arr[1]);
                duration += 3600 * (Integer.parseInt(arr[0]) - 12);
            }
            long hh = duration / 3600;
            duration %= 3600;
            long mm = duration / 60;
            duration %= 60;
            long ss = duration;
            teamScoreDto.setDuration(format(hh) + ":" + format(mm) + ":" + format(ss));
            teamScoreDto.setDurationInTime(duration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return teamScoreDto;
    }

    private static String format(long s) {
        if (s < 10) {
            return "0" + s;
        } else {
            return "" + s;
        }
    }

    public class TeamComparator implements Comparator<TeamScoreDto> {

        @Override
        public int compare(TeamScoreDto o1, TeamScoreDto o2) {
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = DateUtil.stringToDatePattern(o1.getDuration(), "hh:mm:ss", new Locale("tr_TR"));
                date2 = DateUtil.stringToDatePattern(o2.getDuration(), "hh:mm:ss", new Locale("tr_TR"));
            } catch (ParseException parseException) {
            }
            return date1.compareTo(date2);
        }
    }

}
