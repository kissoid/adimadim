/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.facade;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author Adem
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findRangeByNamedQuery(int[] range, String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return (List<T>) q.getResultList();
    }

    public List<T> findRangeByNamedQuery(int[] range, String namedQuery, Map parameters) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return (List<T>) q.getResultList();
    }

    public List<T> findRangeByQuery(int[] range, String query, Map parameters) {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return (List<T>) q.getResultList();
    }
    
    public T findByNamedQuery(String namedQuery, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        List<T> result = q.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return (T)result.get(0);
    }

    public List<T> findAllByNamedQuery(String namedQuery) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        return (List<T>) q.getResultList();
    }

    public T findByNamedQuery(String namedQuery, Map parameters, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List<T> result = q.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return (T)result.get(0);
    }

    public List<T> findAllByNamedQuery(String namedQuery, Map parameters) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return (List<T>) q.getResultList();
    }

    public Object findByQuery(String query, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createQuery(query);
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List result = q.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public Object findByQuery(String query, Map parameters, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List result = q.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public List findAllByQuery(String query) throws Exception {
        Query q = getEntityManager().createQuery(query);
        return q.getResultList();
    }

    public List findAllByQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return q.getResultList();
    }

    public List findAllByNativeQuery(String query) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        return q.getResultList();
    }

    public List findAllByNativeQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return q.getResultList();
    }

    public List findRangeByNativeQuery(int[] range, String query) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List findRangeByNativeQuery(int[] range, String query, Map parameters) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
