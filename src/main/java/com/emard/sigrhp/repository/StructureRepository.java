package com.emard.sigrhp.repository;

import java.util.Optional;

import com.emard.sigrhp.domain.Structure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Structure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {

    Page<Structure> findByDeletedFalseOrderByDenomination(Pageable pageable);

    Page <Structure> findByDeletedFalseAndId(Pageable pageable, Optional<Long> structure);

    Structure findByDenomination(String code);
}
