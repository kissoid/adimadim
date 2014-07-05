/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.db.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceScoreDto {

    @XmlElement
    private Integer raceScoreId;
    @XmlElement
    private Date duration;
    @XmlElement
    private Integer teamId;
    @XmlElement
    private Integer raceId;
    @XmlElement
    private Integer accountId;
    @XmlElement
    private Integer orderNo;
    
    public RaceScoreDto(){
        
    }

    public Integer getRaceScoreId() {
        return raceScoreId;
    }

    public void setRaceScoreId(Integer raceScoreId) {
        this.raceScoreId = raceScoreId;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }    

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
    
}
