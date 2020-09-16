package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.StatutDemande;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StatutDemande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Long> {

    Page<StatutDemande> findByDeletedFalseOrderByLibelle(Pageable pageable);

    StatutDemande findByCode(String encours);
}
