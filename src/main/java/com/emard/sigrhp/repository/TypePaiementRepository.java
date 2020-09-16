package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Structure;
import com.emard.sigrhp.domain.TypePaiement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePaiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePaiementRepository extends JpaRepository<TypePaiement, Long> {

    Page<TypePaiement> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<TypePaiement> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);
}
