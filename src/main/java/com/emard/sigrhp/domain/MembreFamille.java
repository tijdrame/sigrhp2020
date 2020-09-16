package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A MembreFamille.
 */
@Entity
@Table(name = "membre_famille")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MembreFamille extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "date_mariage")
    private LocalDate dateMariage;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "is_actif")
    private Boolean isActif;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "membreFamilles", allowSetters = true)
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "membreFamilles", allowSetters = true)
    private Sexe sexe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "membreFamilles", allowSetters = true)
    private TypeRelation typeRelation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public MembreFamille prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public MembreFamille nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public MembreFamille adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public MembreFamille telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public MembreFamille dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDate getDateMariage() {
        return dateMariage;
    }

    public MembreFamille dateMariage(LocalDate dateMariage) {
        this.dateMariage = dateMariage;
        return this;
    }

    public void setDateMariage(LocalDate dateMariage) {
        this.dateMariage = dateMariage;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public MembreFamille photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public MembreFamille photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Boolean isIsActif() {
        return isActif;
    }

    public MembreFamille isActif(Boolean isActif) {
        this.isActif = isActif;
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public MembreFamille collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public MembreFamille sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public TypeRelation getTypeRelation() {
        return typeRelation;
    }

    public MembreFamille typeRelation(TypeRelation typeRelation) {
        this.typeRelation = typeRelation;
        return this;
    }

    public void setTypeRelation(TypeRelation typeRelation) {
        this.typeRelation = typeRelation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembreFamille)) {
            return false;
        }
        return id != null && id.equals(((MembreFamille) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembreFamille{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", dateMariage='" + getDateMariage() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", isActif='" + isIsActif() + "'" +
            "}";
    }
}
