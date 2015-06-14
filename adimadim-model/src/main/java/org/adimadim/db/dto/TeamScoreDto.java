/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.db.dto;

import java.util.List;
import java.util.Objects;
import org.adimadim.db.entity.RaceScore;

/**
 *
 * @author Mehmet Adem ŞENGÜL
 */
public class TeamScoreDto {

    private Integer teamId;
    private String teamName;
    private String duration;
    private String category;
    private long durationInTime;
    private List<RaceScore> raceScoreList;

    public TeamScoreDto() {
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<RaceScore> getRaceScoreList() {
        return raceScoreList;
    }

    public void setRaceScoreList(List<RaceScore> raceScoreList) {
        this.raceScoreList = raceScoreList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getDurationInTime() {
        return durationInTime;
    }

    public void setDurationInTime(long durationInTime) {
        this.durationInTime = durationInTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.teamId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TeamScoreDto other = (TeamScoreDto) obj;
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamScoreDto{" + "teamId=" + teamId + '}';
    }

}
