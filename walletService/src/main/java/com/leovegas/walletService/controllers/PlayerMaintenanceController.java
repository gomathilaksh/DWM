package com.leovegas.walletService.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leovegas.walletService.domainObject.AuditDO;
import com.leovegas.walletService.domainObject.PlayerDO;
import com.leovegas.walletService.exceptions.CustomException;
import com.leovegas.walletService.helpers.PlayerMaintenanceHelper;
import jakarta.validation.Valid;

/**
 * RestController for playerMaintenance.
 * C - createPlayer service 
 * R - getPlayer service 
 * U - updatePlayer service 
 * D - deletePlayer service
 * 
 * @author gomathi lakshmanaperumal.
 */

@RestController
@RequestMapping("/player")
public class PlayerMaintenanceController {

	private static final Logger logger = LoggerFactory.getLogger(PlayerMaintenanceController.class);

	@Autowired
	PlayerMaintenanceHelper helper;

	/**
	 * createPlayer is used for new player creation. For each new player, new wallet
	 * account will be created with Zero balance. Based on the country of the
	 * player, currency code will be determined for the wallet account.
	 * 
	 * @param playerDo
	 * @return
	 * @throws CustomException 
	 */
	@PostMapping
	public ResponseEntity<Object> createPlayer(@Valid @RequestBody PlayerDO playerDo) throws CustomException {
		PlayerDO playerDetails = null;
		logger.info("---> Enter createPlayer Service");
		playerDetails = helper.createPlayer(playerDo);
		helper.createWalletAccount(playerDo);
		logger.info("---> End createPlayer Service");
		return new ResponseEntity<>(playerDetails, HttpStatus.CREATED);
	}

	/**
	 * getPlayer is used to fetch player details based on the playerId in input.
	 * 
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/{playerId}")
	public ResponseEntity<Object> getPlayer(@PathVariable(required = true) Long playerId) throws CustomException {
		logger.info("---> Enter getPlayer Service");
		PlayerDO playerDetails = helper.getPlayerDetails(playerId);
		logger.info("---> End getPlayer Service");
		return new ResponseEntity<>(playerDetails, HttpStatus.OK);

	}

	/**
	 * updatePlayer is used to update the player details.
	 * @param playerDo
	 * @return
	 * @throws CustomException
	 */
	@PutMapping
	public ResponseEntity<Map<String, String>> updatePlayer(@Valid @RequestBody PlayerDO playerDo)
			throws CustomException {
		logger.info("---> Enter updatePlayer Service");
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (null == playerDo.getId() || playerDo.getId() <= 0) {
				throw new CustomException("Invalid PlayerId");
			}
			helper.updatePlayer(playerDo);
			map.put("message", "Record updated for playerId :: " + playerDo.getId());
		} catch (CustomException ex) {
			map.put("message", ex.getMessage());
		} catch (Exception ex) {
			map.put("message", "Error while updating Record");
		}
		logger.info("---> End updatePlayer Service");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);

	}

	/**
	 * deletePlayer will delete the current wallet account of the player 
	 * and then the player account will be deleted.
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	@DeleteMapping("/{playerId}")
	public ResponseEntity<Map<String, String>> deletePlayer(@PathVariable(required = true) Long playerId)
			throws CustomException {
		logger.info("---> Enter deletePlayer Service");
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (null == playerId || playerId <= 0) {
				throw new CustomException("Invalid PlayerId");
			}
			helper.deletePlayer(playerId);
			map.put("message", "Record Deleted for PlayerId :: " + playerId);
		} catch (CustomException ex) {
			map.put("message", "Invalid PlayerId :: " + playerId);
		} catch (Exception ex) {
			map.put("message", "Error while deleting Record for playerId :: " + playerId);
		}
		logger.info("---> End deletePlayer Service");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}
	
	/**
	 * getPlayerAuditHistory will provide activity report of player account.
	 * 
	 * @param playerId
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/auditHistory/{playerId}")
	public ResponseEntity<List<AuditDO>> getPlayerAuditHistory(@PathVariable(required = true) Long playerId)
			throws CustomException {
		logger.info("---> Enter getPlayerAuditHistory service");
		List<AuditDO> history = helper.getAuditHistory(playerId);
		logger.info("---> End getPlayerAuditHistory service");
		return new ResponseEntity<List<AuditDO>>(history, HttpStatus.OK);

	}

}
