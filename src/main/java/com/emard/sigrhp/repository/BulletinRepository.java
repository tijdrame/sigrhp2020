package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.Bulletin;
import com.emard.sigrhp.domain.Exercice;
import com.emard.sigrhp.domain.MoisConcerne;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Bulletin entity.
 */
@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {

    @Query(value = "select distinct bulletin from Bulletin bulletin left join fetch bulletin.remboursements",
        countQuery = "select count(distinct bulletin) from Bulletin bulletin")
    Page<Bulletin> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct bulletin from Bulletin bulletin left join fetch bulletin.remboursements")
    List<Bulletin> findAllWithEagerRelationships();

    @Query("select bulletin from Bulletin bulletin left join fetch bulletin.remboursements where bulletin.id =:id")
    Optional<Bulletin> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Bulletin> findByDeletedFalseAndCollaborateur_StructureOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(Pageable pageable, Optional<Structure> structure);

    Page<Bulletin> findByDeletedFalseOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(Pageable pageable);

    Page<Bulletin> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndMoisConcerneAndExerciceAndDeletedOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(String prenom, String nom, String matricule, MoisConcerne moisConcerne, Exercice exercice, Boolean supprime, Pageable pageable);

    Page<Bulletin> findByCollaborateur_StructureAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndMoisConcerneAndExerciceAndDeletedOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(Optional<Structure> structure, String prenom, String nom, String matricule, MoisConcerne moisConcerne, Exercice exercice, Boolean supprime, Pageable pageable);

    Page<Bulletin> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndExercice_DebutExerciceAndMoisConcerne_LibelleLikeIgnoreCaseAndDeletedOrderByCreatedDate(String prenom, String nom, String matricule, Integer debutExercice, String mot, Boolean deleted, Pageable pageable);

    Page<Bulletin> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndCollaborateur_StructureAndExercice_DebutExerciceAndMoisConcerne_LibelleLikeIgnoreCaseAndDeletedOrderByCreatedDate(String prenom, String nom, String matricule, Optional<Structure> structure, Integer debutExercice, String mot, Boolean deleted, Pageable pageable);
}
