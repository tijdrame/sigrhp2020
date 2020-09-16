package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Absence.
 */
@Entity
@Table(name = "absence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Absence extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_absence", nullable = false)
    private LocalDate dateAbsence;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "absences", allowSetters = true)
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "absences", allowSetters = true)
    private Motif motif;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "absences", allowSetters = true)
    private Exercice exercice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAbsence() {
        return dateAbsence;
    }

    public Absence dateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
        return this;
    }

    public void setDateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public Absence collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Motif getMotif() {
        return motif;
    }

    public Absence motif(Motif motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public Absence exercice(Exercice exercice) {
        this.exercice = exercice;
        return this;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Absence)) {
            return false;
        }
        return id != null && id.equals(((Absence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Absence{" +
            "id=" + getId() +
            ", dateAbsence='" + getDateAbsence() + "'" +
            "}";
    }
}
