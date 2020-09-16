package com.emard.sigrhp.repository;

import java.util.List;
import java.util.Optional;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.DetailPret;
import com.emard.sigrhp.domain.Remboursement;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Remboursement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {

    List<Remboursement> findByDetailPret(DetailPret detailPret);

    Page<Remboursement> findByDeletedFalseOrderByDetailPret_Pret_Libelle(Pageable pageable);

    Page<Remboursement> findByDeletedFalseAndDetailPret_Collaborateur_StructureOrderByDetailPret_Pret_Libelle(Pageable pageable, Optional<Structure> structure);

    List<Remboursement> findByDetailPret_CollaborateurAndDeletedFalseAndIsRembourseFalse(Collaborateur collaborateur);

    Page<Remboursement> findByDetailPret_Collaborateur_PrenomLikeIgnoreCaseAndDetailPret_Collaborateur_NomLikeIgnoreCaseAndDetailPret_Collaborateur_MatriculeLikeIgnoreCaseAndDetailPret_Collaborateur_StructureAndDeletedFalseOrderByCreatedDate
        (String prenom, String nom, String matricule, Optional<Structure> structure, Pageable pageable);

    Page<Remboursement> findByDeletedFalseAndDetailPret_Collaborateur_PrenomLikeIgnoreCaseAndDetailPret_Collaborateur_NomLikeIgnoreCaseAndDetailPret_Collaborateur_MatriculeLikeIgnoreCaseOrderByCreatedDate
        (String prenom, String nom, String matricule, Pageable pageable);
        
}
