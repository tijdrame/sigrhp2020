package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Pret.
 */
@Entity
@Table(name = "pret")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pret extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "nb_remboursement", nullable = false)
    private Integer nbRemboursement;

    @NotNull
    @Column(name = "date_pret", nullable = false)
    private LocalDate datePret;

    @NotNull
    @Column(name = "date_debut_remboursement", nullable = false)
    private LocalDate dateDebutRemboursement;

    @ManyToOne
    @JsonIgnoreProperties(value = "prets", allowSetters = true)
    private Structure structure;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Pret libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNbRemboursement() {
        return nbRemboursement;
    }

    public Pret nbRemboursement(Integer nbRemboursement) {
        this.nbRemboursement = nbRemboursement;
        return this;
    }

    public void setNbRemboursement(Integer nbRemboursement) {
        this.nbRemboursement = nbRemboursement;
    }

    public LocalDate getDatePret() {
        return datePret;
    }

    public Pret datePret(LocalDate datePret) {
        this.datePret = datePret;
        return this;
    }

    public void setDatePret(LocalDate datePret) {
        this.datePret = datePret;
    }

    public LocalDate getDateDebutRemboursement() {
        return dateDebutRemboursement;
    }

    public Pret dateDebutRemboursement(LocalDate dateDebutRemboursement) {
        this.dateDebutRemboursement = dateDebutRemboursement;
        return this;
    }

    public void setDateDebutRemboursement(LocalDate dateDebutRemboursement) {
        this.dateDebutRemboursement = dateDebutRemboursement;
    }

    public Structure getStructure() {
        return structure;
    }

    public Pret structure(Structure structure) {
        this.structure = structure;
        return this;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pret)) {
            return false;
        }
        return id != null && id.equals(((Pret) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pret{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", nbRemboursement=" + getNbRemboursement() +
            ", datePret='" + getDatePret() + "'" +
            ", dateDebutRemboursement='" + getDateDebutRemboursement() + "'" +
            "}";
    }
}
