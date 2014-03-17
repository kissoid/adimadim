/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.generic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author Adem
 */
public abstract class GenericAbstractFacade {

    public GenericAbstractFacade() {
    }

    protected abstract EntityManager getEntityManager();

    public void create(Object entity) {
        getEntityManager().persist(entity);
    }

    public void edit(Object entity) {
        getEntityManager().merge(entity);
    }

    public void remove(Object entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public Object find(Class entityClass, Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<Object> findAll(Class entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<Object> findRange(Class entityClass, int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public Object findByNamedQuery(String namedQuery, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNamedQuery(namedQuery);
        return (Object)q.getSingleResult();
    }

    public Object findAllByNamedQuery(String namedQuery, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNamedQuery(namedQuery);
        return q.getResultList();
    }

    public Object findByNamedQuery(String namedQuery, Map parameters, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return (Object)q.getSingleResult();
    }
    
    public List<Object> findByNamedQueryWithRange(String namedQuery, Map parameters, LockModeType lockModeType, int[] range) throws Exception{
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return (List<Object>)q.getResultList();
    }
    
    public Object findAllByNamedQuery(String namedQuery, Map parameters, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return q.getResultList();
    }

    public Object findByQuery(String query, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createQuery(query);
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return q.getSingleResult();
    }
    
    public Object findByQuery(String query, Map parameters, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return q.getSingleResult();
    }

    public List findAllByQuery(String query, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createQuery(query);
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return q.getResultList();
    }
    
    public List findAllByQuery(String query, Map parameters, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return q.getResultList();
    }
    
    public List findAllByNativeQuery(String query, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNativeQuery(query);
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return q.getResultList();
    }
    
    public List findAllByNativeQuery(String query, Map parameters, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNativeQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return q.getResultList();
    }
    
    public int count(Class entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Object> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
}
