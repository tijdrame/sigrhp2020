package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Absence;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Absence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    Page<Absence> findByDeletedFalseAndCollaborateur_StructureOrderByDateAbsenceDesc(Pageable pageable, Optional<Structure> structure);

    Page<Absence> findByDeletedFalseOrderByDateAbsenceDesc(Pageable pageable);


    Page<Absence> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndCollaborateur_StructureAndExercice_DebutExerciceAndMotif_LibelleLikeIgnoreCaseAndDeletedFalseOrderByDateAbsenceDesc
        (String prenom, String nom, String matricule, Optional<Structure> structure, Integer exercice, String motif, Pageable pageable);

    Page<Absence> findByDeletedFalseAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndExercice_DebutExerciceAndMotif_LibelleLikeIgnoreCaseOrderByDateAbsenceDesc
        (String prenom, String nom, String matricule, Integer exercice, String motif, Pageable pageable);
}
