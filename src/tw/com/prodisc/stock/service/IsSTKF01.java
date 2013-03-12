package tw.com.prodisc.stock.service;


import java.util.List;

import tw.com.prodisc.stock.bean.BSTKF01;

public interface IsSTKF01 <T extends BSTKF01> extends IsBASE<T> {
	/**  全抓 */
	public List<T> getAll();
	public T getID(String ID);
	public T getNo(String no);
	public List<T> getByType();
	public boolean getByType(String s004);
}
