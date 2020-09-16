package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Regime;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Regime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimeRepository extends JpaRepository<Regime, Long> {
    Page<Regime> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);

    Page<Regime> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
