/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.adimadim.db.entity.AccountAlbum;
import org.adimadim.db.entity.AccountAlbumPicture;
import org.adimadim.facade.AccountAlbumFacade;
import org.adimadim.facade.AccountAlbumPictureFacade;

/**
 *
 * @author Adem
 */
@Stateless
public class AccountPictureService {

    @Inject
    private AccountAlbumFacade accountAlbumFacade;
    @Inject
    private AccountAlbumPictureFacade accountAlbumPictureFacade;

    public AccountPictureService() {
    }

    public AccountAlbum getProfileAlbumByAccountId(Integer accountId) throws Exception{
        Map map = new HashMap();
        map.put("accountId", accountId);
        map.put("profileAlbum", "E");
        return accountAlbumFacade.findByNamedQuery("AccountAlbum.findByAccountIdProfileAlbum", map, null);
    }
    
    public void saveAccountAlbumPicture(AccountAlbumPicture accountAlbumPicture){
        if(accountAlbumPicture.getPictureId() == null){
            accountAlbumPictureFacade.save(accountAlbumPicture);
        } else {
            accountAlbumPictureFacade.update(accountAlbumPicture);
        }
    }
    

}
