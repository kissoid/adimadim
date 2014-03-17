/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.restful;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.adimadim.db.dto.DonationDto;
import org.adimadim.facade.AbstractFacade;


/**
 *
 * @author Adem
 */
@Stateless
@Path("donation")
public class DonationFacadeREST extends AbstractFacade<DonationDto> {
    @PersistenceContext(unitName = "adimadimPU")
    private EntityManager em;

    public DonationFacadeREST() {
        super(DonationDto.class);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<DonationDto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<DonationDto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<DonationDto> donationList = super.findRange(new int[]{from, to});
        return donationList;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
