package com.emard.sigrhp.repository;

import java.util.List;
import java.util.Optional;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.PrimeCollab;
import com.emard.sigrhp.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PrimeCollab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimeCollabRepository extends JpaRepository<PrimeCollab, Long> {

    Page<PrimeCollab> findByDeletedFalseOrderByPrime_Libelle(Pageable pageable);

    Page<PrimeCollab> findByDeletedFalseAndCollaborateur_StructureOrderByPrime_Libelle(Pageable pageable, Optional<Structure> structure);

    List<PrimeCollab> findByCollaborateurAndDeletedFalse(Collaborateur collaborateur);
}
