package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Avantage;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Avantage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvantageRepository extends JpaRepository<Avantage, Long> {

    Page<Avantage> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);

    Page <Avantage> findByDeletedFalseAndStructure(Pageable pageable, Optional<Structure> structure);

    Page<Avantage> findByDeletedFalseOrderByLibelle(Pageable pageable);

    // Avantage findOptionalByStructure(Optional<Structure> structure);
}
