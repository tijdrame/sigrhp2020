package com.emard.sigrhp.repository;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.Structure;
import com.emard.sigrhp.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Collaborateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {

    @Query("select collaborateur from Collaborateur collaborateur where collaborateur.userCollab.login = ?#{principal.username}")
    List<Collaborateur> findByUserCollabIsCurrentUser();

    Page<Collaborateur> findByDeletedFalseOrderByNomAscPrenomAsc(Pageable pageable);

    Page<Collaborateur> findByDeletedFalseAndStructureOrderByNomAscPrenomAsc(Pageable pageable, Optional<Structure> structure);

    Collaborateur findByUserCollab(User user);
}
