package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Statut;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Statut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatutRepository extends JpaRepository<Statut, Long> {

    Page<Statut> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<Statut> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);
    
}
