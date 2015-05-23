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
import javax.persistence.criteria.CriteriaQuery;

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

    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    public T saveAndReturn(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    public T updateAndReturn(T entity) {
        entity = getEntityManager().merge(entity);
        getEntityManager().flush();
        return entity;
    }

    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    public void removeEntityFromCache() {
        getEntityManager().getEntityManagerFactory().getCache().evict(entityClass);
    }

    public void clearAllCache() {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    public void refreshAndRemove(T entity) {
        getEntityManager().refresh(entity);
        getEntityManager().remove(entity);
    }

    public void mergeAndRemove(T entity) {
        entity = getEntityManager().merge(entity);
        getEntityManager().remove(entity);
    }

    public void flush() {
        getEntityManager().flush();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findRangeByNamedQuery(int[] range, String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
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
        return q.getResultList();
    }

    public List<T> findRangeByQuery(int[] range, String query) {
        Query q = getEntityManager().createQuery(query);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
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
        return q.getResultList();
    }

    public T findByNamedQuery(String namedQuery, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List<T> tempList = (List<T>) q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public List<T> findListByNamedQuery(String namedQuery) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        return q.getResultList();
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
        List<T> tempList = (List<T>) q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public T findByNamedQuery(String namedQuery, Map parameters) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        List<T> tempList = (List<T>) q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public List<T> findListByNamedQuery(String namedQuery, Map parameters) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return q.getResultList();
    }

    public T findByQuery(String query) throws Exception {
        Query q = getEntityManager().createQuery(query);
        List<T> tempList = (List<T>) q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public T findByQuery(String query, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createQuery(query);
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List<T> tempList = (List<T>) q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public T findByQuery(String query, Map parameters, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List<T> tempList = (List<T>) q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public T findByQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        List<T> tempList = (List<T>) q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public Object findValueByQuery(String query, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createQuery(query);
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }

        List tempList = q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public Object findValueByQuery(String query) throws Exception {
        Query q = getEntityManager().createQuery(query);
        List tempList = q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public Object findValueByQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createQuery(query);
        if (parameters != null) {
            Iterator iterator = parameters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                q.setParameter(entry.getKey().toString(), entry.getValue());
            }
        }

        List tempList = q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public Object findValueByQuery(String query, Map parameters, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createQuery(query);
        if (parameters != null) {
            Iterator iterator = parameters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                q.setParameter(entry.getKey().toString(), entry.getValue());
            }
        }
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List tempList = q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public List findValueListByQuery(String query) throws Exception {
        Query q = getEntityManager().createQuery(query);
        return q.getResultList();
    }

    public List findValueListByQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return q.getResultList();
    }

    public List<T> findListByQuery(String query) throws Exception {
        Query q = getEntityManager().createQuery(query);
        return q.getResultList();
    }

    public List<T> findListByQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return q.getResultList();
    }

    public List findListByNativeQuery(String query) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        return q.getResultList();
    }

    public List findListByNativeQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        if (parameters != null) {
            Iterator iterator = parameters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                q.setParameter(entry.getKey().toString(), entry.getValue());
            }
        }
        return q.getResultList();
    }

    public Object findByNativeQuery(String query, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List tempList = q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
    }

    public Object findByNativeQuery(String query, Map parameters, LockModeType lockModeType) throws Exception {
        Query q = getEntityManager().createNativeQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        List tempList = q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return tempList.get(0);
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

    public int countByNamedQuery(String namedQuery, Map parameters) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }

        return q.getResultList().size();
    }

    public int countByQuery(String query, Map parameters) throws Exception {
        Query q = getEntityManager().createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }

        Object result = q.getSingleResult();
        if (result == null) {
            return 0;
        } else {
            Long longResult = (Long) result;
            return longResult.intValue();
        }
    }

    /*
     * Bu method silinecek. Bu metodu kullanan metodlar refactor edilecek.
     */
    public List<T> findListByNamedQuery(String namedQuery, Map parameters, Integer firstResult, Integer maxResults) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);

        if (firstResult != null && firstResult > 0) {
            q.setFirstResult(firstResult);
        }

        if (maxResults != null && maxResults > 0) {
            q.setMaxResults(maxResults);
        }

        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return q.getResultList();
    }

    /*
     * Bu method silinecek. Bu metodu kullanan metodlar refactor edilecek.
     */
    public T findByNamedQuery(String namedQuery, Map parameters, LockModeType lockModeType, Integer firstResult, Integer maxResults) throws Exception {
        Query q = getEntityManager().createNamedQuery(namedQuery);

        if (firstResult != null && firstResult > 0) {
            q.setFirstResult(firstResult);
        }

        if (maxResults != null && maxResults > 0) {
            q.setMaxResults(maxResults);
        }

        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if (lockModeType != null) {
            q.setLockMode(lockModeType);
        }
        return (T) q.getSingleResult();
    }

    public List<T> findListByNativeQuery(String query, Map<String, Object> parameters, Class<T> clazz) {
        Query q = getEntityManager().createNativeQuery(query, clazz);
        for (Map.Entry entry : parameters.entrySet()) {
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        return q.getResultList();
    }

    public List<T> findListByNativeQuery(String query, Map<String, Object> parameters, int maxSize, Class<T> clazz) {
        Query q = getEntityManager().createNativeQuery(query, clazz);
        for (Map.Entry entry : parameters.entrySet()) {
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        q.setMaxResults(maxSize);
        q.setFirstResult(0);
        return q.getResultList();
    }

    public T findByNativeQuery(String query, Map<String, Object> parameters, Class<T> clazz) throws Exception {
        Query q = getEntityManager().createNativeQuery(query, clazz);
        for (Map.Entry entry : parameters.entrySet()) {
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        q.setMaxResults(2);
        q.setFirstResult(0);
        List tempList = q.getResultList();
        if (tempList.isEmpty()) {
            return null;
        }
        return (T) tempList.get(0);
    }

    public List<T> findListByNativeQueryByRange(String query, Map<String, Object> parameters, int[] range, Class<T> clazz) {
        Query q = getEntityManager().createNativeQuery(query, clazz);
        for (Map.Entry entry : parameters.entrySet()) {
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
}
