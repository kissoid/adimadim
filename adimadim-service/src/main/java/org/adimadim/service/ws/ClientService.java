/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.service.ws;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author Ergo
 */
@WebService(serviceName = "ClientService")
@Stateless()
public class ClientService {
    @EJB
    private org.adimadim.service.ClientService ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"
    
}
