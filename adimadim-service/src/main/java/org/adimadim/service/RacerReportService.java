/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.adimadim.db.dto.BestEvolutionDto;
import org.adimadim.db.dto.MostRunningDto;
import org.adimadim.facade.RaceFacade;


/**
 *
 * @author Adem
 */
@Stateless
public class RacerReportService {

    @Inject
    private RaceFacade raceFacade;
    
    public List<MostRunningDto> getMostRunningList(Date startDate, Date endDate) throws Exception {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append(" select ");
        sqlString.append("     b.account_id, b.name, b.surname, b.gender, count(*) race_count  ");
        sqlString.append(" from  ");
        sqlString.append("     race_score a, account b, race c ");
        sqlString.append(" where ");
        sqlString.append("     b.account_id = a.account_id ");
        sqlString.append("     and c.race_id = a.race_id ");
        sqlString.append("     and c.race_date between ?1 and c.race_date <= ?2 ");
        sqlString.append(" group by b.name, b.surname, b.gender order by count(*) desc ");
        
        Map params = new HashMap();
        params.put(1, startDate);
        params.put(2, endDate);
        
        List<Object[]> racerList = raceFacade.findListByNativeQuery(sqlString.toString(), params);
        List<MostRunningDto> mostRunningList = new ArrayList<MostRunningDto>();
        for (Object[] object : racerList) {
            MostRunningDto mostRunning = new MostRunningDto();
            mostRunning.setAccountId(object[0] == null ? 0 : Long.valueOf(object[0].toString()).intValue());
            mostRunning.setName(object[1] == null ? "" : object[1].toString());
            mostRunning.setSurname(object[2] == null ? "" : object[2].toString());
            mostRunning.setGender(object[3] == null ? "" : object[3].toString());
            mostRunning.setRaceCount(object[4] == null ? 0 : Long.valueOf(object[4].toString()).intValue());
            mostRunningList.add(mostRunning);
        }
        return mostRunningList;
    }    
    
    public List<BestEvolutionDto> getBestEvolutionList(Date startDate, Date endDate) throws Exception {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append(" select * from ( ");
        sqlString.append("     select "); 
        sqlString.append("         account_id,  ");
        sqlString.append("         name,  ");
        sqlString.append("         surname,  ");
        sqlString.append("         gender,  ");
        sqlString.append("         first_race,  ");
        sqlString.append("         last_race, ");
        sqlString.append("         case when last_race>first_race then -1 else case when first_race>last_race then 1 else 0 end end sign, ");
        sqlString.append("         case when last_race>first_race then timediff(last_race,first_race) else timediff(first_race,last_race) end duration ");
        sqlString.append("     from ( ");
        sqlString.append("         select  ");
        sqlString.append("             a.account_id,  ");
        sqlString.append("             a.name, ");
        sqlString.append("             a.surname, ");
        sqlString.append("             a.gender, ");
        sqlString.append("             (select duration from race_score where account_id=a.account_id and race_id = (select min(x.race_id) from race x, race_score y where x.race_id=y.race_id and y.account_id=a.account_id and x.race_date between str_to_date(?1,'%d.%m.%Y') and str_to_date(?2,'%d.%m.%Y') )) first_race, ");
        sqlString.append("             (select duration from race_score where account_id=a.account_id and race_id = (select max(x.race_id) from race x, race_score y where x.race_id=y.race_id and y.account_id=a.account_id and x.race_date between str_to_date(?3,'%d.%m.%Y') and str_to_date(?4,'%d.%m.%Y') )) last_race ");
        sqlString.append("         from  ");
        sqlString.append("             account a ");
        sqlString.append("     ) x ");
        sqlString.append(" ) y  ");
        sqlString.append(" order by  ");
        sqlString.append("     y.sign desc,  ");
        sqlString.append("     y.duration desc ");
        
        Map params = new HashMap();
        params.put(1, startDate);
        params.put(2, endDate);
        params.put(3, startDate);
        params.put(4, endDate);
        
        List<Object[]> racerList = raceFacade.findListByNativeQuery(sqlString.toString(), params);
        List<BestEvolutionDto> bestEvolutionList = new ArrayList<BestEvolutionDto>();
        for (Object[] object : racerList) {
            BestEvolutionDto bestEvolution = new BestEvolutionDto();
            bestEvolution.setAccountId(object[0] == null ? 0 : Long.valueOf(object[0].toString()).intValue());
            bestEvolution.setName(object[1] == null ? "" : object[1].toString());
            bestEvolution.setSurname(object[2] == null ? "" : object[2].toString());
            bestEvolution.setGender(object[3] == null ? "" : object[3].toString());
            bestEvolution.setFirstDuration(object[4] == null ? "" : object[4].toString());
            bestEvolution.setLastDuration(object[5] == null ? "" : object[5].toString());
            bestEvolution.setSign(object[6] == null ? 0 : Long.valueOf(object[6].toString()).intValue());
            bestEvolution.setEvolution(object[7] == null ? "" : object[7].toString());
            bestEvolutionList.add(bestEvolution);
        }
        return bestEvolutionList;
    }
    
}
