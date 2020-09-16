package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.Bareme;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bareme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaremeRepository extends JpaRepository<Bareme, Long> {
    
    Bareme findByRevenuBrut(Double revenuBrut);

}
