package tw.com.prodisc.stock.service.impl;

import java.util.List;

import org.hibernate.transform.Transformers;

import tw.com.prodisc.stock.bean.BSTKF01;
import tw.com.prodisc.stock.service.IsSTKF01;

public class SImplSTKF01<T extends BSTKF01> extends SImplBASE<T>
	implements IsSTKF01<T> {
	
	@SuppressWarnings("unchecked")
	public boolean getByType(String s004){
		List<BSTKF01> f01s = this.getDao().createQuery(
				" select p from BSTKF01 p "
						+ " where lower(p.stk01004) = lower(:NO) "
		                  + " and p.deleted = false ")
				.setParameter("NO", s004).list();
		
		if (f01s.size() > 0) return f01s.get(0).isStk01005();
		return true;
	}
	@SuppressWarnings("unchecked")
	public List<T> getByType(){
		String hql = "Select stk01003,stk01004,stk01005"+
						" from stkf01"+
						" where deleted = false"+
						" Group by stk01003,stk01004,stk01005 "+
						"Order by stk01003,CONVERT(stk01004 USING big5) COLLATE 'big5_chinese_ci'";
		return this.getDao().createSQLQuery(hql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		/*
		String hql = "Select new map(p.stk01003 as stk01003,p.stk01004 as stk01004,p.stk01005 as stk01005) "+
				 "from BSTKF01 p where p.deleted = false " +
			     				" Group by stk01003,stk01004,stk01005"+
				  				" Order by stk01003,stk01004";
		 return this.getDao().createQuery(hql).list();
		*/ 
				
		
	}
	@SuppressWarnings("unchecked")
	public List<T> getAll(){
		String hql = "Select p "+
				"from BSTKF01 p where deleted = false "+
				  				"Order by stk01001";
		return this.getDao().createQuery(hql).list();
	}
	@SuppressWarnings("unchecked")
	public T getNo(String no){
		List<T> f01s = this.getDao().createQuery(
				" select p from BSTKF01 p "
						+ " where lower(p.stk01001) = lower(:NO) "
		                  + " and p.deleted = false ")
				.setParameter("NO", no).list();
		if (f01s.size() > 0) return f01s.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public T getID(String ID){
		List<T> f01s = this.getDao().createQuery(
				" select p from BSTKF01 p "
						+ " where lower(p.id) = lower(:ID) "
		                  + " and p.deleted = false ")
				.setParameter("ID", ID).list();
		if (f01s.size() > 0) return f01s.get(0);
		return null;
	}
}
