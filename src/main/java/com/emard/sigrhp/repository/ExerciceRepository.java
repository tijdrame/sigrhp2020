package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Exercice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Exercice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Long> {

    Page<Exercice> findByDeletedFalseOrderByDebutExerciceDesc(Pageable pageable);

    Optional<Exercice> findByDebutExerciceAndDeletedFalse(Integer debutExercice);
    
}
