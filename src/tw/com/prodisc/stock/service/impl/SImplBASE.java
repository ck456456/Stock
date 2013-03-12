package tw.com.prodisc.stock.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import tw.com.prodisc.stock.bean.BSTKF00;
import tw.com.prodisc.stock.dao.IDao;
import tw.com.prodisc.stock.service.IsBASE;
import tw.com.prodisc.util.KC_Pub;

public abstract class SImplBASE<T extends BSTKF00> implements IsBASE<T> {

	protected IDao<T> dao;

	public IDao<T> getDao() {
		return dao;
	}

	public void setDao(IDao<T> dao) {
		this.dao = dao;
	}

	public T find(Class<T> clazz, int id) {
		return dao.find(clazz, id);
	}

	public void create(T baseBean){  // 新增
		if (KC_Pub.kc001 != 1){ 
			HttpServletRequest  request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			baseBean.setIdCreated((Integer)session.getAttribute("G_ID"));
		}
		
		baseBean.setDateCreated(new Date());
		dao.create(baseBean);
	}

	public T save(T baseBean) {  // 修改
		
		if (KC_Pub.kc001 != 1){
			HttpServletRequest  request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			baseBean.setIdModified((Integer)session.getAttribute("G_ID"));
		}
		baseBean.setDateModified(new Date());
		return dao.save(baseBean);
	}

	public void del_T(T baseBean){
		dao.delete(baseBean);
	}
	
	public void delete(T baseBean) {  // 刪除
		if (KC_Pub.kc001 != 1){ 
			HttpServletRequest  request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			baseBean.setIdDeleted((Integer)session.getAttribute("G_ID"));
		}
		baseBean.setDateDeleted(new Date());
		baseBean.setDeleted(true);
		dao.save(baseBean);
	}

	public int getTotalCount(String hql, Object... params) {
		return dao.getTotalCount(hql, params);
	}

	public List<T> list(String hql) {
		return dao.list(hql);
	}

	public List<T> list(String hql, int firstResult, int maxSize,
			Object... params) {
		return dao.list(hql, firstResult, maxSize, params);
	}

}
