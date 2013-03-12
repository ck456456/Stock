package tw.com.prodisc.stock.service;

import java.util.Date;
import java.util.List;

import tw.com.prodisc.stock.bean.BSTKF02;

public interface IsSTKF02 <T extends BSTKF02> extends IsBASE<T> {
	public List<T> getByDate(Date dt,String fg);
	public List<T> getByDate(Date dt);
	public List<T> getDateList(Date d01,int i01,String fg);
	public List<T> getRise(Date dt,String f1,String ty,String f2,String f3,int i01);
	public T getID(String ID);
}
