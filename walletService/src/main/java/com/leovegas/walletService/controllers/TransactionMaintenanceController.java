package com.leovegas.walletService.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.leovegas.walletService.domainObject.TransactionDO;
import com.leovegas.walletService.domainObject.WalletDO;
import com.leovegas.walletService.exceptions.CustomException;
import com.leovegas.walletService.helpers.TransactionMaintenanceHelper;
import jakarta.validation.Valid;

/**
 * RestController for TransactionMaintenance
 * getWalletBalance for input playerId
 * perform credit/debit transaction
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@RestController
@RequestMapping("/walletaccount")
public class TransactionMaintenanceController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionMaintenanceController.class);
	
	@Autowired
	TransactionMaintenanceHelper helper;

	/**
	 * getWalletBalance is used to fetch current balance for input playerId.
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/{playerId}")
	public ResponseEntity<Map<String, String>> getWalletBalance(@PathVariable(required = true) Long playerId)
			throws CustomException {
		logger.info("---> Enter getWalletBalance service");
		Map<String, String> map = new HashMap<String, String>();
		String result = helper.findCurrentBalanceAsString(playerId);
		map.put("message", result);
		logger.info("---> End getWalletBalance service");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);

	}

	/**
	 * getWalletAccount is used to fetch all the details of the wallet account of the player.
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/getWalletAccount/{playerId}")
	public ResponseEntity<WalletDO> getWalletAccount(@PathVariable(required = true) Long playerId)
			throws CustomException {
		logger.info("---> Enter getWalletAccount service");
		WalletDO walletDetails = helper.getWalletAccount(playerId);
		logger.info("---> End getWalletAccount service");
		return new ResponseEntity<WalletDO>(walletDetails, HttpStatus.OK);

	}

	/**
	 * performTransaction is used to perform credit/debit transaction
	 * based on the input transactionType
	 * @param transactionDetails
	 * @return
	 * @throws CustomException
	 */
	@PostMapping
	public ResponseEntity<TransactionDO> performTransaction(@Valid @RequestBody TransactionDO transactionDetails)
			throws CustomException {
		logger.info("---> Enter performTransaction service");
		TransactionDO transactionDO = helper.performTransaction(transactionDetails);
		logger.info("---> End performTransaction service");
		return new ResponseEntity<TransactionDO>(transactionDO, HttpStatus.OK);
	}

	/**
	 * getTransactionHistory is used to fetch the transactionHistory
	 * for the input playerId
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/transactionHistory/{playerId}")
	public ResponseEntity<List<TransactionDO>> getTransactionHistory(@PathVariable(required = true) Long playerId)
			throws CustomException {
		logger.info("---> Enter getTransactionHistory service");
		List<TransactionDO> history = helper.getTransactionHistory(playerId);
		logger.info("---> End getTransactionHistory service");
		return new ResponseEntity<List<TransactionDO>>(history, HttpStatus.OK);

	}

}
