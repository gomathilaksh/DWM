package com.leovegas.walletService.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leovegas.walletService.domainObject.AuditDO;
import com.leovegas.walletService.repository.AuditRepository;
import com.leovegas.walletService.services.AuditService;

/**
 * service layer contacts with AuditRepository
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	AuditRepository auditRepository;

	/**
	 * save audit to DB table name is AUDIT_DETAILS
	 */
	@Override
	public boolean performAudit(AuditDO auditDetails) {
		boolean auditFlag = false;
		if (auditDetails != null) {
			auditRepository.save(auditDetails);
			auditFlag = true;
		}
		return auditFlag;
	}

	/**
	 * find audits from DB based on PlayerId table name is AUDIT_DETAILS
	 */
	@Override
	public List<AuditDO> findByplayerId(Long playerId) {
		List<AuditDO> auditDetails = auditRepository.findByplayerId(playerId);
		return auditDetails;
	}

}
