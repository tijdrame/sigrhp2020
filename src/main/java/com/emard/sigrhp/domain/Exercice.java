package com.emard.sigrhp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Exercice.
 */
@Entity
@Table(name = "exercice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Exercice extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "debut_exercice", nullable = false)
    private Integer debutExercice;

    @Column(name = "fin_exercice")
    private Integer finExercice;

    @Column(name = "actif")
    private Boolean actif;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDebutExercice() {
        return debutExercice;
    }

    public Exercice debutExercice(Integer debutExercice) {
        this.debutExercice = debutExercice;
        return this;
    }

    public void setDebutExercice(Integer debutExercice) {
        this.debutExercice = debutExercice;
    }

    public Integer getFinExercice() {
        return finExercice;
    }

    public Exercice finExercice(Integer finExercice) {
        this.finExercice = finExercice;
        return this;
    }

    public void setFinExercice(Integer finExercice) {
        this.finExercice = finExercice;
    }

    public Boolean isActif() {
        return actif;
    }

    public Exercice actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exercice)) {
            return false;
        }
        return id != null && id.equals(((Exercice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exercice{" +
            "id=" + getId() +
            ", debutExercice=" + getDebutExercice() +
            ", finExercice=" + getFinExercice() +
            ", actif='" + isActif() + "'" +
            "}";
    }
}
