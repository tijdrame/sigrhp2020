package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Motif;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Motif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotifRepository extends JpaRepository<Motif, Long> {

    Page<Motif> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);

    Page<Motif> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Motif findByCode(String p);
    
}
