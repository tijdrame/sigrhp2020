package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PrimeCollab.
 */
@Entity
@Table(name = "prime_collab")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrimeCollab extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "primeCollabs", allowSetters = true)
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "primeCollabs", allowSetters = true)
    private Prime prime;

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

    public PrimeCollab montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public PrimeCollab collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Prime getPrime() {
        return prime;
    }

    public PrimeCollab prime(Prime prime) {
        this.prime = prime;
        return this;
    }

    public void setPrime(Prime prime) {
        this.prime = prime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrimeCollab)) {
            return false;
        }
        return id != null && id.equals(((PrimeCollab) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrimeCollab{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            "}";
    }
}
