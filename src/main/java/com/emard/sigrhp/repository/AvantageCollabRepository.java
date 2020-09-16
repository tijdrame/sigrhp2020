package com.emard.sigrhp.repository;

import java.util.List;
import java.util.Optional;

import com.emard.sigrhp.domain.AvantageCollab;
import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvantageCollab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvantageCollabRepository extends JpaRepository<AvantageCollab, Long> {
    
    Page<AvantageCollab> findByDeletedFalseAndCollaborateur_StructureOrderByAvantage_Libelle(Pageable pageable, Optional<Structure> structure);

    Page<AvantageCollab> findByDeletedFalseOrderByAvantage_Libelle(Pageable pageable);

    List<AvantageCollab> findByCollaborateurAndDeletedFalse(Collaborateur collaborateur);
}
