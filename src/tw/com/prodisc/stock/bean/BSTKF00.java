package tw.com.prodisc.stock.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public class BSTKF00 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Version
	private Integer version;    // 版本 hibernate 自動維護
	private boolean deleted;    // 刪除位元 , 使用預設設定
	private Integer idCreated;  // 新增id戳記
	private Integer idModified; // 修改id戳記
	private Integer idDeleted;  // 刪除id戳記時
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dateCreated;   // 新增時間戳記時
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dateModified;  // 修改時間戳記時
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dateDeleted;   // 刪除時間戳記時
	
	public Integer getIdCreated() {
		return idCreated;
	}

	public void setIdCreated(Integer idCreated) {
		this.idCreated = idCreated;
	}

	public Integer getIdModified() {
		return idModified;
	}

	public void setIdModified(Integer idModified) {
		this.idModified = idModified;
	}

	public Integer getIdDeleted() {
		return idDeleted;
	}

	public void setIdDeleted(Integer idDeleted) {
		this.idDeleted = idDeleted;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getDateDeleted() {
		return dateDeleted;
	}

	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = dateDeleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}