package com.leovegas.walletService.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leovegas.walletService.domainObject.WalletDO;
import com.leovegas.walletService.repository.WalletRepository;
import com.leovegas.walletService.services.WalletService;

/**
 * Service layer that contacts with walletRepository.
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	WalletRepository walletRepository;

	/**
	 * method to save wallet details to DB table name is WALLET_DETAILS
	 */
	@Override
	public boolean createWallet(WalletDO walletDetails) {
		boolean accountCreated = false;
		if (walletDetails != null) {
			walletRepository.save(walletDetails);
			accountCreated = true;
		}
		return accountCreated;
	}

	/**
	 * method to delete wallet from DB table name is WALLET_DETAILS
	 */
	@Override
	public boolean deleteWallet(WalletDO walletDetails) {
		boolean accountDeleted = false;
		if (walletDetails != null) {
			walletRepository.delete(walletDetails);
			accountDeleted = true;
		}
		return accountDeleted;
	}

	/**
	 * method to return the wallet details for the input playerId table name is
	 * WALLET_DETAILS
	 */
	@Override
	public WalletDO findByplayerId(Long playerId) {
		WalletDO walletDetails = walletRepository.findByplayerId(playerId);
		return walletDetails;
	}

}
