/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.Race;
import org.adimadim.db.entity.RaceScore;

/**
 *
 * @author Ergo
 */
@Stateless
@WebService
public class ClientExportImport {

    @Inject
    private AccountService accountService;
    @Inject
    private RaceService raceService;
    @Inject
    private RaceScoreService raceScoreService;
    
    /**
     * Web service operation
     * @return 
     */
    /*
    @WebMethod(operationName = "retrieveAccounts")
    public List<Account> retrieveAccounts() {
        try {
            return accountService.retrieveAllAccounts();
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @WebMethod(operationName = "retrieveRaces")
    public List<Race> retrieveRaces() {
        try {
            return raceService.retrieveAllRaces();
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    @WebMethod(operationName = "saveRaceScores")
    public String retrieveRaces(List<RaceScore> raceScores) {
        String result = "";
        try {
            result =  raceScoreService.saveRaceScores(raceScores);
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     * Web service operation
     * @param raceScores
     * @return 
     */
    @WebMethod(operationName = "saveRaceScores")
    public String saveRaceScores(@WebParam(name = "raceScores") List<RaceScore> raceScores) {
        String result = "";
        try {
            result =  raceScoreService.saveRaceScores(raceScores);
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "retrieveRaces")
    public List<Race> retrieveRaces() {
        try {
            return raceService.retrieveAllRaces();
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     * @param startAccountId
     * @param count
     * @return 
     */
    @WebMethod(operationName = "retrieveAccounts")
    public List<Account> retrieveAccounts(@WebParam(name = "startAccountId") Integer startAccountId, @WebParam(name = "count") Integer count) {
        try {
            List<Account> accountList = accountService.retrieveAccountRangeStartByAccountId(startAccountId, count);
            for(Account account : accountList){
                account.setPassword("*****");
            }
            return accountList;
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /*@WebMethod(operationName = "retrieveAccounts")
    public List<Account> retrieveAccounts(@WebParam(name = "start") int start, @WebParam(name = "end") int end) {
        try {
            List<Account> accountList = accountService.retrieveAccountRange(start, end);
            for(Account account : accountList){
                account.setPassword("*****");
            }
            return accountList;
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/

    /**
     * Web service operation
     * @return 
     */
    /*@WebMethod(operationName = "retrieveAccounts")
    public List<Account> retrieveAccounts() {
        try {
            List<Account> accountList = accountService.retrieveAllAccounts();
            for(Account account : accountList){
                account.setPassword("*****");
            }
            return accountList;
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/
}
