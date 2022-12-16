package com.leovegas.walletService.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leovegas.walletService.domainObject.AuditDO;

/**
 * interface AuditRepository persist data to AUDIT_DETAILS
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@Repository
public interface AuditRepository extends JpaRepository<AuditDO, Long> {

	List<AuditDO> findByplayerId(Long playerId);

}
