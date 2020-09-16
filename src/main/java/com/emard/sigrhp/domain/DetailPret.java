package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DetailPret.
 */
@Entity
@Table(name = "detail_pret")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DetailPret extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "is_rembourse")
    private Boolean isRembourse;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "detailPrets", allowSetters = true)
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "detailPrets", allowSetters = true)
    private Pret pret;

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

    public DetailPret montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean isIsRembourse() {
        return isRembourse;
    }

    public DetailPret isRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
        return this;
    }

    public void setIsRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public DetailPret collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Pret getPret() {
        return pret;
    }

    public DetailPret pret(Pret pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailPret)) {
            return false;
        }
        return id != null && id.equals(((DetailPret) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailPret{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", isRembourse='" + isIsRembourse() + "'" +
            "}";
    }
}
