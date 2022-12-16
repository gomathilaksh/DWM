package com.leovegas.walletService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.leovegas.walletService.domainObject.TransactionDO;

/**
 * TransactionRepository interface persist data to TRANSACTION_DETAILS
 * 
 * @author gomathi lakshmanaperumal
 *
 */

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDO, Long> {

	List<TransactionDO> findByplayerId(Long playerId);

}
