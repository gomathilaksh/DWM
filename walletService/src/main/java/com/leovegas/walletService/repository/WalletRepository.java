package com.leovegas.walletService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leovegas.walletService.domainObject.WalletDO;

/**
 * WalletRepository interface persist data to WALLET_DETAILS
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@Repository
public interface WalletRepository extends JpaRepository<WalletDO, Long> {

	WalletDO findByplayerId(Long playerId);

}
