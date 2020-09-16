package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.DetailPret;
import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DetailPret entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailPretRepository extends JpaRepository<DetailPret, Long> {

    Page<DetailPret> findByDeletedFalseAndCollaborateur_StructureOrderByPret_Libelle(Pageable pageable, Optional<Structure> structure);

    Page<DetailPret> findByDeletedFalseOrderByPret_Libelle(Pageable pageable);
}
