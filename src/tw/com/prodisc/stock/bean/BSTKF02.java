package tw.com.prodisc.stock.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

@Entity
@Table(name = "STKF02")
@org.hibernate.annotations.Table(appliesTo = "STKF02",
	indexes = { @Index(name = "index_001", columnNames = { "stk02001" })
	})
public class BSTKF02 extends BSTKF00{
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date 		stk02001;   	 // 交易日期
	private float 		stk02002;		 // 漲跌	 
	private float 		stk02003;		 // 開盤 價	 
	private float 		stk02004;		 // 收盤 價
	private float 		stk02005;		 // 最高 價
	private float 		stk02006;		 // 最低 價
	private float 		stk02007;		 // 均價
	private long 		stk02008;		 // 成交股數
	private double 		stk02009;		 // 成交金額(元)
	private long 		stk02010;		 // 成交筆數
	
	/* 股票代號主檔 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "f01_id")
	@ForeignKey(name = "stkF02_F01")
	private BSTKF01 f01;
	
	
	public BSTKF01 getF01() {
		return f01;
	}
	public void setF01(BSTKF01 f01) {
		this.f01 = f01;
	}
	public Date getStk02001() {
		return stk02001;
	}
	public void setStk02001(Date stk02001) {
		this.stk02001 = stk02001;
	}
	public float getStk02002() {
		return stk02002;
	}
	public void setStk02002(float stk02002) {
		this.stk02002 = stk02002;
	}
	public float getStk02003() {
		return stk02003;
	}
	public void setStk02003(float stk02003) {
		this.stk02003 = stk02003;
	}
	public float getStk02004() {
		return stk02004;
	}
	public void setStk02004(float stk02004) {
		this.stk02004 = stk02004;
	}
	public float getStk02005() {
		return stk02005;
	}
	public void setStk02005(float stk02005) {
		this.stk02005 = stk02005;
	}
	public float getStk02006() {
		return stk02006;
	}
	public void setStk02006(float stk02006) {
		this.stk02006 = stk02006;
	}
	public float getStk02007() {
		return stk02007;
	}
	public void setStk02007(float stk02007) {
		this.stk02007 = stk02007;
	}
	public long getStk02008() {
		return stk02008;
	}
	public void setStk02008(long stk02008) {
		this.stk02008 = stk02008;
	}
	public double getStk02009() {
		return stk02009;
	}
	public void setStk02009(double stk02009) {
		this.stk02009 = stk02009;
	}
	public long getStk02010() {
		return stk02010;
	}
	public void setStk02010(long stk02010) {
		this.stk02010 = stk02010;
	}
}
