package com.leovegas.walletService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.leovegas.walletService.domainObject.PlayerDO;

/**
 * PlayerRepository interface persist data to PLAYER_DETAILS
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@Repository
public interface PlayerRepository extends JpaRepository<PlayerDO, Long> {

	boolean existsByEmail(String email);

}
