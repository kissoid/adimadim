/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.validator;

import org.adimadim.db.entity.Race;
import org.adimadim.service.exception.RaceException;

/**
 *
 * @author Adem
 */
public class RaceValidator extends AbstractValidator{
    
    public synchronized static void validateRaceForCreation(Race race) throws RaceException {
        if (isStringEmpty(race.getRaceName())) {
            throw new RaceException("Yarış adı boş olamaz.");
        }
        if (race.getRaceDate() == null) {
            throw new RaceException("Yarış tarihi boş olamaz.");
        }
    }
    
}
