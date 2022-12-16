package com.leovegas.walletService.domainObject;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * 
 * Domain object for Audit
 * @author gomathi lakshmanaperumal
 *
 */
@Entity
@Table(name = "AUDIT_DETAILS")
public class AuditDO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="AUDIT_CODE")
	private String audit_cde;
	
	@Column(name="DESCRIPTION")
	private String audit_description;
	
	@Column(name="PLAYER_ID")
	private Long playerId;
	
	@Column(name="TIME_STAMP")
	private Date creationDate;
	
	@PrePersist
	protected void onCreate() {
		this.creationDate = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAuditCode() {
		return audit_cde;
	}
	
    public void setAuditCode(String audit_cde) {
    	this.audit_cde = audit_cde;
    }
    
    public String getAuditDescription() {
		return audit_description;
	}
    
    public void setAuditDescription(String audit_description) {
    	this.audit_description = audit_description;
    }
    
    public Long getPlayerId() {
		return playerId;
	}
    
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
