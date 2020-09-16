package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DemandeConge.
 */
@Entity
@Table(name = "demande_conge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeConge extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "motif_rejet")
    private String motifRejet;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @ManyToOne
    @JsonIgnoreProperties(value = "demandeConges", allowSetters = true)
    private StatutDemande statutRH;

    @ManyToOne
    @JsonIgnoreProperties(value = "demandeConges", allowSetters = true)
    private StatutDemande statutDG;

    @ManyToOne
    @JsonIgnoreProperties(value = "demandeConges", allowSetters = true)
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "demandeConges", allowSetters = true)
    private TypeAbsence typeAbsence;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public DemandeConge dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public DemandeConge dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public DemandeConge motifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
        return this;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public String getLibelle() {
        return libelle;
    }

    public DemandeConge libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public StatutDemande getStatutRH() {
        return statutRH;
    }

    public DemandeConge statutRH(StatutDemande statutDemande) {
        this.statutRH = statutDemande;
        return this;
    }

    public void setStatutRH(StatutDemande statutDemande) {
        this.statutRH = statutDemande;
    }

    public StatutDemande getStatutDG() {
        return statutDG;
    }

    public DemandeConge statutDG(StatutDemande statutDemande) {
        this.statutDG = statutDemande;
        return this;
    }

    public void setStatutDG(StatutDemande statutDemande) {
        this.statutDG = statutDemande;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public DemandeConge collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public TypeAbsence getTypeAbsence() {
        return typeAbsence;
    }

    public DemandeConge typeAbsence(TypeAbsence typeAbsence) {
        this.typeAbsence = typeAbsence;
        return this;
    }

    public void setTypeAbsence(TypeAbsence typeAbsence) {
        this.typeAbsence = typeAbsence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeConge)) {
            return false;
        }
        return id != null && id.equals(((DemandeConge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeConge{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", motifRejet='" + getMotifRejet() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
