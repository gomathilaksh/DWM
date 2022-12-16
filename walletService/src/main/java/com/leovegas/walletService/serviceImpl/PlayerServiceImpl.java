package com.leovegas.walletService.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leovegas.walletService.domainObject.PlayerDO;
import com.leovegas.walletService.exceptions.CustomException;
import com.leovegas.walletService.repository.PlayerRepository;
import com.leovegas.walletService.services.PlayerService;

/**
 * Service layer that contacts with playerRepository.
 * 
 * @author gomathi lakshmanaperumal
 *
 */

@Service
public class PlayerServiceImpl implements PlayerService {

	private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * save player details to DB table name is PLAYER_DETAILS.
	 * @throws CustomException 
	 */
	@Override
	public PlayerDO createPlayer(PlayerDO playerDetails) throws CustomException {
		if (playerRepository.existsByEmail(playerDetails.getEmail())) {
			throw new CustomException(
					"Email-id already exists for another player. Please provide different unique email");
		}
		PlayerDO player = playerRepository.save(playerDetails);
		return player;
	}

	/**
	 * fetch player details from DB table name is PLAYER_DETAILS.
	 */
	@Override
	public PlayerDO getPlayerDetails(Long playerId) throws CustomException {
		try {
			PlayerDO playerDo = playerRepository.findById(playerId).get();
			if (null == playerDo) {
				logger.error("No player is found for playerId : " + playerId);
				throw new CustomException("No player is found for playerId : " + playerId);
			}
			return playerDo;
		} catch (Exception ex) {
			logger.error("Error while Retrieving Record for playerId : " + playerId);
			throw new CustomException("Error while Retrieving Record for playerId : " + playerId);
		}
	}

	/**
	 * update player details to DB table name is PLAYER_DETAILS.
	 */
	@Override
	public PlayerDO updatePlayer(PlayerDO playerDetails) throws CustomException {
		Long playerId = playerDetails.getId();

		PlayerDO playerDo = playerRepository.findById(playerId).get();
		if (null == playerDo) {
			logger.error("No player is found for playerId : " + playerId);
			throw new CustomException("No player is found for playerId : \" + playerId");
		}
		if (!playerDo.getCountry().equalsIgnoreCase(playerDetails.getCountry())) {
			throw new CustomException("Player should not have permission to change the Country. "
					+ "The Wallet account is specified to the currencyCode of the existing Country. "
					+ "If needed to change the Country, please create a new player account for the new Country");
		}
		playerDetails.setCreatedDate(playerDo.getCreatedDate());
		playerRepository.save(playerDetails);

		return playerDetails;
	}

	/**
	 * delete entries from table for the given playerId table name is
	 * PLAYER_DETAILS.
	 */
	@Override
	public void deletePlayer(Long playerId) throws CustomException {
		if (playerRepository.existsById(playerId)) {
			PlayerDO playerDo = playerRepository.findById(playerId).get();

			if (null == playerDo) {
				logger.error("Invalid playerId");
				throw new CustomException("Invalid playerId");
			} else {
				playerRepository.delete(playerDo);
			}
		} else {
			logger.error("No Player Found for playerId : " + playerId);
			throw new CustomException("No Player Found for playerId : " + playerId);
		}
	}

	@Override
	public boolean existByEmail(String email) {
		
			if (playerRepository.existsByEmail(email)) {
				return true;
			}
			return false;
		}


}
