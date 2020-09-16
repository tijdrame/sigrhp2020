package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SituationMatrimoniale.
 */
@Entity
@Table(name = "situation_matrimoniale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SituationMatrimoniale extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "nb_parts", nullable = false)
    private Double nbParts;

    @ManyToOne
    @JsonIgnoreProperties(value = "situationMatrimoniales", allowSetters = true)
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

    public SituationMatrimoniale libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public SituationMatrimoniale code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getNbParts() {
        return nbParts;
    }

    public SituationMatrimoniale nbParts(Double nbParts) {
        this.nbParts = nbParts;
        return this;
    }

    public void setNbParts(Double nbParts) {
        this.nbParts = nbParts;
    }

    public Structure getStructure() {
        return structure;
    }

    public SituationMatrimoniale structure(Structure structure) {
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
        if (!(o instanceof SituationMatrimoniale)) {
            return false;
        }
        return id != null && id.equals(((SituationMatrimoniale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SituationMatrimoniale{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", nbParts=" + getNbParts() +
            "}";
    }
}
