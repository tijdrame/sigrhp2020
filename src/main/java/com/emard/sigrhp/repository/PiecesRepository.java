package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Pieces;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pieces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PiecesRepository extends JpaRepository<Pieces, Long> {

    Page<Pieces> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<Pieces> findByDeletedFalseAndCollaborateur_StructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);

    Page<Pieces> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndDeletedOrderByCreatedDate
        (String prenom, String nom, String matricule, Boolean deleted, Pageable pageable);

    Page<Pieces> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndCollaborateur_StructureAndDeletedOrderByCreatedDate
        (String prenom, String nom, String matricule, Optional<Structure> structure, Boolean deleted, Pageable pageable);
        
}
