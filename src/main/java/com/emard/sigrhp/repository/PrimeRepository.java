package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Prime;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Prime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long> {

    Page<Prime> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);

    Page<Prime> findByDeletedFalseOrderByLibelle(Pageable pageable);
    
}
