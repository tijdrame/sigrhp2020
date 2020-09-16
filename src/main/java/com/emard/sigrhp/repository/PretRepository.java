package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Pret;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pret entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PretRepository extends JpaRepository<Pret, Long> {

    Page<Pret> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);

    Page<Pret> findByDeletedFalseOrderByLibelle(Pageable pageable);
    
}
