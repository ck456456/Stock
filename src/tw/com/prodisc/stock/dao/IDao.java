package tw.com.prodisc.stock.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public interface IDao<T> {

	public T find(Class<T> clazz, int id);

	public void create(T baseBean);

	public T save(T baseBean);

	public void delete(T baseBean);
	
	public List<T> list(String hql);

	public int getTotalCount(String hql, Object... params);

	public List<T> list(String hql, int firstResult, int maxSize,
			Object... params);

	public Query createQuery(String hql);
	public SQLQuery createSQLQuery(String hql);
	
	public void closeSession(Session session);
	public Session openSession();
}
