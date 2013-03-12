package tw.com.prodisc.stock.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import tw.com.prodisc.stock.dao.IDao;


public class DaoImpl<T> extends HibernateDaoSupport implements IDao<T> {

	public T find(Class<T> clazz, int id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	public void create(T baseBean) {
		getHibernateTemplate().persist(baseBean);
	}
	
	public Session openSession(){
		return this.getHibernateTemplate().getSessionFactory().openSession();
	}
	
	public void closeSession(Session session){
		session.close();
	}
	
	public SQLQuery createSQLQuery(String hql) {
		return getSession().createSQLQuery(hql);
	}
	public Query createQuery(String hql) {
		return getSession().createQuery(hql);
	}

	public void delete(T baseBean) {
		getHibernateTemplate().delete(baseBean);
	}

	@SuppressWarnings("unchecked")
	public List<T> list(String hql) {
		return getHibernateTemplate().find(hql);
	}

	public int getTotalCount(String hql, Object... params) {
		Query query = createQuery(hql);
		
		for (int i = 0; params != null && i < params.length; i++)
			if (params[i] != null){
				query.setParameter("P"+(i+1), params[i]);
			}
		Object obj =  query.uniqueResult();
		return ((Long) obj).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<T> list(String hql, int firstResult, int maxResults,
			Object... params) {
		Query query = createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++)
			if (params[i] != null){
				query.setParameter("P"+(i+1), params[i]);
			}	
		List<T> list = query.setFirstResult(firstResult)
				.setMaxResults(maxResults).list();
		return list;
	}

	public T save(T baseBean) {
		T ret =(T)getHibernateTemplate().merge(baseBean);
		getHibernateTemplate().flush();
		return ret;
		// getHibernateTemplate().flush();
		// getHibernateTemplate().merge(baseBean);
		// getHibernateTemplate().update(baseBean);
		// getHibernateTemplate().saveOrUpdate(baseBean);
		// getHibernateTemplate().save(baseBean);
	}

}
