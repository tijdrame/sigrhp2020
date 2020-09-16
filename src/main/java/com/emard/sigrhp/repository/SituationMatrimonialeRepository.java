package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.SituationMatrimoniale;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SituationMatrimoniale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituationMatrimonialeRepository extends JpaRepository<SituationMatrimoniale, Long> {

    Page<SituationMatrimoniale> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<SituationMatrimoniale> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);
}
