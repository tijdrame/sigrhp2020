package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Regime.
 */
@Entity
@Table(name = "regime")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Regime extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "taux_patronal", nullable = false)
    private Double tauxPatronal;

    @Column(name = "taux_salarial")
    private Double tauxSalarial;

    @NotNull
    @Column(name = "plafond", nullable = false)
    private Double plafond;

    @ManyToOne
    @JsonIgnoreProperties(value = "regimes", allowSetters = true)
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

    public Regime libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public Regime code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTauxPatronal() {
        return tauxPatronal;
    }

    public Regime tauxPatronal(Double tauxPatronal) {
        this.tauxPatronal = tauxPatronal;
        return this;
    }

    public void setTauxPatronal(Double tauxPatronal) {
        this.tauxPatronal = tauxPatronal;
    }

    public Double getTauxSalarial() {
        return tauxSalarial;
    }

    public Regime tauxSalarial(Double tauxSalarial) {
        this.tauxSalarial = tauxSalarial;
        return this;
    }

    public void setTauxSalarial(Double tauxSalarial) {
        this.tauxSalarial = tauxSalarial;
    }

    public Double getPlafond() {
        return plafond;
    }

    public Regime plafond(Double plafond) {
        this.plafond = plafond;
        return this;
    }

    public void setPlafond(Double plafond) {
        this.plafond = plafond;
    }

    public Structure getStructure() {
        return structure;
    }

    public Regime structure(Structure structure) {
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
        if (!(o instanceof Regime)) {
            return false;
        }
        return id != null && id.equals(((Regime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Regime{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", tauxPatronal=" + getTauxPatronal() +
            ", tauxSalarial=" + getTauxSalarial() +
            ", plafond=" + getPlafond() +
            "}";
    }
}
