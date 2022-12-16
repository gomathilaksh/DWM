package com.leovegas.walletService.services;

import java.util.List;

import com.leovegas.walletService.domainObject.TransactionDO;

public interface TransactionService {

	public boolean existsById(Long id);

	public TransactionDO createTransaction(TransactionDO transactionDetails);

	public List<TransactionDO> getTransactionHistory(Long playerId);

}
