package com.leovegas.walletService.helpers;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.leovegas.walletService.constants.ServiceConstant;
import com.leovegas.walletService.domainObject.AuditDO;
import com.leovegas.walletService.domainObject.PlayerDO;
import com.leovegas.walletService.domainObject.WalletDO;
import com.leovegas.walletService.exceptions.CustomException;
import com.leovegas.walletService.services.AuditService;
import com.leovegas.walletService.services.PlayerService;
import com.leovegas.walletService.services.WalletService;

/**
 * Helper class for PlayerMaintenanceController.
 * 
 * @author gomathi lakshmanaperumal.
 *
 */
@Component
public class PlayerMaintenanceHelper {

	private static final Logger logger = LoggerFactory.getLogger(PlayerMaintenanceHelper.class);

	@Autowired
	PlayerService playerService;

	@Autowired
	WalletService walletService;

	@Autowired
	AuditService auditService;

	/**
	 * functionality for wallet account creation
	 * 
	 * @param playerDetails
	 */
	public void createWalletAccount(PlayerDO playerDetails) {
		WalletDO walletDetails = new WalletDO();
		walletDetails.setCurrentBalance(0.0);
		walletDetails.setCurrencyCode(fetchCurrencyCode(playerDetails.getCountry().trim()));
		walletDetails.setPlayerId(playerDetails.getId());
		walletDetails.setAccountNumber(generateAccountNumber());
		walletService.createWallet(walletDetails);
		logger.info("---> Zeo Balance Wallet account Successfully created for PlayerId - " + playerDetails.getId());
		populateAudit(ServiceConstant.CREATE_WALLET_AUDIT, playerDetails.getId());
		logger.info("----> Audit created for Wallet creation");

	}

	/**
	 * functionality for fetching currencyCode based on the country of the player.
	 * 
	 * @param country
	 * @return
	 */
	public String fetchCurrencyCode(String country) {
		String currencyCode = null;
		for (Locale locale : Locale.getAvailableLocales()) {
			if (locale.getDisplayCountry().equalsIgnoreCase(country)) {
				Currency currency = Currency.getInstance(locale);
				currencyCode = currency.getCurrencyCode();
			}
		}
		return currencyCode;

	}

	/**
	 * functionality for generating random six digit wallet account number.
	 * 
	 * @return
	 */
	private String generateAccountNumber() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return String.format("%06d", number);

	}

	/**
	 * functionality for logging Audit for future reference.
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
	 * get auditMessage from auditCode
	 * 
	 * @param auditCode
	 * @return
	 */
	private String getAuditMessage(String auditCode) {
		String auditMessage = ServiceConstant.CONSTANT_MAP.get(auditCode);
		return auditMessage;

	}

	/**
	 * functionality for player creation
	 * 
	 * @param playerDo
	 * @return
	 * @throws CustomException 
	 */
	public PlayerDO createPlayer(PlayerDO playerDo) throws CustomException {
		PlayerDO playerDetails = playerService.createPlayer(playerDo);
		logger.info("---> Player created Successfully PlayerId - " + playerDetails.getId());
		populateAudit(ServiceConstant.CREATE_PLAYER_AUDIT, playerDetails.getId());
		logger.info("----> Audit created for player creation");
		return playerDetails;

	}

	/**
	 * functionality for fetching player Details.
	 * 
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	public PlayerDO getPlayerDetails(Long playerId) throws CustomException {
		PlayerDO playerDetails = null;
		playerDetails = playerService.getPlayerDetails(playerId);
		logger.info("----> Player Details fetched Successfully");
		return playerDetails;
	}

	/**
	 * functionality for updating player details
	 * 
	 * @param playerDo
	 * @throws CustomException
	 */
	public void updatePlayer(PlayerDO playerDo) throws CustomException {
		playerService.updatePlayer(playerDo);
		logger.info("----> Player updated Successfully");
		populateAudit(ServiceConstant.UPDATE_PLAYER_AUDIT, playerDo.getId());
		logger.info("----> Audit created for player update");

	}

	/**
	 * functionality for delete player
	 * 
	 * @param playerId
	 * @throws CustomException
	 */
	public void deletePlayer(Long playerId) throws CustomException {
		deleteWallet(playerId);
		playerService.deletePlayer(playerId);
		logger.info("---->Player Deleted Successfully");
		populateAudit(ServiceConstant.DELETE_PLAYER_AUDIT, playerId);
		logger.info("---->Audit created for player Deletion");

	}

	/**
	 * functionality for wallet deletion.
	 * 
	 * @param playerId
	 * @throws CustomException
	 */
	public void deleteWallet(Long playerId) throws CustomException {
		WalletDO walletDetails = walletService.findByplayerId(playerId);
		if (walletDetails != null) {
			walletService.deleteWallet(walletDetails);
			logger.info("----> Wallet Deleted Successfully");
			populateAudit(ServiceConstant.DELETE_WALLET_AUDIT, playerId);
			logger.info("---->Audit created for Wallet Deletion");
		}

	}

	/**
	 * functionality of fetching audits for the player.
	 * 
	 * @param playerId
	 * @return
	 */
	public List<AuditDO> getAuditHistory(Long playerId) {
		List<AuditDO> auditDetails = auditService.findByplayerId(playerId);
		return auditDetails;
	}

}
