package com.leovegas.walletService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.leovegas.walletService.domainObject.PlayerDO;
import com.leovegas.walletService.domainObject.TransactionDO;
import com.leovegas.walletService.domainObject.WalletDO;
import com.leovegas.walletService.exceptions.CustomException;
import com.leovegas.walletService.helpers.PlayerMaintenanceHelper;
import com.leovegas.walletService.helpers.TransactionMaintenanceHelper;
import com.leovegas.walletService.services.PlayerService;
import com.leovegas.walletService.services.WalletService;

/**
 * unit test the functionalities of wallet service
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@SpringBootTest
class WalletServiceApplicationTests {

	@Autowired
	PlayerService playerService;

	@Autowired
	WalletService walletService;

	@Autowired
	PlayerMaintenanceHelper helper;

	@Autowired
	TransactionMaintenanceHelper transactionHelper;

	private static long NEW_PLAYER_ID = 24;
	private static long INVALID_PLAYER_ID = 1;

	/**
	 * test case to check player details is not null for the input PlayerId
	 */
	@Test
	public void testGetPlayer() {
		try {
			assertNotNull(playerService.getPlayerDetails(NEW_PLAYER_ID));
		} catch (CustomException e) {
			e.printStackTrace();
		}

	}

	/**
	 * test case to check for zero balance wallet account for new player creation
	 */
	@Test
	public void testGetBalanceForNewPlayer() {
		double newBalance = 0.0;
		try {
			WalletDO walletDetails = walletService.findByplayerId(NEW_PLAYER_ID);
			assertEquals(String.valueOf(walletDetails.getCurrentBalance()), String.valueOf(newBalance));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * test case to check currencyCode for the given Country of Player
	 */
	@Test
	public void testCurrencyCodeByCountry() {
		try {
			PlayerDO playerDetails = playerService.getPlayerDetails(NEW_PLAYER_ID);
			String actual = helper.fetchCurrencyCode(playerDetails.getCountry());
			WalletDO walletDetails = walletService.findByplayerId(NEW_PLAYER_ID);
			String expected = walletDetails.getCurrencyCode();
			assertEquals(actual, expected);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * test case to check exception while deleting invalid player
	 */
	@Test
	public void testDeleteInvalidPlayer() {
		try {
			helper.deletePlayer(INVALID_PLAYER_ID);
		} catch (CustomException e) {
			assertEquals(e.getMessage(), "No Player Found for playerId : " + INVALID_PLAYER_ID);
		}

	}

	/**
	 * test case to check exception if transactionId is not unique
	 */
	@Test
	public void testExceptionTransactionId() {
		TransactionDO testTransaction = new TransactionDO();
		testTransaction.setAmount(10.0);
		testTransaction.setId(1L);
		testTransaction.setPlayerId(NEW_PLAYER_ID);
		testTransaction.setCurrencyCode("EUR");
		testTransaction.setTransactionType("CREDIT");
		try {
			transactionHelper.performTransaction(testTransaction);
		} catch (CustomException e) {
			assertEquals(e.getMessage(), "transaction id should be unique for each transaction");
		}

	}

	/**
	 * test case to check exception for insufficient balance while debit in wallet
	 * account of player.
	 */
	@Test
	public void testExceptionInsufficientBalance() {
		TransactionDO testTransaction = new TransactionDO();
		testTransaction.setAmount(10.0);
		testTransaction.setId(265L);
		testTransaction.setPlayerId(NEW_PLAYER_ID);
		testTransaction.setCurrencyCode("EUR");
		testTransaction.setTransactionType("DEBIT");
		try {
			transactionHelper.performTransaction(testTransaction);
		} catch (CustomException e) {
			assertEquals(e.getMessage(),
					"Insufficient balance in the wallet account for playerId : " + testTransaction.getPlayerId());
		}

	}

	/**
	 * test case to check for exception for invalid transactionType.
	 */
	@Test
	public void testExceptionInvalidTransactionType() {
		TransactionDO testTransaction = new TransactionDO();
		testTransaction.setAmount(10.0);
		testTransaction.setId(265L);
		testTransaction.setPlayerId(NEW_PLAYER_ID);
		testTransaction.setCurrencyCode("EUR");
		testTransaction.setTransactionType("CREDITDEBIT");
		try {
			transactionHelper.performTransaction(testTransaction);
		} catch (CustomException e) {
			assertEquals(e.getMessage(), "Invalid transactiontype. Choose either 'credit' or 'debit'");
		}

	}

}
