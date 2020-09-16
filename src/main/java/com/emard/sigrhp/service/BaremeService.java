package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.Bareme;
import com.emard.sigrhp.repository.BaremeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Bareme.
 */
@Service
@Transactional
public class BaremeService {

    private final Logger log = LoggerFactory.getLogger(BaremeService.class);

    private final BaremeRepository baremeRepository;

    public BaremeService(BaremeRepository baremeRepository) {
        this.baremeRepository = baremeRepository;
    }

    /**
     * Save a bareme.
     *
     * @param bareme the entity to save
     * @return the persisted entity
     */
    public Bareme save(Bareme bareme) {
        log.debug("Request to save Bareme : {}", bareme);
        return baremeRepository.save(bareme);
    }

    /**
     * Get all the baremes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bareme> findAll(Pageable pageable) {
        log.debug("Request to get all Baremes");
        return baremeRepository.findAll(pageable);
    }


    /**
     * Get one bareme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Bareme> findOne(Long id) {
        log.debug("Request to get Bareme : {}", id);
        return baremeRepository.findById(id);
    }

    /**
     * Delete the bareme by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bareme : {}", id);
        baremeRepository.deleteById(id);
    }

    @Transactional
    public Bareme getBaremeByRevenuBrut(Double revenuBrut){
        log.debug("Request to get the Bareme ");
        return  baremeRepository.findByRevenuBrut(revenuBrut);
    }
}
