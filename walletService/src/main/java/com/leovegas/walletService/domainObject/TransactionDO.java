package com.leovegas.walletService.domainObject;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Domain object for transaction
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@Entity
@Table(name = "TRANSACTION_DETAILS")
public class TransactionDO {

	@Id
	@NotNull(message="Please provide unique id")
	@Column(unique = true)
	private Long id;

	@NotNull(message="Please provide amount to be transacted")
	@Column(name = "TRANS_AMOUNT")
	private Double amount;

	@NotBlank(message="Please provide currency code")
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@NotBlank(message="Please provide transactionType either credit or debit")
	@Column(name = "TRANS_TYPE")
	private String transactionType;

	@Column(name = "TRANS_MESSAGE")
	private String transactionMessage;
	
	@Column(name = "CURRENT_BALANCE")
	private Double currentBalance;

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	@NotNull(message="Please provide valid playerId")
	@Column(name = "PLAYER_ID")
	private Long playerId;

	@Column(name = "UPD_TIMESTAMP")
	private Date time_stamp;

	@PrePersist
	protected void onCreate() {
		this.time_stamp = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionMessage() {
		return transactionMessage;
	}

	public void setTransactionMessage(String transactionMessage) {
		this.transactionMessage = transactionMessage;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public Date getTimestamp() {
		return time_stamp;
	}

	public void setTimestamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}
}
