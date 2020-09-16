package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Collaborateur.
 */
@Entity
@Table(name = "collaborateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Collaborateur extends AbstractAuditingEntity implements Serializable {

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

    
    @Column(name = "matricule", unique = true)
    private String matricule;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "taux_horaire", nullable = false)
    private Double tauxHoraire;

    @NotNull
    @Column(name = "salaire_de_base", nullable = false)
    private Double salaireDeBase;

    @NotNull
    @Column(name = "sur_salaire", nullable = false)
    private Double surSalaire;

    @NotNull
    @Column(name = "retenue_repas", nullable = false)
    private Double retenueRepas;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "prime_transport", nullable = false)
    private Double primeTransport;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "numero_rib")
    private String numeroRib;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private Fonction fonction;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private Categorie categorie;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private Nationalite nationalite;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private Statut statut;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private SituationMatrimoniale situationMatrimoniale;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private TypeContrat typeContrat;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private Regime regime;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private User userCollab;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private Sexe sexe;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborateurs", allowSetters = true)
    private Structure structure;

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

    public Collaborateur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Collaborateur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public Collaborateur matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getAdresse() {
        return adresse;
    }

    public Collaborateur adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getTauxHoraire() {
        return tauxHoraire;
    }

    public Collaborateur tauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
        return this;
    }

    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public Double getSalaireDeBase() {
        return salaireDeBase;
    }

    public Collaborateur salaireDeBase(Double salaireDeBase) {
        this.salaireDeBase = salaireDeBase;
        return this;
    }

    public void setSalaireDeBase(Double salaireDeBase) {
        this.salaireDeBase = salaireDeBase;
    }

    public Double getSurSalaire() {
        return surSalaire;
    }

    public Collaborateur surSalaire(Double surSalaire) {
        this.surSalaire = surSalaire;
        return this;
    }

    public void setSurSalaire(Double surSalaire) {
        this.surSalaire = surSalaire;
    }

    public Double getRetenueRepas() {
        return retenueRepas;
    }

    public Collaborateur retenueRepas(Double retenueRepas) {
        this.retenueRepas = retenueRepas;
        return this;
    }

    public void setRetenueRepas(Double retenueRepas) {
        this.retenueRepas = retenueRepas;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Collaborateur dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Collaborateur photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Collaborateur photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getLogin() {
        return login;
    }

    public Collaborateur login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public Collaborateur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPrimeTransport() {
        return primeTransport;
    }

    public Collaborateur primeTransport(Double primeTransport) {
        this.primeTransport = primeTransport;
        return this;
    }

    public void setPrimeTransport(Double primeTransport) {
        this.primeTransport = primeTransport;
    }

    public String getTelephone() {
        return telephone;
    }

    public Collaborateur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNumeroRib() {
        return numeroRib;
    }

    public Collaborateur numeroRib(String numeroRib) {
        this.numeroRib = numeroRib;
        return this;
    }

    public void setNumeroRib(String numeroRib) {
        this.numeroRib = numeroRib;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public Collaborateur fonction(Fonction fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Collaborateur categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public Collaborateur nationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
        return this;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public Statut getStatut() {
        return statut;
    }

    public Collaborateur statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public SituationMatrimoniale getSituationMatrimoniale() {
        return situationMatrimoniale;
    }

    public Collaborateur situationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
        return this;
    }

    public void setSituationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public Collaborateur typeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
        return this;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Regime getRegime() {
        return regime;
    }

    public Collaborateur regime(Regime regime) {
        this.regime = regime;
        return this;
    }

    public void setRegime(Regime regime) {
        this.regime = regime;
    }

    public User getUserCollab() {
        return userCollab;
    }

    public Collaborateur userCollab(User user) {
        this.userCollab = user;
        return this;
    }

    public void setUserCollab(User user) {
        this.userCollab = user;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Collaborateur sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Structure getStructure() {
        return structure;
    }

    public Collaborateur structure(Structure structure) {
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
        if (!(o instanceof Collaborateur)) {
            return false;
        }
        return id != null && id.equals(((Collaborateur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collaborateur{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tauxHoraire=" + getTauxHoraire() +
            ", salaireDeBase=" + getSalaireDeBase() +
            ", surSalaire=" + getSurSalaire() +
            ", retenueRepas=" + getRetenueRepas() +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", login='" + getLogin() + "'" +
            ", email='" + getEmail() + "'" +
            ", primeTransport=" + getPrimeTransport() +
            ", telephone='" + getTelephone() + "'" +
            ", numeroRib='" + getNumeroRib() + "'" +
            "}";
    }
}
