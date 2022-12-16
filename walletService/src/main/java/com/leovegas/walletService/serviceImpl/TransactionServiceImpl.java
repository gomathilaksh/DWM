package com.leovegas.walletService.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leovegas.walletService.domainObject.TransactionDO;
import com.leovegas.walletService.repository.TransactionRepository;
import com.leovegas.walletService.services.TransactionService;

/**
 * Service layer that contacts with transactionRepository.
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository repository;

	/**
	 * save transaction details to DB table name is TRANSACTION_DETAILS
	 */
	@Override
	public TransactionDO createTransaction(TransactionDO transactionDetails) {
		TransactionDO transaction = null;
		if (transactionDetails != null) {
			 transaction = repository.save(transactionDetails);
			
		}
		return transaction;
	}

	/**
	 * check if transactionId is already present in DB table name is
	 * TRANSACTION_DETAILS
	 */
	@Override
	public boolean existsById(Long id) {
		if (repository.existsById(id)) {
			return true;
		}
		return false;
	}

	/**
	 * fetch list of transactions for the input playerId table name is
	 * TRANSACTION_DETAILS
	 */
	@Override
	public List<TransactionDO> getTransactionHistory(Long playerId) {
		List<TransactionDO> transactionHistory = repository.findByplayerId(playerId);
		return transactionHistory;
	}

}
