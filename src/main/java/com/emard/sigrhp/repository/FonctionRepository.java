package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Fonction;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Fonction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FonctionRepository extends JpaRepository<Fonction, Long> {

    Page<Fonction> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<Fonction> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);
}
