package tw.com.prodisc.stock.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STKF01")
public class BSTKF01 extends BSTKF00{
	@Column(length=12)
	private String  stk01001; 	 // 股票代號
	@Column(length=50)
	private String  stk01002; 	 // 股票名稱
	private int  	stk01003; 	 // 0:上市 1:上櫃
	@Column(length=50)
	private String  stk01004; 	 // 股票類別
	private boolean stk01005;    // 股票類別是否顯示
	
	public String getStk01001() {
		return stk01001;
	}
	public void setStk01001(String stk01001) {
		this.stk01001 = stk01001;
	}
	public String getStk01002() {
		return stk01002;
	}
	public void setStk01002(String stk01002) {
		this.stk01002 = stk01002;
	}
	public int getStk01003() {
		return stk01003;
	}
	public void setStk01003(int stk01003) {
		this.stk01003 = stk01003;
	}
	public String getStk01004() {
		return stk01004;
	}
	public void setStk01004(String stk01004) {
		this.stk01004 = stk01004;
	}
	public boolean isStk01005() {
		return stk01005;
	}
	public void setStk01005(boolean stk01005) {
		this.stk01005 = stk01005;
	}
}
