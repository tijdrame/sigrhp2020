package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Categorie;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Categorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    Page<Categorie> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<Categorie> findByDeletedFalseAndStructureOrderByLibelle(Pageable pageable, Optional<Structure> structure);
    
}
