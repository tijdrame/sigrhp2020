package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Remboursement.
 */
@Entity
@Table(name = "remboursement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Remboursement extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_remboursement", nullable = false)
    private LocalDate dateRemboursement;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "is_rembourse")
    private Boolean isRembourse;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "remboursements", allowSetters = true)
    private DetailPret detailPret;

    @ManyToMany(mappedBy = "remboursements")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Bulletin> bulletins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRemboursement() {
        return dateRemboursement;
    }

    public Remboursement dateRemboursement(LocalDate dateRemboursement) {
        this.dateRemboursement = dateRemboursement;
        return this;
    }

    public void setDateRemboursement(LocalDate dateRemboursement) {
        this.dateRemboursement = dateRemboursement;
    }

    public Double getMontant() {
        return montant;
    }

    public Remboursement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean isIsRembourse() {
        return isRembourse;
    }

    public Remboursement isRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
        return this;
    }

    public void setIsRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
    }

    public DetailPret getDetailPret() {
        return detailPret;
    }

    public Remboursement detailPret(DetailPret detailPret) {
        this.detailPret = detailPret;
        return this;
    }

    public void setDetailPret(DetailPret detailPret) {
        this.detailPret = detailPret;
    }

    public Set<Bulletin> getBulletins() {
        return bulletins;
    }

    public Remboursement bulletins(Set<Bulletin> bulletins) {
        this.bulletins = bulletins;
        return this;
    }

    public Remboursement addBulletins(Bulletin bulletin) {
        this.bulletins.add(bulletin);
        bulletin.getRemboursements().add(this);
        return this;
    }

    public Remboursement removeBulletins(Bulletin bulletin) {
        this.bulletins.remove(bulletin);
        bulletin.getRemboursements().remove(this);
        return this;
    }

    public void setBulletins(Set<Bulletin> bulletins) {
        this.bulletins = bulletins;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remboursement)) {
            return false;
        }
        return id != null && id.equals(((Remboursement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Remboursement{" +
            "id=" + getId() +
            ", dateRemboursement='" + getDateRemboursement() + "'" +
            ", montant=" + getMontant() +
            ", isRembourse='" + isIsRembourse() + "'" +
            "}";
    }
}
