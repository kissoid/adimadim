/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.adimadim.db.entity.RaceAlbum;
import org.adimadim.db.entity.RaceAlbumPicture;
import org.adimadim.facade.RaceAlbumFacade;
import org.adimadim.facade.RaceAlbumPictureFacade;
import org.adimadim.service.exception.RaceAlbumException;

/**
 *
 * @author Adem
 */
@Stateless
public class RacePictureService {

    @Inject
    private RaceAlbumFacade raceAlbumFacade;
    @Inject
    private RaceAlbumPictureFacade raceAlbumPictureFacade;

    public RacePictureService() {
    }

    public List<RaceAlbum> retrieveAllRaceAlbums() throws Exception{
        return raceAlbumFacade.findListByNamedQuery("RaceAlbum.findAllByAlbumIdOrderDesc");
    }   
    
    public List<RaceAlbumPicture> getRaceAlbumPictureByAlbumId(Integer albumId) throws Exception{
        Map map = new HashMap();
        map.put("albumId", albumId);
        return raceAlbumPictureFacade.findListByNamedQuery("RaceAlbumPicture.findByAlbumId", map);
    }
    
    public void saveRaceAlbumPicture(RaceAlbumPicture raceAlbumPicture) throws Exception{
        raceAlbumPictureFacade.save(raceAlbumPicture);
    }
    
    public void createRaceAlbum(RaceAlbum raceAlbum) throws Exception{
        raceAlbumFacade.save(raceAlbum);
    }

    public void deleteRaceAlbum(RaceAlbum raceAlbum) throws Exception{
        if(racePictureCountByRaceId(raceAlbum.getAlbumId()) > 0){
            throw new RaceAlbumException("Bu albümde resimler olduğu için silinemez");
        }
        raceAlbumFacade.remove(raceAlbum);
    }
    
    public void deleteRaceAlbumPicture(RaceAlbumPicture raceAlbumPicture) throws RaceAlbumException, Exception{
        raceAlbumPictureFacade.remove(raceAlbumPicture);
    }
    
    private Integer racePictureCountByRaceId(Integer albumId) throws Exception{
        Map map = new HashMap();
        map.put("albumId", albumId);
        String jpqlString = "select count(r) from RaceAlbumPicture r where r.albumId = :albumId";
        Long raceScoreCount = (Long)raceAlbumPictureFacade.findValueByQuery(jpqlString, map, null);
        raceScoreCount = (raceScoreCount == null ? 0 : raceScoreCount);
        return raceScoreCount.intValue();
    }
}
