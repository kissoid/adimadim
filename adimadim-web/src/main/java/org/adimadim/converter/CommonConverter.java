/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.converter;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.WeakHashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ersin Türetkan
 */

@FacesConverter(value = "commonConverter")
public class CommonConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        WeakHashMap<Object, String> entities = getCachedMap();
        for (Entry<Object, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        WeakHashMap<Object, String> entities = getCachedMap();
        synchronized (entities) {
            if (!entities.containsKey(entity)) {
                String uuid = UUID.randomUUID().toString();
                entities.put(entity, uuid);
                return uuid;
            } else {
                return entities.get(entity);
            }
        }
    }

    @SuppressWarnings("unchecked")
	private WeakHashMap<Object, String> getCachedMap(){
        WeakHashMap<Object, String> map = (WeakHashMap<Object, String>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("converterCacheMap");
        if(map == null){
            map = new WeakHashMap<Object, String>();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("converterCacheMap", map);
        }
        return map;
    }
    
}