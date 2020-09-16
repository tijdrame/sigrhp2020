package com.emard.sigrhp.service;

import com.emard.sigrhp.config.audit.AuditEventConverter;
import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.domain.UserExtra;
import com.emard.sigrhp.repository.PersistenceAuditEventRepository;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 */
@Service
@Transactional
public class AuditEventService {

    private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    private final AuditEventConverter auditEventConverter;

    public AuditEventService(
        PersistenceAuditEventRepository persistenceAuditEventRepository,
        AuditEventConverter auditEventConverter) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    public Page<AuditEvent> findAll(Pageable pageable) {
        return persistenceAuditEventRepository.findAll(pageable)
            .map(auditEventConverter::convertToAuditEvent);
    }

    public Page<AuditEvent> findByDates(Instant fromDate, Instant toDate, Pageable pageable) {
        return persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate, pageable)
            .map(auditEventConverter::convertToAuditEvent);
    }

    public Optional<AuditEvent> find(Long id) {
        return Optional.ofNullable(persistenceAuditEventRepository.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(auditEventConverter::convertToAuditEvent);
    }

    public void saveAudit(PersistentAuditEvent persistentAuditEvent){
        persistenceAuditEventRepository.save(persistentAuditEvent);
    }

    public PersistentAuditEvent logEvent(String event, User user, UserExtra userExtra){
        PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
        persistentAuditEvent.setAuditEventDate(Instant.now());
        persistentAuditEvent.setAuditEventType(event);
        persistentAuditEvent.setStructure(userExtra != null ? userExtra.getStructure(): null);
        persistentAuditEvent.setPrincipal(user.getLogin());

        return persistentAuditEvent;
    }
}
