package com.leovegas.walletService.helpers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.leovegas.walletService.constants.ServiceConstant;
import com.leovegas.walletService.domainObject.AuditDO;
import com.leovegas.walletService.domainObject.TransactionDO;
import com.leovegas.walletService.domainObject.WalletDO;
import com.leovegas.walletService.exceptions.CustomException;
import com.leovegas.walletService.services.AuditService;
import com.leovegas.walletService.services.PlayerService;
import com.leovegas.walletService.services.TransactionService;
import com.leovegas.walletService.services.WalletService;

/**
 * helper class for TransactionMaintenanceController.
 * 
 * @author gomathi lakshmanaperumal.
 *
 */
@Component
public class TransactionMaintenanceHelper {

	private static final Logger logger = LoggerFactory.getLogger(TransactionMaintenanceHelper.class);

	@Autowired
	WalletService walletService;

	@Autowired
	PlayerService playerService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	AuditService auditService;

	/**
	 * method returns current balance of wallet account as String for the input
	 * playerId
	 * 
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	public String findCurrentBalanceAsString(Long playerId) throws CustomException {
		StringBuffer outputMessage = new StringBuffer();
		WalletDO walletDetails = getWalletAccount(playerId);
		if (walletDetails == null) {
			throw new CustomException("Wallet account is not found for playerId : " + playerId);
		}
		outputMessage.append("The available balance in the account ");
		outputMessage.append(walletDetails.getAccountNumber());
		outputMessage.append(" for the playerId ");
		outputMessage.append(playerId);
		outputMessage.append(" is ");
		outputMessage.append(walletDetails.getCurrentBalance());
		outputMessage.append(walletDetails.getCurrencyCode());
		return outputMessage.toString();
	}

	/**
	 * method to perform transaction based on transactionType check for unique
	 * transactionId - supplied by caller check for valid Currency code check for
	 * sufficient balance before perform debit.
	 * 
	 * @param transactionDetails
	 * @throws CustomException
	 */
	public TransactionDO performTransaction(TransactionDO transactionDetails) throws CustomException {
		TransactionDO transaction = null;
		boolean isIdExists = transactionService.existsById(transactionDetails.getId());
		if (!isIdExists) {
			logger.info("transactionId " + transactionDetails.getId() + " is unique");
			WalletDO walletDetails = walletService.findByplayerId(transactionDetails.getPlayerId());
			if (walletDetails == null) {
				throw new CustomException(
						"Wallet account is not found for playerId : " + transactionDetails.getPlayerId());
			}
			boolean isValid = validateCurrencyType(walletDetails.getCurrencyCode(),
					transactionDetails.getCurrencyCode());
			if (isValid) {
				logger.info("currencyCode check succesful");
				Double balance = walletDetails.getCurrentBalance();
				if (transactionDetails.getTransactionType().equalsIgnoreCase("debit")) {
					logger.info("perform debit transaction for playerId : " + transactionDetails.getPlayerId());
					double difference = balance - transactionDetails.getAmount();
					if (difference >= 0) {
						transactionDetails.setTransactionMessage("Amount " + transactionDetails.getAmount() + " "
								+ transactionDetails.getCurrencyCode() + " Debit successful");
						transactionDetails.setCurrentBalance(difference);
						transaction = transactionService.createTransaction(transactionDetails);
						updateWalletBalance(transactionDetails.getPlayerId(), difference);

					} else {
						logger.error("Insufficient balance in the wallet account for playerId : "
								+ transactionDetails.getPlayerId());
						throw new CustomException("Insufficient balance in the wallet account for playerId : "
								+ transactionDetails.getPlayerId());
					}
				} else if (transactionDetails.getTransactionType().equalsIgnoreCase("credit")) {
					logger.info("perform credit transaction for playerId : " + transactionDetails.getPlayerId());
					double actualAmount = balance + transactionDetails.getAmount();
					transactionDetails.setTransactionMessage("Amount " + transactionDetails.getAmount() + " "
							+ transactionDetails.getCurrencyCode() + " Credit successful");
					transactionDetails.setCurrentBalance(actualAmount);
				      transaction = transactionService.createTransaction(transactionDetails);
					updateWalletBalance(transactionDetails.getPlayerId(), actualAmount);

				} else {
					logger.error("Invalid transactiontype. Choose either 'credit' or 'debit'");
					throw new CustomException("Invalid transactiontype. Choose either 'credit' or 'debit'");
				}
			}
		} else {
			logger.error("transaction id should be unique for each transaction");
			throw new CustomException("transaction id should be unique for each transaction");
		}
		return transaction;
	}

	/**
	 * method to validate currency code.
	 * 
	 * @param actual
	 * @param currencyCode
	 * @return
	 * @throws CustomException
	 */
	private boolean validateCurrencyType(String actual, String currencyCode) throws CustomException {
		boolean isValid = false;
		if (actual != null) {
			if (currencyCode.equalsIgnoreCase(actual)) {
				isValid = true;
			} else {
				throw new CustomException("Invalid currency code specified for the country");
			}
		} else {
			throw new CustomException("Please update valid Country in player account");
		}
		return isValid;

	}

	/**
	 * method to update balance in the wallet account of the player after each
	 * transaction.
	 * 
	 * @param playerId
	 * @param difference
	 */
	private void updateWalletBalance(Long playerId, Double difference) {
		WalletDO walletDetails = walletService.findByplayerId(playerId);
		walletDetails.setCurrentBalance(difference);
		walletService.createWallet(walletDetails);
		populateAudit(ServiceConstant.UPDATE_WALLET_BALANCE, playerId);

	}

	/**
	 * method to fetch walletAccount of the player
	 * 
	 * @param playerId
	 * @return
	 */
	public WalletDO getWalletAccount(Long playerId) {
		WalletDO walletDetails = walletService.findByplayerId(playerId);
		return walletDetails;
	}

	/**
	 * method to log audit for future reference
	 * 
	 * @param auditCode
	 * @param playerId
	 */
	public void populateAudit(String auditCode, Long playerId) {
		AuditDO auditDetails = new AuditDO();
		auditDetails.setAuditCode(auditCode);
		auditDetails.setAuditDescription(getAuditMessage(auditCode));
		auditDetails.setPlayerId(playerId);
		auditService.performAudit(auditDetails);
	}

	/**
	 * method to retrieve auditMessage as per auditCode
	 * 
	 * @param auditCode
	 * @return
	 */
	private String getAuditMessage(String auditCode) {
		String auditMessage = ServiceConstant.CONSTANT_MAP.get(auditCode);
		return auditMessage;

	}

	/**
	 * method to fetch transactionHistory of a player
	 * 
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	public List<TransactionDO> getTransactionHistory(Long playerId) throws CustomException {
		List<TransactionDO> transactionHistory = transactionService.getTransactionHistory(playerId);
		if (transactionHistory.size() == 0) {
			throw new CustomException("No Transaction History is found for the playerId " + playerId);
		}
		return transactionHistory;
	}
}