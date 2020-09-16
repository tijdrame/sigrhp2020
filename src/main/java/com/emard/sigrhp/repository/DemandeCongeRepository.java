package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.DemandeConge;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DemandeConge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    Page<DemandeConge> findByDeletedFalseAndCollaborateur_StructureOrderByCreatedDate(Pageable pageable, Optional<Structure> structure);

    Page<DemandeConge> findByDeletedFalseOrderByCreatedDate(Pageable pageable);

    Page<DemandeConge> findByDeletedFalseAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndAndCollaborateur_MatriculeLikeIgnoreCaseOrderByCreatedDate(String prenom, String nom, String matricule, Pageable pageable);

    Page<DemandeConge> findByCollaborateurAndDeletedFalseOrderByCreatedDate(Collaborateur collaborateur, Pageable pageable);

    Page<DemandeConge> findByDeletedFalseAndCollaborateur_StructureAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndAndCollaborateur_MatriculeLikeIgnoreCaseOrderByCreatedDate(Optional<Structure> structure, String prenom, String nom, String matricule, Pageable pageable);
    
}
