package tw.com.prodisc.stock.service.impl;

import java.util.Date;
import java.util.List;

import tw.com.prodisc.stock.bean.BSTKF02;
import tw.com.prodisc.stock.service.IsSTKF02;

public class SImplSTKF02<T extends BSTKF02> extends SImplBASE<T>
implements IsSTKF02<T> {

	@SuppressWarnings("unchecked")
	public List<T> getRise(Date dt,String f1,String ty,String f2,String f3,int i01){
		String hql = "Select new map(p.f01.id as id,p.f01.stk01001 as stk01001,p.f01.stk01002 as stk01002) "+
				 "from BSTKF02 p where p.deleted = false " +
			     				" and p.stk02001 = :D01 "+
			     				" and p.f01.stk01004 in "+ty+
			     				" and lower(p.f01.stk01003) = lower(:F1) "+
				  				" Order by "+f2+" "+f3;
		// (stk02004/(stk02004-stk02002))
		return this.getDao().createQuery(hql)
				.setDate("D01", dt)
				.setParameter("F1", f1)
				.setFirstResult(0)
				.setMaxResults(i01)
				.list();
	}
	@SuppressWarnings("unchecked")
	public List<T> getDateList(Date d01,int i01,String fg){
		String hql = "Select new map(p.stk02001 as stk02001) "+
				 "from BSTKF02 p where p.deleted = false " +
			     				" and p.stk02001 <= :D01 "+
			     				" and lower(p.f01.stk01003) = lower(:FG) "+
			     				" Group by stk02001"+
				  				" Order by stk02001 desc";
		return this.getDao().createQuery(hql)
				.setDate("D01", d01)
				.setParameter("FG", fg)
				.setFirstResult(0)
				.setMaxResults(i01)
				.list();
	}
	@SuppressWarnings("unchecked")
	public List<T> getByDate(Date dt,String fg){
		String hql = "Select p "+
				"from BSTKF02 p where deleted = false "+
                			     " and p.stk02001 = :DT "+
                			     " and lower(p.f01.stk01003) = lower(:FG) "+
				  				"Order by p.f01.stk01001";
		return this.getDao().createQuery(hql)
				.setDate("DT", dt)
				.setParameter("FG", fg)
				.list();
	}
	@SuppressWarnings("unchecked")
	public List<T> getByDate(Date dt){
		String hql = "Select p "+
				"from BSTKF02 p where deleted = false "+
                			     " and p.stk02001 = :DT "+
				  				"Order by p.f01.stk01001";
		return this.getDao().createQuery(hql).setDate("DT", dt).list();
	}
	
	@SuppressWarnings("unchecked")
	public T getID(String ID){
		List<T> f01s = this.getDao().createQuery(
				" select p from BSTKF02 p "
						+ " where lower(p.id) = lower(:ID) "
		                  + " and p.deleted = false ")
				.setParameter("ID", ID).list();
		if (f01s.size() > 0) return f01s.get(0);
		return null;
	}
}
