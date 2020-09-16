package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AvantageCollab.
 */
@Entity
@Table(name = "avantage_collab")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvantageCollab extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "avantageCollabs", allowSetters = true)
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "avantageCollabs", allowSetters = true)
    private Avantage avantage;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public AvantageCollab montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public AvantageCollab collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Avantage getAvantage() {
        return avantage;
    }

    public AvantageCollab avantage(Avantage avantage) {
        this.avantage = avantage;
        return this;
    }

    public void setAvantage(Avantage avantage) {
        this.avantage = avantage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvantageCollab)) {
            return false;
        }
        return id != null && id.equals(((AvantageCollab) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvantageCollab{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            "}";
    }
}
