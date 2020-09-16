package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Structure;
import com.emard.sigrhp.domain.TypeContrat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeContratRepository extends JpaRepository<TypeContrat, Long> {

    Page<TypeContrat> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<TypeContrat> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);

}
