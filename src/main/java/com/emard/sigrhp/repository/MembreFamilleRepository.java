package com.emard.sigrhp.repository;

import java.util.List;
import java.util.Optional;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.MembreFamille;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MembreFamille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreFamilleRepository extends JpaRepository<MembreFamille, Long> {

    Page<MembreFamille> findByDeletedFalseOrderByNomAscPrenomAsc(Pageable pageable);

    Page<MembreFamille> findByDeletedFalseAndCollaborateur_StructureOrderByNomAscPrenomAsc(Pageable pageable, Optional<Structure> structure);

    List<MembreFamille> findByCollaborateur(Collaborateur collaborateur);
    
}
