package com.leovegas.walletService.services;

import java.util.List;

import com.leovegas.walletService.domainObject.AuditDO;

public interface AuditService {

	public boolean performAudit(AuditDO auditDetails);

	public List<AuditDO> findByplayerId(Long playerId);

}
