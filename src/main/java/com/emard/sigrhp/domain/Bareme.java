package com.emard.sigrhp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Bareme.
 */
@Entity
@Table(name = "bareme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bareme extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "revenu_brut")
    private Double revenuBrut;

    @Column(name = "trim_f")
    private Double trimF;

    @Column(name = "une_part")
    private Double unePart;

    @Column(name = "une_part_et_demi")
    private Double unePartEtDemi;

    @Column(name = "deux_parts")
    private Double deuxParts;

    @Column(name = "deux_parts_et_demi")
    private Double deuxPartsEtDemi;

    @Column(name = "trois_parts")
    private Double troisParts;

    @Column(name = "trois_parts_et_demi")
    private Double troisPartsEtDemi;

    @Column(name = "quatre_parts")
    private Double quatreParts;

    @Column(name = "quatre_parts_et_demi")
    private Double quatrePartsEtDemi;

    @Column(name = "cinq_parts")
    private Double cinqParts;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRevenuBrut() {
        return revenuBrut;
    }

    public Bareme revenuBrut(Double revenuBrut) {
        this.revenuBrut = revenuBrut;
        return this;
    }

    public void setRevenuBrut(Double revenuBrut) {
        this.revenuBrut = revenuBrut;
    }

    public Double getTrimF() {
        return trimF;
    }

    public Bareme trimF(Double trimF) {
        this.trimF = trimF;
        return this;
    }

    public void setTrimF(Double trimF) {
        this.trimF = trimF;
    }

    public Double getUnePart() {
        return unePart;
    }

    public Bareme unePart(Double unePart) {
        this.unePart = unePart;
        return this;
    }

    public void setUnePart(Double unePart) {
        this.unePart = unePart;
    }

    public Double getUnePartEtDemi() {
        return unePartEtDemi;
    }

    public Bareme unePartEtDemi(Double unePartEtDemi) {
        this.unePartEtDemi = unePartEtDemi;
        return this;
    }

    public void setUnePartEtDemi(Double unePartEtDemi) {
        this.unePartEtDemi = unePartEtDemi;
    }

    public Double getDeuxParts() {
        return deuxParts;
    }

    public Bareme deuxParts(Double deuxParts) {
        this.deuxParts = deuxParts;
        return this;
    }

    public void setDeuxParts(Double deuxParts) {
        this.deuxParts = deuxParts;
    }

    public Double getDeuxPartsEtDemi() {
        return deuxPartsEtDemi;
    }

    public Bareme deuxPartsEtDemi(Double deuxPartsEtDemi) {
        this.deuxPartsEtDemi = deuxPartsEtDemi;
        return this;
    }

    public void setDeuxPartsEtDemi(Double deuxPartsEtDemi) {
        this.deuxPartsEtDemi = deuxPartsEtDemi;
    }

    public Double getTroisParts() {
        return troisParts;
    }

    public Bareme troisParts(Double troisParts) {
        this.troisParts = troisParts;
        return this;
    }

    public void setTroisParts(Double troisParts) {
        this.troisParts = troisParts;
    }

    public Double getTroisPartsEtDemi() {
        return troisPartsEtDemi;
    }

    public Bareme troisPartsEtDemi(Double troisPartsEtDemi) {
        this.troisPartsEtDemi = troisPartsEtDemi;
        return this;
    }

    public void setTroisPartsEtDemi(Double troisPartsEtDemi) {
        this.troisPartsEtDemi = troisPartsEtDemi;
    }

    public Double getQuatreParts() {
        return quatreParts;
    }

    public Bareme quatreParts(Double quatreParts) {
        this.quatreParts = quatreParts;
        return this;
    }

    public void setQuatreParts(Double quatreParts) {
        this.quatreParts = quatreParts;
    }

    public Double getQuatrePartsEtDemi() {
        return quatrePartsEtDemi;
    }

    public Bareme quatrePartsEtDemi(Double quatrePartsEtDemi) {
        this.quatrePartsEtDemi = quatrePartsEtDemi;
        return this;
    }

    public void setQuatrePartsEtDemi(Double quatrePartsEtDemi) {
        this.quatrePartsEtDemi = quatrePartsEtDemi;
    }

    public Double getCinqParts() {
        return cinqParts;
    }

    public Bareme cinqParts(Double cinqParts) {
        this.cinqParts = cinqParts;
        return this;
    }

    public void setCinqParts(Double cinqParts) {
        this.cinqParts = cinqParts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bareme)) {
            return false;
        }
        return id != null && id.equals(((Bareme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bareme{" +
            "id=" + getId() +
            ", revenuBrut=" + getRevenuBrut() +
            ", trimF=" + getTrimF() +
            ", unePart=" + getUnePart() +
            ", unePartEtDemi=" + getUnePartEtDemi() +
            ", deuxParts=" + getDeuxParts() +
            ", deuxPartsEtDemi=" + getDeuxPartsEtDemi() +
            ", troisParts=" + getTroisParts() +
            ", troisPartsEtDemi=" + getTroisPartsEtDemi() +
            ", quatreParts=" + getQuatreParts() +
            ", quatrePartsEtDemi=" + getQuatrePartsEtDemi() +
            ", cinqParts=" + getCinqParts() +
            "}";
    }
}
