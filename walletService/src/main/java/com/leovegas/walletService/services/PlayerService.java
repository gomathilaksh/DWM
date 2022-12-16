package com.leovegas.walletService.services;

import com.leovegas.walletService.domainObject.PlayerDO;
import com.leovegas.walletService.exceptions.CustomException;

public interface PlayerService {

	public PlayerDO createPlayer(PlayerDO playerDetails) throws CustomException;

	public PlayerDO getPlayerDetails(Long playerId) throws CustomException;

	public PlayerDO updatePlayer(PlayerDO playerDetails) throws CustomException;

	public void deletePlayer(Long playerId) throws CustomException;
	
	public boolean existByEmail(String email);

}
