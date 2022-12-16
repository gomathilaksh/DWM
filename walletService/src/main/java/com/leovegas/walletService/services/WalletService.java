package com.leovegas.walletService.services;

import com.leovegas.walletService.domainObject.WalletDO;

public interface WalletService {

	public boolean createWallet(WalletDO walletdetails);

	public boolean deleteWallet(WalletDO walletDetails);

	public WalletDO findByplayerId(Long playerId);

}
