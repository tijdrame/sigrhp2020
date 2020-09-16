package com.emard.sigrhp.repository;

import java.util.List;

import com.emard.sigrhp.domain.Nationalite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Nationalite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Long> {

    Page<Nationalite> findByDeletedFalseOrderByLibelle(Pageable pageable);

    List<Nationalite> findByDeletedFalseOrderByLibelle();
}
